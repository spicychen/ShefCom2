package java2016.java2016.search2.Search2;

public class NQueensSearch extends Search{
	private int tar;//target number of queens
	private Chessboard result;
	
	public NQueensSearch(int s){
		tar=s;
	}
	
	public int getTar(){
		return tar;
	}

	public Chessboard getResult() {
		return result;
	}

	public void setResult(Chessboard result) {
		this.result = result;
	}
}