from scipy import spatial as sp
import math

class Retrieve:
	# Create new Retrieve object storing index and termWeighting scheme
	def __init__(self,index,termWeighting):
		self.index = index
		self.termWeighting = termWeighting
		self.num_of_docs = 0
		
		#doc_size is only calculated if termWeighting is tfidf
		if termWeighting == 'tfidf':
			docs_set = set()
			
			#put all documents into a set
			for i in index:
				docs_set.update(set(index[i].keys()))
			
			self.num_of_docs = len(docs_set) #number of documents is simply the length of this set

	# Method to apply query to index
	def forQuery(self,query):
		
		#length of query
		q_len = len(query)
		#initialise the vector of query
		q_vec = [0]*q_len
		#initialise document scores
		doc_scores = {}
		
		#binary method
		if self.termWeighting == 'binary':
			for q in query:
				if q in self.index: #if term found
					docs = self.index[q] #retrieve documents
					for doc in docs: 
						if doc not in doc_scores:
							doc_scores[doc] = -1 #score -1 for matching the term
						else:
							doc_scores[doc] -= 1 #award when having another matching term
							#values are negative such that sorting won't need to reverse
							
		else:
			#tf method
			if self.termWeighting == 'tf':
				
				#query vector: list of query term frequency
				q_vec = list(query.values()) 
				
				#construct document vector and store it in doc_scores
				for i,q in enumerate(query):
					if q in self.index: #if term found
						docs = self.index[q] #retrieve documents
						for doc in docs:
							if doc not in doc_scores:
								#initialise document vector
								doc_vec = [0]*q_len
								doc_scores[doc] = doc_vec
								
							doc_scores[doc][i] = docs[doc] # put term freq into vector
							
			#tf.idf method
			elif self.termWeighting == 'tfidf':
			
				#construct document vector and store it in doc_scores
				for i,q in enumerate(query):
					if q in self.index: #if term found
						docs = self.index[q] #retrieve documents
						
						#document frequency: number of documents containing the term
						doc_freq = len(docs)
						
						#i-th value of query vector =  tf * corresponding idf
						q_vec[i] = query[q] * math.log(self.num_of_docs/ doc_freq, 10)
						
						for doc in docs:
							if doc not in doc_scores:
								#initialise document vector
								doc_vec = [0]*q_len
								doc_scores[doc] = doc_vec
								
							#i-th value of document vector =  tf * corresponding idf
							doc_scores[doc][i] = docs[doc] * math.log(self.num_of_docs/doc_freq, 10)
							
			#calculate the cosine distance between document vector and query vector
			for doc in doc_scores:
				doc_scores[doc] = sp.distance.cosine(doc_scores[doc],q_vec)
				
		#sort the documents according to their score
		ranked_doc = sorted(doc_scores, key=lambda x:doc_scores[x])
			
		return ranked_doc
