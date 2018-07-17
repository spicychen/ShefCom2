package java2016.java2016.search2;

import java.util.*;
public class NQueensState extends SearchState{
	private Chessboard cb;//chessboard
	private int qX;
	private int qY;//coordinates of the current Queen.
	public Chessboard getBoard(){return cb;}
	public int getQX(){return qX;}
	public int getQY(){return qY;}
	
	public NQueensState(Chessboard c, int x, int y){
		cb=c;
		qX = x;	qY = y;
		cb.placeQueen(x,y);
	}
	
	public boolean goalP(Search searcher){
		NQueensSearch nqs = (NQueensSearch) searcher;
		int tar = nqs.getTar();
		int cur = cb.getQs();
		if(cur == tar)
			nqs.setResult(cb);
		return (cur == tar);//true when number of queens meets the target
	}
	
	public ArrayList<SearchState> getSuccessors(Search searcher){
		ArrayList<SearchState> slis = new ArrayList<SearchState>();
		ArrayList<NQueensState> nqlis = new ArrayList<NQueensState>();
		
		int size = cb.getSize();
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				if(cb.placeable(i,j)){//check the place that no queens surround.
					int si = cb.getSize();
					char[][] oldb = cb.getCB();//So array is an object!
					char[][] newb = new char[si][si];
					for(int x=0; x<size; x++)
						for(int y=0; y<size; y++){
							newb[y][x]=oldb[y][x];
						}
					Chessboard newcb = new Chessboard(newb,cb.getQs(),si);
					nqlis.add(new NQueensState(newcb,i,j));
				}
			}
		}
		
		for(NQueensState nqs: nqlis)	slis.add((SearchState) nqs);
		
		return slis;
	}
	
	public boolean sameState(SearchState s2){
		NQueensState nqs2 = (NQueensState) s2;
		Chessboard cb2 = nqs2.getBoard();
		boolean equalQs=(cb.getQs()==cb2.getQs());
		boolean backslashmirror=false;
		boolean slashmirror=false;
		boolean horizontal=false;
		boolean vertical = false;
		if(equalQs){//save time
			char[][] b = cb.getCB();
			int s = cb.getSize()-1;
			char[][] b2 = cb2.getCB();
			
			outerloop:
			for(int i=0; i<s; i++){		for(int j=0; j<=i; j++){
					if(b[i][j]==b2[j][i])
						backslashmirror=true;
					else{
						backslashmirror=false;
						break outerloop;	}	}	}
	
			outerloop:
			for(int i=0; i<s; i++){		for(int j=0; j<=s-i; j++){
					if(b[i][j]==b2[s-j][s-i])
						slashmirror=true;
					else{
						slashmirror=false;
						break outerloop;	}	}	}
			
			outerloop:
			for(int i=0; i<s/2; i++){		for(int j=0; j<s; j++){
					if(b[i][j]==b2[s-i][j])
						horizontal=true;
					else{
						horizontal=false;
						break outerloop;	}	}	}
			
			outerloop:
			for(int i=0; i<s; i++){		for(int j=0; j<s/2; j++){
					if(b[i][j]==b2[i][s-j])
						vertical=true;
					else{
						vertical=false;
						break outerloop;	}	}	}
		}

		return (equalQs && (backslashmirror || slashmirror || horizontal || vertical));//check whether the queens' relative position of both chessboards are the same
	}
	
	public String toString(){
		return "("+(qX+1)+","+(qY+1)+")=>(numOfQueens,chessboardSize)("+cb.getQs()+","+cb.getSize()+")"+"\n"+cb.toString();
	}
	
}