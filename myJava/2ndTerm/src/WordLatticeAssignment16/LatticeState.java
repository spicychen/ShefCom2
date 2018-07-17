package WordLatticeAssignment16;

/**
	State in a lattice search
	2016 version
*/

import java.util.*;

public class LatticeState extends SearchState{
	
	// word hypotheses for this state
	private WordH w;
	
	//constructor
	public LatticeState(WordH wh, int lc){
		w = wh;
		localCost = lc;
		estRemCost=wh.getERCost();
	}
	
	//accessor
	public WordH getAWord(){
		return w;
	}
	
	//goalP
	public boolean goalP(Search searcher) {
		LatticeSearch ls = (LatticeSearch) searcher;
		WordLattice wl = ls.getLatt(); // get word lattice
		return (w.getEnd()== wl.getEndTime());//return true if there is no word to be followed.
	}
	
	//getSuccesors
	public ArrayList<SearchState> getSuccessors(Search searcher){
		LatticeSearch ls = (LatticeSearch) searcher;
		LM lm = ls.getLM(); //get the Language Model
		WordLattice wl = ls.getLatt();	//get the Lattice
		ArrayList<WordH> whs = wl.wordsAtT(w.getEnd()); // find words that are able to follow this word
		ArrayList<SearchState> succs=new ArrayList<SearchState>();
		for(WordH h: whs){
					int lc = h.getCost()+lm.getCost(w.getWord(), h.getWord());	//local cost = word cost + transition cost
					succs.add((SearchState) new LatticeState(h, lc));}
		return succs;
		
	}

	//sameState
	boolean sameState(SearchState n2) {
		return false;
	}

	//toString
    public String toString () {
      return ("Lattice State: "+w);
    }
}