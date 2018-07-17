"""\
------------------------------------------------------------
USE: python <PROGNAME> (options) file1...fileN
OPTIONS:
    -h : print this help message
    -b : use BINARY weights (default: count weighting)
    -s FILE : use stoplist file FILE
    -I PATT : identify input files using pattern PATT, 
              (otherwise uses files listed on command line)
------------------------------------------------------------
"""

import sys, re, getopt, glob

opts, args = getopt.getopt(sys.argv[1:],'hs:bI:')
opts = dict(opts)
filenames = args

##############################
# HELP option

if '-h' in opts:
    help = __doc__.replace('<PROGNAME>',sys.argv[0],1)
    print(help,file=sys.stderr)
    sys.exit()

##############################
# Identify input files, when "-I" option used

if '-I' in opts:
	filenames = glob.glob(opts['-I'])
print('INPUT-FILES:', ' '.join(filenames))
##############################
# STOPLIST option

stops = set()
if '-s' in opts:
    with open(opts['-s'],'r') as stop_fs:
        for line in stop_fs :
            stops.add(line.strip())

##############################

def read_two_files(fn1,fn2):
	list1 = []
	list2 = []
	
	with open(fn1, 'r') as file1:
		list1 = re.findall(r'[A-Za-z]+',file1.read())
	list1 = list(map(lambda x:x.lower(), list1))
	
	with open(fn2, 'r') as file2:
		list2 = re.findall(r'[A-Za-z]+',file2.read())
	list2 = list(map(lambda x:x.lower(), list2))
	
	return list1, list2

def count_words(list_of_words):
	counts = dict();
	for w in list_of_words:
		if w in counts:
			counts[w] += 1
		elif w in stops:
			continue
		else:
			counts[w] = 1
			
	return counts
	
	
def sensitive_word_overlap(count1, count2):
	min_union = 0
	max_union = 0
	union_keys = set.union(set(count1.keys()),set(count2.keys()))
	for k in union_keys:
		if k in count1 and k in count2:
			min_union += min(count1[k],count2[k])
			max_union += max(count1[k],count2[k])
		elif k in count1:
			max_union += count1[k]
		elif k in count2:
			max_union += count2[k]
			
	return min_union/max_union
	
def compare_across_files(file_list):
	num_files = len(file_list)
	for i in range(num_files-1):
		for j in range((i+1),num_files):
			list1, list2 = read_two_files(file_list[i],file_list[j])
			count1 = count_words(list1)
			count2 = count_words(list2)
			print('%s & %s : %.3f' % (file_list[i],file_list[j],sensitive_word_overlap(count1,count2)))

if filenames:
	compare_across_files(filenames)
"""
list1 = []
list2 = []

with open(sys.argv[3], 'r') as file1:
	list1 = re.findall(r'[A-Za-z]+',file1.read())
list1 = list(map(lambda x:x.lower(), list1))

with open(sys.argv[4], 'r') as file2:
	list2 = re.findall(r'[A-Za-z]+',file2.read())
list2 = list(map(lambda x:x.lower(), list2))

def count_words(list_of_words):
	counts = dict();
	for w in list_of_words:
		if w in counts:
			counts[w] += 1
		elif w in stops:
			continue
		else:
			counts[w] = 1
			
	return counts
	

def simple_word_overlap(count1, count2):
	set1 = set(count1.keys())
	set2 = set(count2.keys())
	
	intersection_count = len(set.intersection(set1,set2))
	union_count = len(set.union(set1,set2))
	
	return intersection_count/union_count
	
count1 = count_words(list1)
count2 = count_words(list2)

def sensitive_word_overlap(count1, count2):
	min_union = 0
	max_union = 0
	union_keys = set.union(set(count1.keys()),set(count2.keys()))
	for k in union_keys:
		if k in count1 and k in count2:
			min_union += min(count1[k],count2[k])
			max_union += max(count1[k],count2[k])
		elif k in count1:
			max_union += count1[k]
		elif k in count2:
			max_union += count2[k]
			
	return min_union/max_union
	
print(sensitive_word_overlap(count1,count2))"""