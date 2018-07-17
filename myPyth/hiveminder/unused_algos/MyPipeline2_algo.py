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
	
scores = {"waste": -3,
	"home": -1,
	"headhome": -1,
	"work": 1,
	"plant":15,
	"HiveLost":-25,
	"no_gardener:(":-10}

@algo_player(name="MyPipeline2",
	description="Best Firsto and planing the space in early stage. it may have low scores in 1000 step but high scores in more step")
def mypipeline2_algo(board_width, board_height, hives, flowers, inflight, crashed,
					lost_volants, received_volants, landed, scores, player_id, game_id, turn_num):
	if inflight:
		
		hive_locations = [hive[:2] for hive in hives]
		flowerll = [(flower[:2],flower[5]) for flower in flowers]
		filled_tiles = hive_locations + [location for location, life in flowerll]
		"""print("MyP2")
		print(hive_locations)
		print(flowerll)"""
		
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
	futurehives, futureflowers, futureinflight, crashed, landed, lost = detail_advance(board_width,board_height,hives,flowers,inflight,turn_num,move)
	futurehive_locations = [fh.to_json()[:2] for fh in futurehives]
	ffs = [f.to_json() for f in futureflowers]
	bees = [(bee_id, abstract_bee.to_json()) for bee_id, abstract_bee in futureinflight.items() if abstract_bee.to_json()[0]=="Bee"]
	core_hive = hives[0][:2]
	if bees:
		bees_states = [(bee[1:4],bee[6]) for bee_id, bee in bees]
		for bp,bs in bees_states:
			headhome = [h for h in futurehive_locations if is_on_course_with(bp[:2],bp[2],h)]
			gowork = [f for f in ffs if is_on_course_with(bp[:2],bp[2],f[:2])]
			if bp[:2] in [f[:2] for f in gowork]:
				states.append(("work",10-2*bs))
			if headhome:
				states.append(("headhome",2*bs))
				hx,hy = nearest_hex_cell(bp[:2],headhome)
				bx,by = bp[:2]
				flower_in_path = [ex for x,y,gp,po,vi,ex in gowork if (x>hx and x<bx) or (x<hx and x>bx)]
				for _ in flower_in_path:
					states.append(("work",10-2*bs))
			else:
				for _ in gowork:
					states.append(("work",10-2*bs))
	
	if move is not None:
		action = move["command"]
		bee_pos = inflight[move["entity"]][1:3]
		good_to_hive = good_address(bee_pos,core_hive)
		if (action == "create_hive" and (good_to_hive or turn_num>6000)) or (action == "flower" and not good_to_hive):
			states.append(("plant",3))
			
	if crashed:
		for crashedbee in crashed['collided']:
			deadbee = inflight[crashedbee]
			if deadbee[0]=="QueenBee":
				states.append(("HiveLost",-3))
			else:
				states.append(("waste",-2*deadbee[6]))
		for exhaustedbee in crashed['exhausted']:
			deadbee = inflight[exhaustedbee]
			if deadbee[0]=="QueenBee":
				states.append(("HiveLost",-3))
			else:
				states.append(("waste",-2*deadbee[6]))
	if landed:
		for landedbee in landed:
			homebee = inflight[landedbee]
			if homebee[0] == "QueenBee":
				states.append(("HiveLost",2*homebee[6]))
			else:
				states.append(("home",2*homebee[6]))
	
	totalscore = 0
	for state,importance in states:
		totalscore += scores[state]+importance
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
	board.visit_flowers(Random())
	landed = set(board.land_bees())
	crashed = {k: set(v) for k, v in board.detect_crashes().items()}
	
	return board.hives, board.flowers, board.inflight, crashed, landed, lost

def good_address(cell,home):
	return cell[0]%2 == home[0]%2
