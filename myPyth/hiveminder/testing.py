import math
from hiveminder.utils import *
from algos.MyGarden_algo import *

#print(flower_num(2,[2,5,3,5,4,6,6,2,6,3,5,3,3,7,0]))
#testingset = {'a','b'}
#example_tuple = [(4,8),(6,5)]

"""board_width = 8
board_height= 8
hives = [[4,1,0]]
flowers = [[6, 1, dict([('launch_probability', 0.2), ('initial_energy', 10), ('dead_bee_score_factor', -3), ('hive_score_factor', 200), ('flower_score_factor', 50), ('nectar_score_factor', 2), ('queen_bee_nectar_threshold', 100), ('bee_nectar_capacity', 5), ('bee_energy_boost_per_nectar', 25), ('flower_seed_visit_initial_threshold', 10), ('flower_seed_visit_subsequent_threshold', 10), ('flower_visit_potency_ratio', 10), ('flower_lifespan', 300), ('flower_lifespan_visit_impact', 10)]), 1, 0, 300]]
inflight = {'fbfafc6f-2ac9-4e4d-a3a7-e038c06547e6': ['Seed', 3, 2, 0]}
turn_num = 9
cmd = {"entity" : 'fbfafc6f-2ac9-4e4d-a3a7-e038c06547e6',
	"command" : "flower"
}"""
game_param = dict([('launch_probability', 0.2), ('initial_energy', 10), ('dead_bee_score_factor', -3),
('hive_score_factor', 200), ('flower_score_factor', 50), ('nectar_score_factor', 2),
('queen_bee_nectar_threshold', 100), ('bee_nectar_capacity', 5), ('bee_energy_boost_per_nectar', 25),
('flower_seed_visit_initial_threshold', 10), ('flower_seed_visit_subsequent_threshold', 10)
, ('flower_visit_potency_ratio', 10), ('flower_lifespan', 300), ('flower_lifespan_visit_impact', 10)
])
#print([1,2][1:])
board_width = 8
board_height = 8
hives = [[0, 7, 65]]
flowers = []
inflight = {'bee1': ['Bee', 3, 5, 180, 73,game_param, 0],'bee2': ['Bee', 5, 4, -120, 122, game_param, 0],
	'bee3': ['Bee', 3, 3, 0, 97,game_param, 0]}
crashed = {'headon': {}, 'collided': {}, 'exhausted': {}
, 'seeds': {}}
lost_volants = {'d': ['Bee', 7, 4, -60, 6, game_param, 0]}
received_volants = {"""'b': ['Bee', 7, 7, -120, 122,game_param, 5]"""}
landed = {}
scores = [545, 602, 444]
player_id = 2
game_id = "game"
turn_num = 155
print(myNewGardener_algo(board_width, board_height, hives, flowers, inflight, crashed,lost_volants, received_volants, landed, scores, player_id, game_id, turn_num))
inflight = {'bee1': ['Bee', 3, 5, 180, 73,game_param, 0],
	'bee2': ['Bee', 3, 3, 0, 97,game_param, 0]}
flowers = [[1,4,game_param,1,0,300],[2,3,game_param,1,0,300]]
print(myNewGardener_algo(board_width, board_height, hives, flowers, inflight, crashed,lost_volants, received_volants, landed, scores, player_id, game_id, turn_num))
flowers = [[3,2,game_param,1,0,300],[3,1,game_param,1,0,300],[3,0,game_param,1,0,300],[3,7,game_param,1,0,300],[3,6,game_param,1,0,300],[3,5,game_param,1,0,300],[3,4,game_param,1,0,300],[3,3,game_param,1,0,300]]
inflight = {'bee1': ['Bee', 3, 3, 180, 73,game_param, 4]}
hives = [[4, 2, 65]]
print(myNewGardener_algo(board_width, board_height, hives, flowers, inflight, crashed,lost_volants, received_volants, landed, scores, player_id, game_id, turn_num))
inflight = {'bee1': ['Bee', 3, 3, 180, 73,game_param, 5],'bee2': ['Bee', 3, 2, 0, 73,game_param, 0]}
print(myNewGardener_algo(board_width, board_height, hives, flowers, inflight, crashed,lost_volants, received_volants, landed, scores, player_id, game_id, turn_num))
flowers = []
inflight = {'bee1': ['Bee', 3, 3, 180, 73,game_param, 1]}
print(myNewGardener_algo(board_width, board_height, hives, flowers, inflight, crashed,lost_volants, received_volants, landed, scores, player_id, game_id, turn_num))
inflight = {'bee1': ['QueenBee', 3, 3, 180, 73,game_param, 1]}
print(myNewGardener_algo(board_width, board_height, hives, flowers, inflight, crashed,lost_volants, received_volants, landed, scores, player_id, game_id, turn_num))
hives = [[4,2,50],[2,2,50]]
flowers = [[3,2,game_param,1,0,300],[3,1,game_param,1,0,300],[3,0,game_param,1,0,300],[3,7,game_param,1,0,300],[3,6,game_param,1,0,300],[3,5,game_param,1,0,300],[3,4,game_param,1,0,300]]
inflight = {'bee1': ['QueenBee', 3, 3, 180, 73,game_param, 1],'bee2': ['Bee', 3, 1, 0, 73,game_param, 1]}
print(myNewGardener_algo(board_width, board_height, hives, flowers, inflight, crashed,lost_volants, received_volants, landed, scores, player_id, game_id, turn_num))
hives = [[4,2,50],[3,2,25],[2,2,75]]
flowers = []
inflight = {'bee1': ['Bee', 3, 3, 180, 73,game_param, 1]}







result = myNewGardener_algo(board_width, board_height, hives, flowers, inflight, crashed,lost_volants, received_volants, landed, scores, player_id, game_id, turn_num)
print(result)









