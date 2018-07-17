import imageio
images = []

pic_name = 'results/step_'

for t in range(1,40000):
	if(t%400==0):
		full_name = pic_name+str(t)+'.png'
		images.append(imageio.imread(full_name))
		
imageio.mimsave('results/animation.gif', images)