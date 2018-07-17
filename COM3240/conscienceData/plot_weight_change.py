import numpy as np
import matplotlib.pyplot as plt

data = np.genfromtxt('conscience_weight_change.csv',delimiter=',')

r = 200

gaussian_weight = [1/(np.exp((i/r*2)**2)) for i in range(-r,r+1)]

print(gaussian_weight)

fig = plt.figure()
ax = plt.gca()

ax.plot(range(r,40000-(r+1)),[np.average(data[t-r:t+r+1],weights=gaussian_weight) for t in range(r,40000-(r+1))])
#ax.set_yscale('log')
ax.set_xscale('log')
plt.show()