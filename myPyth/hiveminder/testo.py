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



def test1(): #e=bee1 or (e=bee3 c=-60)
	hs = [[0, 7, 50]]
	fs = []
	ifl = {'bee1': ['Bee', 3, 5, 180, 73,game_param, 0],'bee2': ['Bee', 5, 4, -120, 122, game_param, 0],'bee3': ['Bee', 3, 3, 0, 97,game_param, 0]}
	return myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
print("test1",test1(),"bee1 or bee3,-60\n")
	
def test2(): #e=bee2 c=-60
	hs = [[0, 7, 50]]
	fs = [[1,4,game_param,1,0,300],[2,3,game_param,1,0,300]]
	ifl= {'bee1': ['Bee', 3, 5, 180, 73,game_param, 0],	'bee2': ['Bee', 3, 3, 0, 97,game_param, 0]}
	return myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
print("test2",test2(),"bee2,-60\n")

def test3(): #bee1,120
	hs = [[4, 2, 50]]
	fs = [[3,2,game_param,1,0,300],[3,1,game_param,1,0,300],[3,0,game_param,1,0,300],[3,7,game_param,1,0,300],[3,6,game_param,1,0,300],[3,5,game_param,1,0,300],[3,4,game_param,1,0,300],[3,3,game_param,1,0,300]]
	ifl = {'bee1': ['Bee', 3, 3, 180, 73,game_param, 4]}
	return myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
print("test3",test3(),"bee1,120\n")

def test3_2():
	hs = [[6, 1, 50]]
	fs = [[1,2,game_param,1,0,300],[0,1,game_param,1,0,300],[7,1,game_param,1,0,300]]
	ifl = {'bee1': ['Bee', 3, 3, 180, 73,game_param, 4]}
	return myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
print("test3.2",test3_2(),"bee1,120\n")

def test3_3():
	hs = [[6, 1, 50],[3, 0, 50]]
	fs = [[5,2,game_param,1,0,300],[4,2,game_param,1,0,300]]
	ifl = {'bee1': ['Bee', 3, 3, 180, 73,game_param, 5]}
	return myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
print("test3.3",test3_3(),"None\n")

def test4(): #bee1,120
	hs = [[4, 2, 50]]
	fs = [[3,2,game_param,1,0,300],[3,1,game_param,1,0,300],[3,0,game_param,1,0,300],[3,7,game_param,1,0,300],[3,6,game_param,1,0,300],[3,5,game_param,1,0,300],[3,4,game_param,1,0,300],[3,3,game_param,1,0,300]]
	ifl = {'bee1': ['Bee', 3, 3, 180, 73,game_param, 5],'bee2': ['Bee', 3, 2, 0, 73,game_param, 0]}
	return myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)
print("test4",test4(),"bee1,120\n")

def test5(): #bee1,120
	hs = [[4, 2, 50]]
	fs = []
	ifl = {'bee1': ['Bee', 3, 3, 180, 73,game_param, 4]}
	return myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)

def test6_1(): #bee1,create_hive
	hs = [[3, 4, 50]]
	fs = []
	ifl = {'bee1': ['QueenBee', 3, 3, 180, 73,game_param, 4]}
	return myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)

def test6_2(): #bee1,flower
	hs = [[4, 2, 50]]
	fs = []
	ifl = {'bee1': ['Seed', 3, 3, 180]}
	return myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)

def test7():
	hs = [[4, 2, 50]]
	fs = [[3,2,game_param,1,0,300],[3,1,game_param,1,0,300],[3,0,game_param,1,0,300],[3,7,game_param,1,0,300],[3,6,game_param,1,0,300],[3,5,game_param,1,0,300],[3,4,game_param,1,0,300],[3,3,game_param,1,0,300]]
	ifl = {'bee1': ['Bee', 3, 3, 180, 73,game_param, 4]}
	return myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)

def test8():
	hs = [[4, 2, 50]]
	fs = [[3,2,game_param,1,0,300],[3,1,game_param,1,0,300],[3,0,game_param,1,0,300],[3,7,game_param,1,0,300],[3,6,game_param,1,0,300],[3,5,game_param,1,0,300],[3,4,game_param,1,0,300],[3,3,game_param,1,0,300]]
	ifl = {'bee1': ['Bee', 3, 3, 180, 73,game_param, 4]}
	return myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)

def test9():
	hs = [[4, 2, 50]]
	fs = [[3,2,game_param,1,0,300],[3,1,game_param,1,0,300],[3,0,game_param,1,0,300],[3,7,game_param,1,0,300],[3,6,game_param,1,0,300],[3,5,game_param,1,0,300],[3,4,game_param,1,0,300],[3,3,game_param,1,0,300]]
	ifl = {'bee1': ['Bee', 3, 3, 180, 73,game_param, 4]}

def test10():
	hs = [[4, 2, 50]]
	fs = [[3,2,game_param,1,0,300],[3,1,game_param,1,0,300],[3,0,game_param,1,0,300],[3,7,game_param,1,0,300],[3,6,game_param,1,0,300],[3,5,game_param,1,0,300],[3,4,game_param,1,0,300],[3,3,game_param,1,0,300]]
	ifl = {'bee1': ['Bee', 3, 3, 180, 73,game_param, 4]}
	return myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)

def test11():
	hs = [[4, 2, 50]]
	fs = [[3,2,game_param,1,0,300],[3,1,game_param,1,0,300],[3,0,game_param,1,0,300],[3,7,game_param,1,0,300],[3,6,game_param,1,0,300],[3,5,game_param,1,0,300],[3,4,game_param,1,0,300],[3,3,game_param,1,0,300]]
	ifl = {'bee1': ['Bee', 3, 3, 180, 73,game_param, 4]}
	return myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)

def test12():
	hs = [[4, 2, 50]]
	fs = [[3,2,game_param,1,0,300],[3,1,game_param,1,0,300],[3,0,game_param,1,0,300],[3,7,game_param,1,0,300],[3,6,game_param,1,0,300],[3,5,game_param,1,0,300],[3,4,game_param,1,0,300],[3,3,game_param,1,0,300]]
	ifl = {'bee1': ['Bee', 3, 3, 180, 73,game_param, 4]}
	return myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)

def test13():
	hs = [[4, 2, 50]]
	fs = [[3,2,game_param,1,0,300],[3,1,game_param,1,0,300],[3,0,game_param,1,0,300],[3,7,game_param,1,0,300],[3,6,game_param,1,0,300],[3,5,game_param,1,0,300],[3,4,game_param,1,0,300],[3,3,game_param,1,0,300]]
	ifl = {'bee1': ['Bee', 3, 3, 180, 73,game_param, 4]}
	return myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)

def test14():
	hs = [[4, 2, 50]]
	fs = [[3,2,game_param,1,0,300],[3,1,game_param,1,0,300],[3,0,game_param,1,0,300],[3,7,game_param,1,0,300],[3,6,game_param,1,0,300],[3,5,game_param,1,0,300],[3,4,game_param,1,0,300],[3,3,game_param,1,0,300]]
	ifl = {'bee1': ['Bee', 3, 3, 180, 73,game_param, 4]}
	return myNewGardener_algo(bw,bh,hs,fs,ifl,cr,lo,rc,ld,sc,pi,gi,tn)


