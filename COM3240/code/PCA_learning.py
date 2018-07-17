import numpy as np
import numpy.matlib
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
import gzip

file_name = 'train-images-idx3-ubyte'
train_data = gzip.GzipFile(file_name+'.gz', 'rb')
train_content = train_data.read()

#extract the data
pixels = 28
train_block = list(train_content[16:])
train = np.reshape(train_block, (-1, pixels*pixels))

#sample from the data
train_sample = train
[m,n]=np.shape(train_sample)

#normalise data
normT = np.sqrt(np.diag(train_sample.dot(train_sample.T)))
train_sample = train_sample.T/np.matlib.repmat(normT.T,n,1)
data = train_sample.T

nPC = 6

PCV = np.zeros((n,nPC))

#centerlise data
meanData = np.matlib.repmat(data.mean(axis=0),m,1)
data = data - meanData

#covariance matrix
C = np.cov(data.T)

#eigen-value and eigen-vector
eigen_val,eigen_vec = np.linalg.eigh(C)

#sort eigen value
idx = np.argsort(eigen_val)
idx = idx[::-1]

eigen_vec = eigen_vec[:,idx]

eigen_val = eigen_val[idx]

#pick most effiecient eigen vector as principle vector
PCV[:,:nPC] = eigen_vec[:,:nPC]

#matrix multiplication to get final data
FinalData = data.dot(PCV)

#store the data into a file
np.savetxt('PCA_FinalData.csv',FinalData, delimiter=",")