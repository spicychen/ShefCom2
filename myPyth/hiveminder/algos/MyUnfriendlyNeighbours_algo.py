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
	"totalnectar":0,
	"centre": 4,
	"indanger":0}

bee_energy_threshold = 2
hives_threshold = 3

@algo_player(name="MyUN",
	description="Best Firsto and planing the space in early stage, gardening flowers by seeds, keep good bees, let poor bees go")
def myKeepGood_algo(board_width, board_height, hives, flowers, inflight, crashed,
					lost_volants, received_volants, landed, scores, player_id, game_id, turn_num):
	
	if inflight:
		
		hive_locations = [hive[:2] for hive in hives]
		flowerll = [(flower[:2],flower[5]) for flower in flowers]
		filled_tiles = hive_locations + [location for location, life in flowerll]
		#"""print("KKKKKK")
		print(hive_locations)
		print(flowerll)
		#print("infl",inflight)
		#print("rc",received_volants)"""
		#print("\n\n\n")
		
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
	#print(futureinflight)
	#print(crashed)
	
	futurehives_loc = [fh.to_json()[:2] for fh in futurehives]
	futureflowers_loc = [f.to_json()[:2] for f in futureflowers]
	
	hives_num = len(futurehives_loc)
	greedy = hives_num>hives_threshold
	if greedy:
		scores['home'] = -6
		scores['headhome'] = -1
	else:
		scores['home'] = 7
		scores['headhome'] = 7
		
	if move is not None:
		ett = move['entity']
		cmd = move['command']
		ifl = inflight[ett]
		if cmd == "create_hive" and good_address(ifl[1:3]):
			states.append(("plant",0))
		if cmd == "flower" and not good_address(ifl[1:3]):
			states.append(("plant",0))
	
	bees = [(bee_id, abstract_bee.to_json()) for bee_id, abstract_bee in futureinflight.items() if abstract_bee.to_json()[0]=="Bee"]
	
	if bees:
		bees_states = [(bee[1:4],bee[6],bee[4]) for bee_id, bee in bees]
		totalnectar = 0
		for bp,bs,bh in bees_states:
			if gonna_lost(bp) and (greedy or bs>=3):
				states.append(("waste",-2*bs))
			else:
				if bh>bee_energy_threshold:
					totalnectar += bs
					states.append(("indanger",-neighbour_tiles(bp[:2])*bs/2))
				hh = head_home(bp[:2],bp[2],[fh[:2] for fh in futurehives_loc])
				if hh:
					if bh>bee_energy_threshold:
						states.append(("headhome",2*bs))
					path = pass_tiles(bp[:2],bp[2],hh)
				else:
					path = pass_tiles(bp[:2],bp[2])
				flower_in_path = [fl for fl in futureflowers_loc if fl in path]
				if flower_in_path:
					states.append(("work",10-2*bs+len(flower_in_path)/2))
				
		states.append(("totalnectar",totalnectar*2))
	
	queenbees = [(abstract_bee.to_json()[1:4],abstract_bee.to_json()[6]) for bee_id, abstract_bee in futureinflight.items() if abstract_bee.to_json()[0]=="QueenBee"]
	if queenbees:
		for qb,qs in queenbees:
			if gonna_lost(qb):
				states.append(("HiveLost",-2*qs))
			else:
				states.append(("centre",(-distance_between_hex_cells(qb[:2],[4,3])*0.1)))
				states.append(("indanger",-neighbour_tiles(qb[:2])))
	
	seeds = [abstract_bee.to_json()[1:3] for bee_id, abstract_bee in futureinflight.items() if abstract_bee.to_json()[0]=="Seed"]
	if seeds:
		for sd in seeds:
			states.append(("centre",(-distance_between_hex_cells(sd,[4,3])*0.3)))
			states.append(("indanger",-neighbour_tiles(sd)*0.01))
	
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
	
	if lost:
		for lostbee in lost:
			lb = inflight[lostbee]
			if lb[0] == "QueenBee":
				states.append(("HiveLost",-2*lb[6]))
			elif lb[0] == "Bee" and (greedy or lb[6]>=3):
				states.append(("waste",-2*lb[6]))
			elif lb[0] == "Seed":
				states.append(("waste",0))
	
	if landed:
		for landedbee in landed:
			homebee = inflight[landedbee]
			if homebee[0] == "QueenBee":
				states.append(("HiveLost",2*homebee[6]))
			elif homebee[6]==5:
				states.append(("fullhome",0))
			else:
				states.append(("home",4*homebee[6]))
	
	
	totalscore=0
	for state,importance in states:
		totalscore += scores[state]+importance
	#print(move,states,totalscore)
	return totalscore
	
def detail_advance(board_width, board_height, hives, flowers, inflight, turn_num, cmd):
	board = Board(game_params=DEFAULT_GAME_PARAMETERS,
				board_width=board_width,
				board_height=board_height,
				hives=[Hive(*i) for i in hives],
				flowers=[Flower.from_json(i) for i in flowers],
				neighbours={'N':1, 'S':1, 'E':1, 'W':1, 'NW':1, 'SE':1, },
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
		if (end is not None and (x,y) == tuple(end)) or x in [-1,8] or y in [-1,8]:
			return tiles
		tiles.append([x,y])
	return tiles

def good_address(cell):
	x,y = cell
	return x not in [0,7] and y not in [0,7] and (x in [1,6] or y in [1,6])

def around_core(beepos,core):
	x,y = core
	surroundings = [[x-1,y],[x+1,y],[x,y+1],[x,y-1]]
	if x%2==0:
		surroundings += [[x-1,y+1],[x+1,y+1]]
	else:
		surroundings += [[x-1,y-1],[x+1,y-1]]
	return beepos in surroundings

def neighbour_tiles(beexy):
	x,y = beexy
	surroundings = [[x-1,y],[x+1,y],[x,y+1],[x,y-1]]
	if x%2==0:
		surroundings += [[x-1,y+1],[x+1,y+1]]
	else:
		surroundings += [[x-1,y-1],[x+1,y-1]]
	return len([(nx,ny) for nx,ny in surroundings if nx==-1 or nx==8 or ny==-1 or ny==8])

def gonna_lost(bee):
	x,y,dr = bee
	return ([x,y] in [[0,7],[7,0]]) or (dr == 0 and x%2==0 and y==7) or (dr == 180 and x%2==1 and y==0) or bee in [[0,6,-60],[7,1,120],[7,7,60],[0,0,-120]]