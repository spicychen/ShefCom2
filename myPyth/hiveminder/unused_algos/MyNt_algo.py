from hiveminder.utils import distance_between_hex_cells
from hiveminder.utils import nearest_hex_cell
from hiveminder.utils import is_on_course_with
from hiveminder.utils import _OtherBoard
from hiveminder._util import even_q_distance, _first
from random import Random
from hiveminder.board import Board, volant_from_json
from hiveminder.hive import Hive
from hiveminder.flower import Flower
from hiveminder.game_params import DEFAULT_GAME_PARAMETERS
from hiveminder.volant import Volant
from hiveminder import algo_player
import math

new_headings = {0: [60, -60],
	60: [120, 0],
	120: [180, 60],
	180: [-120, 120],
	- 120: [-60, 180],
	- 60: [0, -120], }
	
scores = {"waste": -10,
	"fullhome": 18.8,
	"home": -6,
	"headhome": -1,
	"work": 0,
	"plant":15,
	"HiveLost":-70,
	"gardening":0,
	"changemind":-0.1,
	"totalnectar":0}

@algo_player(name="MyNt",
	description="Best Firsto and planing the space in early stage, gardening flowers by seeds")
def myNecTar_algo(board_width, board_height, hives, flowers, inflight, crashed,
					lost_volants, received_volants, landed, scores, player_id, game_id, turn_num):
	
	if inflight:
		
		hive_locations = [hive[:2] for hive in hives]
		flowerll = [(flower[:2],flower[5]) for flower in flowers]
		filled_tiles = hive_locations + [location for location, life in flowerll]
		print("NT")
		print(hive_locations)
		print(flowerll)
		
		possible_moves = [dict(entity = ib, command=new_headings[bee[3]][turn]) for ib, bee in inflight.items() for turn in range(2)]
		queen = [i for i,b in inflight.items() if b[0]=="QueenBee" and b[1:3] not in filled_tiles]
		if queen:
			for i in queen:
				possible_moves.append(dict(entity=i, command="create_hive"))
		seed = [i for i,b in inflight.items() if b[0]=="Seed" and b[1:3] not in filled_tiles]
		if seed:
			for i in seed:
				possible_moves.append(dict(entity=i, command="flower"))
		
		possible_moves.append(None)
		moves_scores = [(move,-cal_score(board_width,board_height,hives,flowers,inflight,turn_num,move)) for move in possible_moves]
		besto_move, highest_score = sorted(moves_scores,key=lambda x:x[1])[0]
		return besto_move
		
def cal_score(board_width,board_height,hives,flowers,inflight,turn_num,move):
	states = []
	futurehives, futureflowers, futureinflight, crashed, landed, lost, rc = detail_advance(board_width,board_height,hives,flowers,inflight,turn_num,move)
		
	#print(crashed)
	if crashed:
		for crashedbee in crashed['collided']:
			deadbee = inflight[crashedbee]
			if deadbee[0]=="QueenBee":
				states.append(("HiveLost",-2*deadbee[6]))
			else:
				states.append(("waste",-2*deadbee[6]))
		for headonbee in crashed['headon']:
			deadbee = inflight[headonbee]
			if deadbee[0]=="QueenBee":
				states.append(("HiveLost",-2*deadbee[6]))
			else:
				states.append(("waste",-2*deadbee[6]))
		for exhaustedbee in crashed['exhausted']:
			deadbee = inflight[exhaustedbee]
			if deadbee[0]=="QueenBee":
				states.append(("HiveLost",-2*deadbee[6]))
			else:
				states.append(("waste",-2*deadbee[6]))
	
	if landed:
		for landedbee in landed:
			homebee = inflight[landedbee]
			if homebee[0] == "QueenBee":
				states.append(("HiveLost",2*homebee[6]))
			elif homebee[6]==5:
				states.append(("fullhome",0))
			else:
				states.append(("home",2*homebee[6]))
	
	
	
	futurehives = [fh.to_json()[:2] for fh in futurehives]
	futureflowers = [f.to_json()[:2] for f in futureflowers]
	bees = [(bee_id, abstract_bee.to_json()) for bee_id, abstract_bee in futureinflight.items() if abstract_bee.to_json()[0]=="Bee"]
	
	if bees:
		bees_states = [(bee[1:4],bee[6]) for bee_id, bee in bees]
		totalnectar = 0
		for bp,bs in bees_states:
			totalnectar += bs
			headhome = head_home(bp[:2],bp[2],[fh[:2] for fh in futurehives])
			if headhome:
				path = pass_tiles(bp[:2],bp[2],headhome)
				flower_in_path = [fl for fl in futureflowers if fl in path]
				if flower_in_path:
					states.append(("work",10-2*bs+len(flower_in_path)/2))
				states.append(("headhome",2*bs))
			else:
				path = pass_tiles(bp[:2],bp[2])
				flower_in_path = [fl for fl in futureflowers if fl in path]
				if flower_in_path:
					states.append(("work",10-2*bs+len(flower_in_path)/2))
		states.append(("totalnectar",totalnectar*2))
	
	seedsxys = [abstract_seed.to_json()[1:3] for sid,abstract_seed in futureinflight.items() if abstract_seed.to_json()[0]=="Seed"]
	for sxy in seedsxys:
		fl_sc = flower_sco(sxy,futurehives)
		states.append(("gardening",fl_sc))
	
	if move is not None:
		action = move["command"]
		bee_id = move["entity"]
		bee = inflight[bee_id]
		core = futurehives[0]
		if action == "create_hive" and good_address(bee[1:3],core):
			states.append(("plant",0))
		elif action == "flower" and (not good_address(bee[1:3],core)) and (flower_sco(bee[1:3],futurehives)!=0):
			states.append(("plant",0))
		elif bee[0] == "Bee" and bee_id not in landed:
			states.append(("changemind",0))
	
	totalscore=0
	for state,importance in states:
		totalscore += scores[state]+importance
	print(move,states,totalscore)
	return totalscore
	
