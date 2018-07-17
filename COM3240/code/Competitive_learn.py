import numpy as np
import numpy.matlib
import math
import matplotlib.pyplot as plt
import gzip
import random
from matplotlib import animation

file_name = 'train-images-idx3-ubyte'
train_data = gzip.GzipFile(file_name+'.gz', 'rb')
train_content = train_data.read()

#extract data from file
pixels = 28
train_block = list(train_content[16:])
train = np.reshape(train_block, (-1, pixels*pixels))

train_sample = train#[:5000]
[m,n]=np.shape(train_sample) 

#parameters
eta = 0.05
winit = 1
alpha = 0.999
tmax = 40000
digits = 15

#initially random generate the weight vector
W = winit*np.random.rand(digits,n)

#normalise the weights
normW = np.sqrt(np.diag(W.dot(W.T)))
normW = normW.reshape(digits,-1)

W = W/normW

#counters
counter = np.zeros(digits)
p_counter = np.zeros(digits)
wCount = np.ones((1,tmax+1))*0.25
average_dw = []

yl = int(round(digits/5))
if digits % 5 != 0:
	yl += 1
	
fig_neurs, axes_neurs = plt.subplots(yl,5,figsize=(10,8))

pic_name = 'results/step_'

for t in range(1,tmax+1):
	i = math.ceil(m*np.random.rand())-1
	
	#random pick a train sample from data
	x = train_sample[i]
	#normalize the sample
	x = x/np.sqrt(x.dot(x))
	
	#score of similarity
	h = W.dot(x)
	
	#choose the one that is most similar to input
	k=np.argmax(h)
	counter[k] += 1
	
	#compute the weight change
	dw = eta*((x.T) - W[k,:])
	average_dw.append(np.average(dw))
	
	wCount[0,t] = wCount[0,t-1]*(alpha + dw.dot(dw.T)*(1-alpha))
	
	W[k,:] = W[k,:]+dw

average_dw = np.array(average_dw)

#plot the weight
for i in range(15):
	axes_neurs[i//5,i%5].imshow(W[i].reshape((28,28)),interpolation='nearest',cmap='inferno')
plt.show()

#save the weight change to a file
np.savetxt('weight_change1.csv',average_dw,delimiter=",")