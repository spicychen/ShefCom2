import numpy as np
import matplotlib.pyplot as plt

row = 3
col = 5

W = np.genfromtxt('conscience_weight.csv',delimiter=',')
fig_neurs, axes_neurs = plt.subplots(row,col,figsize=(10,8))

for i in range(row*col):
	a = W[i].reshape(W[i].shape[0],-1)
	axes_neurs[i//5,i%5].imshow(a.dot(a.T),interpolation='nearest',cmap='inferno')

plt.show()
