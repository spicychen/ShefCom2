package java2016.java2016.search4;

/**
	LatticeSearch.java

	search for map traversal
	2016 version
*/

import java.util.*;

public class LatticeSearch extends Search {
	
	private WordLattice latt;
	private LM bg;
	
	public WordLattice getLatt(){
		return latt;
	}
	public LM getLM(){
		return bg;
	}
	
	public LatticeSearch(WordLattice la, LM bg){
		latt = la;
		this.bg = bg;
	}
}