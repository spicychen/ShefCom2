"""welcome to <PROGNAME>"""

import sys, re, getopt
import numpy as np
import pylab as p

class CommandLine:
	def __init__(self):
		opts, args = getopt.getopt(sys.argv[1:], 'hs:d:')
		opts = dict(opts)
		self.argfile = args
		self.stops = set()
		
		if '-h' in opts:
			self.printHelp()
			
		if '-s' in opts:
			self.readStopList(opts['-s'])
			
		if '-d' in opts:
			self.argfile = opts['-d']
			
	def printHelp(self):
		help = __doc__.replace('<PROGNAME>', sys.argv[0],1)
		print(help, file=sys.stderr)
		sys,exit()
	
	def readStopList(self,file):
		f = open(file, 'r')
		for line in f:
			self.stops.add(line.strip())
			
class Document:
	def __init__(self,file,stops):
		self.name = file
		self.counts = {}
		self.totalWords = 0
		self.frequency = {}
		word = re.compile(r'[A-Za-z]+')
		f = open(file, 'r')
		for line in f:
			for wd in word.findall(line.lower()):
				self.totalWords += 1
				if wd not in stops:
					if wd in self.counts:
						self.counts[wd] += 1
					else:
						self.counts[wd] = 1
		self.calFreq()
						
	def calFreq(self):
		for wd in self.counts:
			self.frequency[wd] = (self.counts[wd] / self.totalWords)
		
class printResult:
	def __init__(self, document):
		self.document = document
		for freq_wd, freq in self.sortFreq():
			print("%s : %.4f" % (freq_wd, freq))
		
	def sortFreq(self):
		sorted_Freq = sorted(self.document.frequency.items(), key = lambda x:x[1], reverse = True)[:10]
		return sorted_Freq
		
class plotResult:
	def __init__(self,document):
		self.document = document
		Y1 = self.rankFreq()
		Y2 = np.cumsum(Y1)
		X = range(1,len(Y1)+1)
		log_x = np.log10(X)
		log_y1 = np.log10(Y1)
		log_y2 = np.log10(Y2)
		p.subplot(211)
		p.plot(log_x,log_y1)
		p.subplot(212)
		p.plot(log_x,log_y2)
		p.show()
		
		
	def rankFreq(self):
		freq = sorted(self.document.frequency.items(), key = lambda x:x[1], reverse = True)
		return [f[1] for f in freq]
		
#######################################################################

if __name__ == '__main__':
	config = CommandLine()
	document = Document(config.argfile, config.stops)
	plotResult(document)