def detail_advance(board_width, board_height, hives, flowers, inflight, turn_num, cmd):
	board = Board(game_params=DEFAULT_GAME_PARAMETERS,
				board_width=board_width,
				board_height=board_height,
				hives=[Hive(*i) for i in hives],
				flowers=[Flower.from_json(i) for i in flowers],
				neighbours={'N':0, 'S':0, 'E':0, 'W':0, 'NW':0, 'SE':0, },
				inflight={volant_id: volant_from_json(volant) for volant_id, volant in inflight.items()},
				dead_bees=0)
	
	board._connect_to_neighbours([board, _OtherBoard()])
	board.apply_command(cmd, turn_num)
	board.remove_dead_flowers(turn_num)
	board.move_volants()
	lost = set(board.send_volants())
	received = set(board.receive_volants())
	board.visit_flowers(Random())
	landed = set(board.land_bees())
	crashed = {k: set(v) for k, v in board.detect_crashes().items()}
	
	return board.hives, board.flowers, board.inflight, crashed, landed, lost, received

def head_home(start,heading,hives):
	v = Volant(*(tuple(start) + (heading,)))
	for _ in range(7):
		v = v.advance()
		x,y,h = v.xyh
		for hive in hives:
			if (x,y) == tuple(hive):
				return [x,y]
	return []

def pass_tiles(start,heading,end=None):
	v = Volant(*(tuple(start) + (heading,)))
	tiles = [list(start)]
	for _ in range(7):
		v = v.advance()
		x,y,h = v.xyh
		if end is not None and (x,y) == tuple(end):
			return tiles
		tiles.append([x%8,y%8])
	return tiles

def good_address(cell,home):
	hx,hy = home
	cx,cy = cell
	dx=cx-hx
	dy=cy-hy
	ry = dy%3
	if dx==0:
		return ry==0
	elif hx%2==1:
		rr = [math.floor(1.5*dx)%3,math.floor(-1.5*dx)%3]
		return ry in rr
	else:
		rr = [math.ceil(1.5*dx)%3,math.ceil(-1.5*dx)%3]
		return ry in rr

def flower_sco(seedxy,hives):
	fs = 0
	x,y = seedxy
	around = [[x,y+1],[x-1,y],[x+1,y],[x,y-1]]
	if x%2==0:
		around.append([x-1,y+1])
		around.append([x+1,y+1])
	else:
		around.append([x-1,y-1])
		around.append([x+1,y-1])
	for pos in around:
		if pos in hives:
			print(pos)
			fs += 1
	return fs