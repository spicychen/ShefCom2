package java2016.java2016.search4;

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
	}
	
	//accessor
	public WordH getAWord(){
		return w;
	}
	
	//goalP
	public boolean goalP(Search searcher) {
		LatticeSearch ls = (LatticeSearch) searcher;
		WordLattice wl = ls.getLatt(); // get word lattice
		for(WordH h: wl.getAllWordHs())
			if(h.getStart()==w.getEnd())//check if there is a word which can follow this word.
				return false;
		return true;//return true if there is no word to be followed.
	}
	
	//getSuccesors
	public ArrayList<SearchState> getSuccessors(Search searcher){
		LatticeSearch ls = (LatticeSearch) searcher;
		LM lm = ls.getLM();
		WordLattice wl = ls.getLatt();
		ArrayList<SearchState> succs=new ArrayList<SearchState>();
		for(WordH h: wl.getAllWordHs())
			if(h.getStart()==w.getEnd())
				if(w.getWord().equals("*start*"))
				succs.add((SearchState) new LatticeState(h, (h.getCost())));
				else
					succs.add((SearchState) new LatticeState(h, (h.getCost()+lm.getCost(w.getWord(), h.getWord()))));
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