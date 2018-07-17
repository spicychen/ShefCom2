package java2016.java2016.search2;

import java.util.ArrayList;

import sheffield.*;

public class runNQueenSearch {
	public static void main(String[]args){
		EasyWriter screen = new EasyWriter();
		int noqs = 6;
		NQueenSearch nqs = new NQueenSearch(noqs,10);
		Queens q = new Queens(1,1);
		ArrayList<Queens> qs = new ArrayList<Queens>();
		qs.add(q);
		SearchState inis = (SearchState) new NQueenState(qs);
		
		String resd = nqs.runSearch(inis, "depthFirst");
		screen.print(resd);
	}
}
