package java2016.java2016.search2;

import java.util.ArrayList;

public class NQueenState extends SearchState{
	
	private ArrayList<Queens> qp;
	
	public NQueenState(ArrayList<Queens> queensposition){
		qp = queensposition;
	}

	@Override
	boolean goalP(Search searcher) {
		NQueenSearch nqs = (NQueenSearch) searcher;
		int tar = nqs.getnumOfQ();
		int cur = qp.size();
		return (cur==tar);
	}

	@Override
	ArrayList<SearchState> getSuccessors(Search searcher) {

	    ArrayList<NQueenState> nqslis=new ArrayList<NQueenState>(); // the list of jugs states
	    ArrayList<SearchState> slis=new ArrayList<SearchState>();
	    ArrayList<Queens> newqp = qp;
	    
	    NQueenSearch nqs = (NQueenSearch) searcher;
	    int tar = nqs.getnumOfQ();
	    for(int i=0; i<tar; i++)
	    	for(int j=0; j<tar; j++){
	    		boolean placeable = true;
	    		for(Queens qs: qp){
	    			int x = qs.getX();
	    			int y = qs.getY();
	    			if((i>=x-1 && i<=x+1) && (j>=y-1 && j<=y+1))
	    				placeable = false;
	    		}
	    		if(placeable){
	    			Queens q = new Queens(i,j);
	    			newqp.add(q);
	    			nqslis.add(new NQueenState(newqp));
	    		}
	    	}
	    
	    for(NQueenState n: nqslis)
	    	slis.add((SearchState) n);
		return slis;
	}

	@Override
	boolean sameState(SearchState n2) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public String toString(){
		String s = "";
		for(Queens qs: qp){
			s += " ("+qs.getX()+","+qs.getY()+") ";
		}
			
		return s;
	}


}
