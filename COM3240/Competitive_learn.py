import numpy as np
import numpy.matlib
import math
import matplotlib.pyplot as plt
import gzip
import random
from matplotlib import animation

def output_func(weights, input_units):
	outputs = []
	for w in weights:
		outputs.append(1/sum((input_units-w)**2))
	return np.array(outputs)
	
def conditional_probability(outputs):
	out_sum = sum(outputs)
	p = [out/out_sum for out in outputs]
	return np.array(p)
	
def weights_update(learning_rate, entropy_counter1, entropy_counter2):
	delta_w = -learning_rate*entropy_counter1 + learning_rate*entropy_counter2
	return delta_w

def q_function(outputs, steps, inputs, weights):
	numerator = np.array([2*(inputs-w) for w in weights])
	denominator = steps*sum(1/outputs)
	return numerator/denominator
	
def ent_mul(a):
	return - a * np.log(a)

def entropy_function(a,b):
	return a.dot(np.log(b))
	
def entropy_centrol(a,e,q):
	return (np.log(a)-e).dot(q)

file_name = 'train-images-idx3-ubyte'
train_data = gzip.GzipFile(file_name+'.gz', 'rb')
train_content = train_data.read()

pixels = 28
train_block = list(train_content[16:])
train = np.reshape(train_block, (-1, pixels*pixels))

train_sample = train#[:5000]
#print(sum(train_sample[0]))
[m,n]=np.shape(train_sample)

eta = 0.05
winit = 1
alpha = 0.999
tmax = 40000
digits = 15
"""
x=train_sample[0]
x=x/np.sqrt(x.dot(x))
print(x.sum())
"""

W = winit*np.random.rand(digits,n)

normW = np.sqrt(np.diag(W.dot(W.T)))
normW = normW.reshape(digits,-1)

W = W/normW
#print(W[0])
counter = np.zeros(digits)
p_counter = np.zeros(digits)
entropy_counter1 = np.zeros(n)
entropy_counter2 = np.zeros(n)
wCount = np.ones((1,tmax+1))*0.25
average_dw = []

#plt.ion()
#plt.close('all')

yl = int(round(digits/5))
if digits % 5 != 0:
	yl += 1
	
fig_neurs, axes_neurs = plt.subplots(yl,5,figsize=(10,8))

#im = axes_neurs[0,0].imshow(W[0].reshape((28,28)),interpolation='nearest',cmap='inferno')

#def init():
#	im.set_data(W[0].reshape((28,28)))

pic_name = 'results/step_'

#for t in range(tmax):
	


for t in range(1,tmax+1):
#def animate(t):
	i = math.ceil(m*np.random.rand())-1
	
	#x = train_sample[i]/2437.2
	x = train_sample[i]
	x = x/np.sqrt(x.dot(x))
	#if t==500:
	#	print(x.sum())
	
	h = W.dot(x)#/digits
	k=np.argmax(h)
	"""
	v = output_func(W,x)
	con_p = conditional_probability(v)
	p_counter += con_p
	p = p_counter/t
	e = ent_mul(p)
	k = np.argmax(e)
	if t == tmax:
		print(p,e)
	
	q = q_function(v,t,x,W)
	entropy1 = entropy_function(con_p,p)
	en1_cent = entropy_centrol(p,entropy1,q)
	entropy_counter1 += en1_cent
	
	entropy2 = entropy_function(con_p,con_p)
	en2_cent = entropy_centrol(con_p,entropy2,q)
	entropy_counter2 += en2_cent
	
	delta_w = weights_update(eta,entropy_counter1,entropy_counter2)
	#print(delta_w)
	#h = h.reshape(h.shape[0],-1)
	
	#xi = np.random.rand(digits)/200
	#output = np.max(h+xi)
	
	#if t==400:
	#	print(h)
		#
		#print(normh)
	#	print(h+xi)
	#normh = h/h.sum()
	#k = np.argmax(h+xi)
	
	#k = np.random.choice(range(len(h)),1,p=normh) if (np.random.uniform()<0.0) else np.argmax(h)
	
	#if (np.random.uniform()<0.01):
	#print(t)"""
	
	counter[k] += 1
	
	dw = eta*((x.T) - W[k,:])
	average_dw.append(np.average(dw))
	
	wCount[0,t] = wCount[0,t-1]*(alpha + dw.dot(dw.T)*(1-alpha))
	
	W[k,:] = W[k,:]+dw
	#im.set_data(W[0].reshape((28,28)))
	#return im
	
	
#anim = animation.FuncAnimation(fig_neurs, animate, init_func=init, frames=10000, interval=1) 
"""	if(t%400==0):
		for i in range(15):
			axes_neurs[i//5,i%5].imshow(W[i].reshape((28,28)),interpolation='nearest',cmap='inferno')
	#plt.imshow()
		plt.savefig(pic_name+str(t)+'.png')
#plt.waitforbuttonpress();

expect_min = 1/digits*0.01

print(expect_min,sum(counter/t<expect_min))

for i in range(15):
	axes_neurs[i//5,i%5].imshow(W[i].reshape((28,28)),interpolation='nearest',cmap='inferno')
#plt.imshow()
plt.show()"""


average_dw = np.array(average_dw)
np.savetxt('weight_change1.csv',average_dw,delimiter=",")