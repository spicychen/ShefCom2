from scipy import spatial as sp
import math

class Retrieve:
	# Create new Retrieve object storing index and termWeighting scheme
	def __init__(self,index,termWeighting):
		self.index = index
		self.termWeighting = termWeighting
		self.docs_size = 0
	#	self.qid = 1
		
		if termWeighting == 'tfidf':
			docs_set = set()
			for i in index:
				docs_set.update(set(index[i].keys()))
			self.docs_size = len(docs_set)

	# Method to apply query to index
	def forQuery(self,query):
		
		#length of query
		q_len = len(query)
		#vector of query
		q_vec = [0]*q_len
		#initialise document found
		doc_found = {}
		
		if self.termWeighting == 'binary':
			for q in query:
				if q in self.index: #if query found
					docs = self.index[q]
					for doc in docs: #retrieve documents
						if doc not in doc_found:
							doc_found[doc] = -1
						else:
							doc_found[doc] -= 1
							
		else:
			
			if self.termWeighting == 'tf':
				q_vec = list(query.values())
				
				#construct document vector and store it in doc_found
				for i,q in enumerate(query):
					if q in self.index: #if query found
						docs = self.index[q]
						for doc in docs: #retrieve documents
							if doc not in doc_found:
								doc_vec = [0]*q_len
								doc_found[doc] = doc_vec
								
							doc_found[doc][i] = docs[doc] # put term freq into vector
							
			elif self.termWeighting == 'tfidf':
			
				#construct document vector and store it in doc_found
				for i,q in enumerate(query):
					if q in self.index: #if query found
						docs = self.index[q]
						doc_freq = len(docs)
						q_vec[i] = query[q] * math.log(self.docs_size/ doc_freq, 10)
						for doc in docs: #retrieve documents
							if doc not in doc_found:
								doc_vec = [0]*q_len
								doc_found[doc] = doc_vec
								
							doc_found[doc][i] = docs[doc] * math.log(self.docs_size/doc_freq, 10)
							
			for doc in doc_found:
				doc_found[doc] = sp.distance.cosine(doc_found[doc],q_vec)
				
		if len(doc_found) > 10:
			ranked_doc = sorted(doc_found, key=lambda x:doc_found[x])[:10]
		else:
			ranked_doc = sorted(doc_found, key=lambda x:doc_found[x])
		#sorted_docs = sorted(doc_found.items(), key=lambda x:x[1])
		#ranked_doc  = [k for k,v in sorted_docs if v < 0.5]
			
		#print('QID',self.qid,' : ',[(i,doc_found[i]) for i in ranked_doc],'\n')
		#self.qid +=1
		
		return ranked_doc
