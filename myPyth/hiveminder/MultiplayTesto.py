import unittest
from algos.MyKeepg_algo import *

game_param = dict([('launch_probability', 0.2), ('initial_energy', 10), ('dead_bee_score_factor', -3),
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
	
	def test01(self):
		hs = [[0,7,50]]
		fs = []
		ifl = {'queen': ['QueenBee', 5, 0, 180, 10,game_param, 0]}
		result = myKeepGood_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		self.assertEqual(result['command'],'create_hive')
	
	def test01_2(self):
		hs = [[0,7,50]]
		fs = []
		ifl = {'queen': ['QueenBee', 1, 2, 180, 10,game_param, 0]}
		result = myKeepGood_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		self.assertEqual(result['command'],'create_hive')
		
	def test01_3(self):
		hs = [[0,7,50]]
		fs = []
		ifl = {'queen': ['QueenBee', 4, 3, 180, 10,game_param, 0]}
		result = myKeepGood_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		self.assertNotEqual(result['command'],'create_hive')
		
	def test01_4(self):
		hs = [[3,3,50],[4,2,50],[5,3,50]]
		fs = []
		ifl = {'queen': ['QueenBee', 4, 3, 180, 10,game_param, 0],'bee1':['Bee', 2, 3, 180, 10,game_param, 5],'bee1':['Bee', 2, 2, 0, 10,game_param, 5]}
		result = myKeepGood_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		self.assertEqual(result['command'],'create_hive')
	
	def test01_5(self):
		hs = [[3,3,50],[5,3,50]]
		fs = [[4,2,game_param,1,0,300],[4,1,game_param,1,0,300],[4,0,game_param,1,0,300],[4,7,game_param,1,0,300],[4,6,game_param,1,0,300],[4,5,game_param,1,0,300],[4,4,game_param,1,0,300],[4,3,game_param,1,0,300]]
		ifl = {'queen': ['QueenBee', 4, 3, 180, 10,game_param, 0],'bee1':['Bee', 4, 1, 0, 10,game_param, 0]}
		result = myKeepGood_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		self.assertEqual(result['entity'],'bee1')
	
	def test02_1(self):
		hs = [[0,7,50]]
		fs = []
		ifl = {'bee1': ['Bee', 4, 2, 180, 10,game_param, 0],'bee2':['Bee', 4, 1, 0, 10,game_param, 0]}
		result = myKeepGood_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		self.assertNotEqual(result,None)
		
	def test02_2(self):
		hs = [[0,7,50]]
		fs = []
		ifl = {'bee1': ['Bee', 4, 3, 180, 10,game_param, 0],'bee2':['Bee', 4, 1, 0, 10,game_param, 0]}
		result = myKeepGood_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		self.assertNotEqual(result,None)
		
	def test02_3(self):
		hs = [[0,7,50]]
		fs = []
		ifl = {'bee1': ['Bee', 5, 0, -120, 10,game_param, 0]}
		result = myKeepGood_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		self.assertEqual(result['command'],-60)
		
	def test02_4(self):
		hs = [[0,7,50]]
		fs = []
		ifl = {'bee1': ['Bee', 5, 0, 120, 4,game_param, 5]}
		result = myKeepGood_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		self.assertEqual(result,None)
		
	def test02_5(self):
		hs = [[0,7,50]]
		fs = [[2,2,game_param,1,0,300],[3,2,game_param,1,0,300]]
		ifl = {'bee1': ['Bee', 4, 3, 180, 10,game_param, 0],'bee2': ['Bee', 4, 1, 0, 10,game_param, 0]}
		result = myKeepGood_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		self.assertEqual(result['command'],-60)
		
	def test02_6(self):
		hs = [[5,3,50]]
		fs = [[4,2,game_param,1,0,300],[4,1,game_param,1,0,300]]
		ifl = {'bee1': ['Bee', 4, 3, 180, 10,game_param, 5],'bee2': ['Bee', 2, 3, 180, 10,game_param, 0],'bee3': ['Bee', 2, 1, 0, 10,game_param, 0]}
		result = myKeepGood_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		self.assertNotEqual(result['entity'],'bee1')
		
	def test02_7(self):
		hs = [[0,7,50]]
		fs = [[4,0,game_param,1,0,300]]
		ifl = {'bee1': ['Bee', 4, 1, 180, 10,game_param, 0]}
		result = myKeepGood_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		self.assertNotEqual(result,None)
		
	def test03_1(self):
		hs = [[5,3,50]]
		fs = [[4,2,game_param,1,0,300]]
		ifl = {'bee1': ['Bee', 4, 3, 180, 10,game_param, 4]}
		result = myKeepGood_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		self.assertEqual(result,None)
		
	def test03_2(self):
		hs = [[5,3,50]]
		fs = []
		ifl = {'bee1': ['Bee', 4, 3, 180, 10,game_param, 5]}
		result = myKeepGood_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		self.assertEqual(result['command'],120)
		
	def test03_3(self):
		hs = [[6,2,50],[0,1,50]]
		fs = [[1,2,game_param,1,0,300]]
		ifl = {'bee1': ['Bee', 4, 3, 180, 10,game_param, 5]}
		result = myKeepGood_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		self.assertEqual(result['command'],-120)
		
	def test03_4(self):
		hs = [[5,3,50]]
		fs = []
		ifl = {'bee1': ['Bee', 4, 3, 180, 10,game_param, 0]}
		result = myKeepGood_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		self.assertEqual(result,None)
		
	def test03_5(self):
		hs = [[5,3,50]]
		fs = []
		ifl = {'bee1': ['Bee', 4, 3, 180, 0,game_param, 0]}
		result = myKeepGood_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		self.assertEqual(result['command'],120)
		
	def test03_6(self):
		hs = [[5,3,50]]
		fs = [[4,2,game_param,1,0,300],[4,3,game_param,1,0,300],[4,4,game_param,1,0,300]]
		ifl = {'bee1': ['Bee', 4, 3, 180, 10,game_param, 0],'bee2': ['Bee', 4, 2, 0, 10,game_param, 0]}
		result = myKeepGood_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		self.assertEqual(result['command'],-120)
		
	def test03_7(self):
		hs = [[0,7,50]]
		fs = [[4,7,game_param,1,0,300],[4,6,game_param,1,0,300],[4,5,game_param,1,0,300],[6,2,game_param,1,0,300]]
		ifl = {'bee1': ['Bee', 4, 3, 180, 10,game_param, 5]}
		result = myKeepGood_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		self.assertNotEqual(result,None)
		
	def test04_1(self):
		hs = [[0,7,50]]
		fs = [[4,2,game_param,1,0,300]]
		ifl = {'seedo': ['Seed', 4, 3, 180]}
		result = myKeepGood_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		self.assertEqual(result['command'],"flower")
		
	def test04_2(self):
		hs = [[0,7,50]]
		fs = [[2,3,game_param,1,0,300]]
		ifl = {'seedo': ['Seed', 3, 3, 180]}
		result = myKeepGood_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		self.assertEqual(result['command'],120)
		
	def test04_3(self):
		hs = [[0,7,50]]
		fs = []
		ifl = {'seed1': ['Seed', 7, 4, 120],'seed2': ['Seed', 7, 2, 0]}
		result = myKeepGood_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		self.assertEqual(result['command'],"flower")
		
	def test04_4(self):
		hs = [[0,7,50]]
		fs = [[4,3,game_param,1,0,300]]
		ifl = {'seed1': ['Seed', 3, 4, 180],'seed2': ['Seed', 3, 3, 180]}
		result = myKeepGood_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		self.assertTrue(result['command']<0)
		
if __name__ == '__main__':
	unittest.main()