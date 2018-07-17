To run PCA:
	python PCA_learning.py
--this will generate result data file

To cluster the result and plot PCA:
	python Kmean_cluster.py
	
To run competitive learning:
	python Competitve_learn.py
--this will plot the final result of the training and store the weight changes
	
To run conscience competitive learning:
	python Cons_Competitive_learn.py
--this will generate 3 data files instead of ploting

to plot the weight vectors of conscience competitive learning;
	python imshowWeight.py
	
to plot the weight changes of conscience competitive learning:
	python plot_weight_change.py