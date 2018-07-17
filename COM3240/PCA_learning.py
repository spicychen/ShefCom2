import numpy as np
import numpy.matlib
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
import gzip

file_name = 'train-images-idx3-ubyte'
train_data = gzip.GzipFile(file_name+'.gz', 'rb')
train_content = train_data.read()

pixels = 28
train_block = list(train_content[16:])
train = np.reshape(train_block, (-1, pixels*pixels))

#print(len(train[:,1]))
#image = np.reshape(train[4999,:],(pixels,pixels),order="F")
#print(c1)
#plt.imshow(image,cmap='inferno')
#plt.show()
#print(long(c[4:8]))



train_sample = train
#print(np.shape(train_sample))
[m,n]=np.shape(train_sample)
"""train_sample_t = np.zeros((m,n))
for i in range(len(train_sample)):
	tst = np.reshape(train_sample[i],(pixels,pixels),order="F")
	tst = np.reshape(tst,pixels*pixels)
	train_sample_t[i] = tst"""

normT = np.sqrt(np.diag(train_sample.dot(train_sample.T)))
#print(train_sample[0])
train_sample = train_sample.T/np.matlib.repmat(normT.T,n,1)
data = train_sample.T

nPC = 6

PCV = np.zeros((n,nPC))

meanData = np.matlib.repmat(data.mean(axis=0),m,1)
data = data - meanData

C = np.cov(data.T)

eigen_val,eigen_vec = np.linalg.eigh(C)

idx = np.argsort(eigen_val)
idx = idx[::-1]

eigen_vec = eigen_vec[:,idx]

eigen_val = eigen_val[idx]

PCV[:,:nPC] = eigen_vec[:,:nPC]
#print(PCV[300:500])

FinalData = data.dot(PCV)

#data_file = open('PCA_FinalData','w')
np.savetxt('PCA_FinalData_03.csv',FinalData, delimiter=",")
#data_file.write(FinalData)
#data_file.close"""

#zeroData = (trainlabels==0)