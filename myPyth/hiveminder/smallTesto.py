import unittest
from algos.MyUnfriendlyNeighbours_algo import *

"""game_param = dict([('launch_probability', 0.2), ('initial_energy', 10), ('dead_bee_score_factor', -3),
('hive_score_factor', 200), ('flower_score_factor', 50), ('nectar_score_factor', 2),
('queen_bee_nectar_threshold', 100), ('bee_nectar_capacity', 5), ('bee_energy_boost_per_nectar', 25),
('flower_seed_visit_initial_threshold', 10), ('flower_seed_visit_subsequent_threshold', 10)
, ('flower_visit_potency_ratio', 10), ('flower_lifespan', 300), ('flower_lifespan_visit_impact', 10)
])

bw = 8
bh = 8
cr = {'headon': {}, 'collided': {}, 'exhausted': {}
, 'seeds': {}}
lo = {'d': ['Bee', 7, 4, -60, 6, game_param, 0]}
rc = {}
ld = {}
sc = [1000]
pi = 1
gi = "game"
tn = 150

class AlgoTesto(unittest.TestCase):
	def testy(self):
		hs = [[1,7,50],[2,6,50],[2,5,50],[0,5,50],[0,6,50]]
		fs = []
		ifl = {'seedo': ['Seed', 1, 5, 0]}
		result = myNecTar_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		self.assertEqual(result,None)

if __name__ == '__main__':
	unittest.main()

def fn():
	dn['a']=90
	print(dn)
dn = {"a":100}
for i in range(1):
	fn()"""
print(neighbour_tiles([0,7]))
print(neighbour_tiles([1,7]))