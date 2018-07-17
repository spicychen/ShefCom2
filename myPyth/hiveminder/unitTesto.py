import unittest
import math
from algos.MyGarden_algo import *
	
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
	
	def test01(self): #test bee avoid crashing
		hs = [[0, 7, 50]]
		fs = []
		ifl = {'bee1': ['Bee', 3, 5, 180, 73,game_param, 0],'bee2': ['Bee', 5, 4, -120, 122, game_param, 0],'bee3': ['Bee', 3, 3, 0, 97,game_param, 0]}
		#print("\n\n\n\n\n\n")
		result = myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		#print("test1\n\n\n\n\n\n")
		self.assertTrue(result['entity']=="bee1" or (result['entity']=="bee3" and result['command']==-60))
	
		
	def test02(self): #bee tend flower
		hs = [[0, 7, 50]]
		fs = [[1,4,game_param,1,0,300],[2,3,game_param,1,0,300]]
		ifl= {'bee1': ['Bee', 3, 5, 180, 73,game_param, 0],	'bee2': ['Bee', 3, 3, 0, 97,game_param, 0]}
		result = myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		#print("test2\n\n\n")
		self.assertEqual(result['entity'],"bee2")
		self.assertEqual(result['command'],-60)
	#("test2",test2(),"bee2,-60\n")
	
	def test03(self): #full bee go home
		hs = [[4, 2, 50]]
		fs = [[3,2,game_param,1,0,300],[3,1,game_param,1,0,300],[3,0,game_param,1,0,300],[3,7,game_param,1,0,300],[3,6,game_param,1,0,300],[3,5,game_param,1,0,300],[3,4,game_param,1,0,300],[3,3,game_param,1,0,300]]
		ifl = {'bee1': ['Bee', 3, 3, 180, 73,game_param, 4]}
		print("\n\n\n\n\n\n")
		result = myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		print("test3\n\n\n")
		#print("test3\n\n\n")
		self.assertEqual(result,None)
	#("test3",test3(),"bee1,120\n")
	
	def test03_2(self):#full bee go home
		hs = [[6, 1, 50]]
		fs = [[1,2,game_param,1,0,300],[0,1,game_param,1,0,300],[7,1,game_param,1,0,300]]
		ifl = {'bee1': ['Bee', 3, 3, 180, 73,game_param, 4]}
		result = myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		#print("test3_2\n\n\n")
		self.assertEqual(result['entity'],"bee1")
		self.assertEqual(result['command'],120)
	
	def test03_2_2(self):#not_enough_bee go work
		hs = [[6, 1, 50]]
		fs = [[1,2,game_param,1,0,300],[0,1,game_param,1,0,300],[7,1,game_param,1,0,300]]
		ifl = {'bee1': ['Bee', 3, 3, 180, 73,game_param, 3]}
		result = myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		#print("test3_2\n\n\n")
		self.assertEqual(result['entity'],"bee1")
		self.assertEqual(result['command'],-120)
	#("test3.2",test3_2(),"bee1,120\n")
	
	def test03_3(self):#dont change mind
		hs = [[6, 1, 50],[3, 0, 50]]
		fs = []
		ifl = {'bee1': ['Bee', 3, 3, 180, 73,game_param, 5]}
		result = myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		self.assertEqual(result,None)
	#("test3.3",test3_3(),"None\n")
	
	def test03_4(self):#lazybee dont go home
		hs = [[4,3, 50],[4,2, 50]]
		fs = []
		ifl = {'bee1': ['Bee', 3, 3, 120, 73,game_param, 0]}
		#print("\n\n\n\n\n\n")
		result = myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		#print("test3_4\n\n\n")
		self.assertEqual(result['command'],180)
	
	def test04(self): #bee home bee work
		hs = [[4, 2, 50]]
		fs = [[3,2,game_param,1,0,300],[3,1,game_param,1,0,300],[3,0,game_param,1,0,300],[3,7,game_param,1,0,300],[3,6,game_param,1,0,300],[3,5,game_param,1,0,300],[3,4,game_param,1,0,300],[3,3,game_param,1,0,300]]
		ifl = {'bee1': ['Bee', 3, 3, 180, 73,game_param, 5],'bee2': ['Bee', 3, 2, 0, 73,game_param, 0]}
		result = myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		#print("test2\n\n\n")
		self.assertEqual(result['entity'],"bee1")
		self.assertEqual(result['command'],120)
	#("test4",test4(),"bee1,120\n")
	
	"""def test05(self): #low bee home because no flower
		hs = [[4, 2, 50]]
		fs = []
		ifl = {'bee1': ['Bee', 3, 3, 180, 73,game_param, 2]}
		#print("\n\n\n\n\n\n")
		result = myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		#print("test5\n\n\n")
		self.assertEqual(result['entity'],"bee1")
		self.assertEqual(result['command'],120)"""
	
	def test06_1(self):#queen_plant
		hs = [[0, 7, 50],[3, 4, 50]]
		fs = []
		ifl = {'bee1': ['QueenBee', 3, 3, 180, 73,game_param, 4]}
		#print("\n\n\n\n\n\n")
		result = myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		#print("test6\n\n\n")
		self.assertEqual(result['entity'],"bee1")
		self.assertEqual(result['command'],"create_hive")
	
	def test06_1_2(self):#queen_move to proper space
		hs = [[3, 4, 50]]
		fs = []
		ifl = {'bee1': ['QueenBee', 2, 3, 180, 73,game_param, 4]}
		#print("\n\n\n\n\n\n")
		result = myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		#print("test6\n\n\n")
		self.assertEqual(result['entity'],"bee1")
		self.assertTrue(result['command']==-120 or result['command']==120)
		
	def test06_1_3(self):#queen not plant around core
		hs = [[3, 4, 50]]
		fs = []
		ifl = {'bee1': ['QueenBee', 3, 3, 180, 73,game_param, 4]}
		#print("\n\n\n\n\n\n")
		result = myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		#print("test6\n\n\n")
		self.assertEqual(result['entity'],"bee1")
		self.assertTrue(result['command']==-120 or result['command']==120)
	
	def test06_2(self): #seedo plant
		hs = [[4, 2, 50]]
		fs = []
		ifl = {'bee1': ['Seed', 2, 3, 180]}
		#print("\n\n\n\n\n\n")
		result = myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		#print("test6\n\n\n")
		self.assertEqual(result['entity'],"bee1")
		self.assertEqual(result['command'],"flower")
	
	def test06_2_2(self): #seed move to prop space
		hs = [[0, 7, 50]]
		fs = [[2,7,game_param,1,0,300]]
		ifl = {'bee1': ['Seed', 3, 3, 180]}
		#print("\n\n\n\n\n\n")
		result = myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		#print("test6.2.2\n\n\n")
		self.assertEqual(result['entity'],"bee1")
		self.assertEqual(result['command'],120)
	
	def test06_2_3(self): #seed move to less flower
		hs = [[0, 7, 50]]
		fs = [[2,7,game_param,1,0,300],[4,3,game_param,1,0,300],[4,2,game_param,1,0,300]]
		ifl = {'bee1': ['Seed', 3, 3, 180],'bee2': ['Seed', 4, 3, 180]}
		#print("\n\n\n\n\n\n")
		result = myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		#print("test6.2.3\n\n\n")
		self.assertEqual(result['entity'],"bee1")
		self.assertEqual(result['command'],-120)
	
	def test06_2_4(self): #seedo move to less f + s
		hs = [[0, 7, 50]]
		fs = [[2,7,game_param,1,0,300],[4,4,game_param,1,0,300],[4,5,game_param,1,0,300],[4,6,game_param,1,0,300]]
		ifl = {'bee1': ['Seed', 3, 3, 180],'bee2': ['Seed', 4, 4, 180],'bee3': ['Seed', 4, 5, 180],'bee4': ['Seed', 4, 6, 180]}
		#print("\n\n\n\n\n\n")
		result = myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		#print("test6.2.3\n\n\n")
		self.assertEqual(result['entity'],"bee1")
		self.assertEqual(result['command'],-120)
	
	def test07(self):#queen shall not die
		hs = [[4, 2, 50],[2,2,50]]
		fs = [[3,2,game_param,1,0,300],[3,1,game_param,1,0,300],[3,0,game_param,1,0,300],[3,7,game_param,1,0,300],[3,6,game_param,1,0,300],[3,5,game_param,1,0,300],[3,4,game_param,1,0,300],[3,3,game_param,1,0,300]]
		ifl = {'bee1': ['QueenBee', 3, 3, 180, 73,game_param, 4],'bee2': ['Bee', 3, 1, 0, 73,game_param, 4]}
		#print("\n\n\n\n\n\n")
		result = myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		#print("test7\n\n\n\n\n\n")
		self.assertEqual(result['entity'],"bee2")
		#print("test2\n\n\n")
	
	"""def test8(self):
		hs = [[4, 2, 50]]
		fs = [[3,2,game_param,1,0,300],[3,1,game_param,1,0,300],[3,0,game_param,1,0,300],[3,7,game_param,1,0,300],[3,6,game_param,1,0,300],[3,5,game_param,1,0,300],[3,4,game_param,1,0,300],[3,3,game_param,1,0,300]]
		ifl = {'bee1': ['Bee', 3, 3, 180, 73,game_param, 4]}
		result = myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		#print("test2\n\n\n")"""
	
	def test09(self):
		hs = [[0, 7, 50],[3, 2, 50]]
		fs = [[2,2,game_param,1,0,300],[2,1,game_param,1,0,300],[2,0,game_param,1,0,300],[2,7,game_param,1,0,300],[2,6,game_param,1,0,300],[2,5,game_param,1,0,300],[2,4,game_param,1,0,300],[2,3,game_param,1,0,300]]
		ifl = {'bee1': ['Bee', 2, 2, 180, 73,game_param, 5],'bee2': ['Bee', 2, 1, 0, 73,game_param, 5],'bee3': ['QueenBee', 3, 3, 180, 73,game_param, 4]}
		#print("\n\n\n\n\n\n")
		result = myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		#print("test9\n\n\n\n\n\n")
		self.assertEqual(result['command'],"create_hive")
		#print("test2\n\n\n")
	
	"""def test10(self):
		hs = [[4, 2, 50]]
		fs = [[3,2,game_param,1,0,300],[3,1,game_param,1,0,300],[3,0,game_param,1,0,300],[3,7,game_param,1,0,300],[3,6,game_param,1,0,300],[3,5,game_param,1,0,300],[3,4,game_param,1,0,300],[3,3,game_param,1,0,300]]
		ifl = {'bee1': ['Bee', 3, 3, 180, 73,game_param, 4]}
		result = myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		#print("test2\n\n\n")"""
	
	def test11(self):
		hs = [[0, 7, 50]]
		fs = [[4,2,game_param,1,0,300],[5,3,game_param,1,0,300]]
		ifl = {'bee1': ['Bee', 3, 3, 180, 73,game_param, 0],'bee2': ['Bee', 3, 2, 0, 73,game_param, 0],'bee3': ['Seed', 4, 2, 120]}
		#print("\n\n\n\n\n\n")
		result = myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		#print("test11\n\n\n\n\n\n")
		self.assertEqual(result['entity'],"bee2")
		self.assertEqual(result['command'],60)
		#print("test2\n\n\n")
	
	def test12(self):
		hs = [[0, 7, 50]]
		fs = []
		ifl = {'bee1': ['QueenBee', 2, 3, 180, 1,game_param, 5]}
		#print("\n\n\n\n\n\n")
		result = myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		#print("test12\n\n\n\n\n\n")
		self.assertEqual(result['command'],"create_hive")
		#print("test2\n\n\n")
	
	def test13(self):
		hs = [[0, 7, 50]]
		fs = [[2,2,game_param,1,0,300],[2,1,game_param,1,0,300],[2,0,game_param,1,0,300],[2,7,game_param,1,0,300],[2,6,game_param,1,0,300],[2,5,game_param,1,0,300],[2,4,game_param,1,0,300],[2,3,game_param,1,0,300]]
		ifl = {'bee1': ['Seed', 2, 3, 180],'bee2': ['Seed', 2, 2, 180]}
		#print("\n\n\n\n\n\n")
		result = myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		#print("test13\n\n\n\n\n\n")
		self.assertFalse(result is None)
		#print("test2\n\n\n")
	
	def test14(self):
		hs = [[0,7, 50]]
		fs = [[2,2,game_param,1,0,300],[2,1,game_param,1,0,300],[2,0,game_param,1,0,300],[4,7,game_param,1,0,300],[4,6,game_param,1,0,300],[4,5,game_param,1,0,300],[4,4,game_param,1,0,300]]
		ifl = {'bee1': ['Seed', 3, 3, 180]}
		#print("\n\n\n\n\n\n")
		result = myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		#print("test14\n\n\n\n\n\n")
		self.assertEqual(result['command'],-120)
		#print("test2\n\n\n")
	
	def test14_2(self):
		hs = [[0,7, 50]]
		fs = [[2,2,game_param,1,0,300],[2,1,game_param,1,0,300],[2,0,game_param,1,0,300],[4,7,game_param,1,0,300],[4,6,game_param,1,0,300],[4,5,game_param,1,0,300],[4,4,game_param,1,0,300]]
		ifl = {'bee1': ['Seed', 3, 3, 180],'bee2': ['Seed', 2, 2, 180],'bee3': ['Seed', 2, 1, 180]}
		#print("\n\n\n\n\n\n")
		result = myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		#print("test14\n\n\n\n\n\n")
		self.assertEqual(result['entity'],"bee1")
		self.assertEqual(result['command'],120)
	
	def test15(self):#bee dont cross crash
		hs = [[0,7, 50]]
		fs = []
		ifl = {'bee1': ['Bee', 3, 0, 180, 73,game_param, 5],'bee2': ['Bee', 3, 6, 0, 73,game_param, 5]}
		#print("\n\n\n\n\n\n")
		result = myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
		#print("test15\n\n\n\n\n\n")
		self.assertFalse(result is None)
if __name__ == '__main__':
	unittest.main()