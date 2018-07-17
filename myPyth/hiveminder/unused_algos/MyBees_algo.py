from hiveminder import algo_player
from hiveminder.utils import *

new_headings = {0: [60, -60],
	60: [120, 0],
	120: [180, 60],
	180: [-120, 120],
	- 120: [-60, 180],
	- 60: [0, -120], }


@algo_player(name="MyBees",
			description="great")
def example_algo(board_width, board_height, hives, flowers, inflight, crashed,
					lost_volants, received_volants, landed, scores, player_id, game_id, turn_num):
	
	if inflight:
		
		hive_locations = [hive[:2] for hive in hives]
		flower_locations = [flower[:2] for flower in flowers]
		filled_tiles = hive_locations + flower_locations
		
		queen_bee = [(i,b) for i,b in inflight.items() if b[0]=="QueenBee"]

		if queen_bee:
			iqb = queen_bee[0][0]
			qb = queen_bee[0][1]
			if qb[1:3] not in filled_tiles:
				avedis = average_distance(qb[1:3], hive_locations)
				if avedis<2:
					return dict(entity=iqb, command=new_headings[qb[3]][0])
				else:
					return dict(entity=iqb, command="create_hive")

		seed = [(i,b) for i,b in inflight.items() if b[0]=="Seed"]

		if seed:
			isd = seed[0][0]
			sd = seed[0][1]
			if sd[1:3] not in filled_tiles:
				return dict(entity=isd, command="flower")

		for bee_id, bee in inflight.items():
			if bee[0] == "Bee":
				if bee[6] > 0 and not is_on_course_with(bee[1:3], bee[3], hive_locations[0]):
					current_heading = bee[3]
					for h in hive_locations:
						if is_on_course_with(bee[1:3], new_headings[current_heading][0], h):
							return dict(entity=bee_id, command=new_headings[current_heading][0])
						elif is_on_course_with(bee[1:3], new_headings[current_heading][1], h):
							return dict(entity=bee_id, command=new_headings[current_heading][1])
				elif bee[6] == 0 and not is_on_course_with(bee[1:3], bee[3], flower_locations[0]):
					current_heading = bee[3]
					for f in flower_locations:
						if is_on_course_with(bee[1:3], new_headings[current_heading][0], f):
							return dict(entity=bee_id, command=new_headings[current_heading][0])
						elif is_on_course_with(bee[1:3], new_headings[current_heading][1], f):
							return dict(entity=bee_id, command=new_headings[current_heading][1])

		# Otherwise, do nothing"""
		return None


def average_distance(cell1, cells):
	dis = [distance_between_hex_cells(cell1,cell2) for cell2 in cells]
	return sum(dis)/len(dis)