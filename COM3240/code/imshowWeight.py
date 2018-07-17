import numpy as np
import matplotlib.pyplot as plt

row = 3
col = 5

W = np.genfromtxt('conscience_weight.csv',delimiter=',')
fig_neurs, axes_neurs = plt.subplots(row,col,figsize=(10,8))

for i in range(row*col):
	axes_neurs[i//5,i%5].imshow(W[i].reshape((28,28)),interpolation='nearest',cmap='inferno')
plt.show()