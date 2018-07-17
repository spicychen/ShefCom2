package java2016.java2016.search2;

public class Chessboard{
	private char[][] cb;
	private int size;
	private int numOfQs;
	public char[][] getCB(){	return cb;	}
	public int getQs(){		return numOfQs;	}
	public int getSize(){	return size;	}
	
	public Chessboard(int n){
		size = n;
		numOfQs = 0;
		setBoard(n);
	}
	
	public Chessboard(char[][] b, int n, int s){
		cb = b;
		numOfQs = n;
		size = s;
	}
	public void setBoard(int n){
		cb = new char[n][n];
		for(int i=0; i<n; i++)
			for(int j=0; j<n; j++)
				cb[i][j]='D';
	}
	public boolean placeable(int x, int y){
		return (cb[y][x]!='X' && cb[y][x]!='Q');
	}
	public void placeQueen(int x, int y){
		if(x>=0 && x<size && y>=0 && y<size){
			numOfQs++;
			for(int i=x-1; i<=x+1; i++)
				for(int j=y-1; j<=y+1; j++)
					if(i>=0 && i<size && j>=0 && j<size)
						cb[j][i]='X';
			cb[y][x]='Q';
		}
	}
	public String toString(){
		String s = "";
		for(char[] cl: cb){
			for(char c: cl)
				s += c;
			s += "\n";
		}
		return s;
	}
	
	public static void main(String[]args){
		Chessboard cb = new Chessboard(7);
		cb.placeQueen(1,1);
		System.out.println(cb);
	}
}