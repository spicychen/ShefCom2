package java2016.java2016.search2;

import java.util.ArrayList;

public class NQueenSearch extends Search{
	
	private int numOfQ;
	private int value;
	
	public NQueenSearch(int target, int value){
		numOfQ = target;
		this.value = value;
	}

	public int getnumOfQ() {
		return numOfQ;
	}
	
	public int getValue(){
		return value;
	}


}
