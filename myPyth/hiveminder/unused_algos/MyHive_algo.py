from hiveminder.utils import *
from hiveminder.utils import _OtherBoard
from hiveminder._util import even_q_distance, _first
from random import Random
from hiveminder.board import Board, volant_from_json
from hiveminder.hive import Hive
from hiveminder.flower import Flower
from hiveminder.game_params import DEFAULT_GAME_PARAMETERS
from hiveminder.volant import Volant
from hiveminder import algo_player

new_headings = {0: [60, -60],
	60: [120, 0],
	120: [180, 60],
	180: [-120, 120],
	- 120: [-60, 180],
	- 60: [0, -120], }
	
scores = {"waste": -4,
	"poor": -2,
	"sweet": 4,
	"lazy": -1,
	"sweetheadhome": 2,
	"lazyheadhome": -1,
	"workhard": 1,
	"worka": 2,
	"goodpos":8,
	"savlower":5,}
@algo_player(name="MyHive",
	description="Best First")
def myhive_algo(board_width, board_height, hives, flowers, inflight, crashed,
					lost_volants, received_volants, landed, scores, player_id, game_id, turn_num):
	if inflight:
		
		hive_locations = [hive[:2] for hive in hives]
		flower_locations = [flower[:2] for flower in flowers]
		filled_tiles = hive_locations + flower_locations
		
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
	for bee_id, abstract_bee in futureinflight.items():
		bee = abstract_bee.to_json()
		if bee[0] == "Bee":
			if any([is_on_course_with(bee[1:3],bee[3],h) for h in futurehive_locations]):
				if bee[6]>0:
					states.append("sweetheadhome")
				else:
					states.append("lazyheadhome")
			for ff in futureflowers:
				f = ff.to_json()
				if is_on_course_with(bee[1:3],bee[3],f[:2]):
					if bee[6]>0:
						states.append("workhard")
					else:
						states.append("worka")
					expiresin = f[5]-turn_num
					if expiresin <10:
						states.append("savlower")
	
	if move is not None:
		action = move["command"]
		
		if action == "create_hive":
			fhc = centre(futurehive_locations)
		
			if abs(fhc[0] - board_width/2) <1.5:
				states.append("goodpos")
			if abs(fhc[1] - board_height/2) <1.5:
				states.append("goodpos")
		
		elif action == "flower":
			states.append("goodpos")
			
		
	if crashed:
		for crashedbee in crashed['collided']:
			if inflight[crashedbee][6]>0:
				states.append("waste")
			else:
				states.append("poor")
		for exhaustedbee in crashed['exhausted']:
			if inflight[exhaustedbee][6]>0:
				states.append("waste")
			else:
				states.append("poor")
	if landed:
		for landedbee in landed:
			if inflight[landedbee][6]>0:
				states.append("sweet")
			else:
				states.append("lazy")
	
	totalscore = 0
	for state in states:
		totalscore += scores[state]
		
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

def centre(cells):
	return [sum(cell)/len(cells) for cell in zip(*cells)]