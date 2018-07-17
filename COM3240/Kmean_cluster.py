import numpy as np
from scipy.cluster.vq import vq, kmeans2, whiten
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
from cycler import cycler

FinalData = np.genfromtxt('PCA_FinalData_03.csv',delimiter=',')

#principle components axis
x,y,z = 1,2,3

#sample 10000 data from the result
sampleFinalData = FinalData[25000:35000,(x-1,y-1,z-1)]

whitened_data = whiten(sampleFinalData)

codes = 10   #number of clusters

# quick kmean clustering method from scipy
codebook, labels = kmeans2(whitened_data, codes)

fig = plt.figure(figsize = (10,10))
ax = fig.gca(projection = '3d')

ax.set_prop_cycle(cycler('color',['blue', 'orange', 'green', 'red', 'purple', 'brown', 'pink', 'gray', 'olive', 'cyan']))

#print(len(labels))
for i in range(codes):
	predicted_label = (labels==i)
	xcomp = sampleFinalData[predicted_label,0].flatten()
	ycomp = sampleFinalData[predicted_label,1].flatten()
	zcomp = sampleFinalData[predicted_label,2].flatten()
	
	ax.plot(xcomp,ycomp,zcomp,'.')
	
ax.set_title('PC'+str(x)+',PC'+str(y)+' and PC'+str(z))
ax.set_xlabel(str(x)+'PC')
ax.set_ylabel(str(y)+'PC')
ax.set_zlabel(str(z)+'PC')

win = plt.gcf()
win.canvas.set_window_title('PC'+str(x)+'_PC'+str(y)+'_PC'+str(z))

plt.show()