import numpy as np
import numpy.matlib
import math
import matplotlib.pyplot as plt
import gzip
import random
from matplotlib import animation

def euclidian_dist(ws,x):
	d = [(w-x).dot(w-x) for w in ws]
	return np.array(d)

file_name = 'train-images-idx3-ubyte'
train_data = gzip.GzipFile(file_name+'.gz', 'rb')
train_content = train_data.read()

pixels = 28
train_block = list(train_content[16:])
train = np.reshape(train_block, (-1, pixels*pixels))

train_sample = train#[:5000]
[m,n]=np.shape(train_sample)

eta = 0.05
winit = 1
alpha = 0.999
tmax = 40000
digits = 15
B = 0.001
C = 7
W = winit*np.random.rand(digits,n)

normW = np.sqrt(np.diag(W.dot(W.T)))
normW = normW.reshape(digits,-1)

W = W/normW
#print(W[0])
counter = np.zeros(digits)

p_counter = np.zeros(digits)
wCount = np.ones((1,tmax+1))*0.25
average_dw = []

pic_name = 'consResults/step_'

for t in range(1,tmax+1):
	i = math.ceil(m*np.random.rand())-1
	
	#normalise the input
	x = train_sample[i]
	x = x/np.sqrt(x.dot(x))
	
	#euclidian distance, return list of double.
	d = euclidian_dist(W,x)
	
	#find the smallest distance between W and x
	j = np.argmax(-d)
	
	#first output
	y = np.zeros(digits)
	y[j] = 1
	
	#eligibility trace, B=0.001
	p_counter = p_counter + B*(y-p_counter)
	
	#bias for all units, C=7
	bi = C*(1/digits-p_counter)
	
	#add the bias to distance
	z = d-bi
	
	#choose the neuron with least biased distance
	k = np.argmax(-z)
	
	
	counter[k] += 1
	
	dw = eta*((x.T) - W[k,:])
	average_dw.append(np.average(dw))
	
	wCount[0,t] = wCount[0,t-1]*(alpha + dw.dot(dw.T)*(1-alpha))
	
	W[k,:] = W[k,:]+dw
	
average_dw = np.array(average_dw)
np.savetxt('conscienceData/conscience_weight_change.csv',average_dw,delimiter=",")
np.savetxt('conscienceData/conscience_weight.csv',W,delimiter=',')
np.savetxt('conscienceData/conscience_counter.csv',counter,delimiter=',')