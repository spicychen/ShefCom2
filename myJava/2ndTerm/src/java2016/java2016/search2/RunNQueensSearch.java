package java2016.java2016.search2;

import javax.swing.JFrame;

import sheffield.*;
public class RunNQueensSearch extends JFrame{
	
	public static void main(String[]arg){
		EasyWriter screen = new EasyWriter();
		EasyReader rd = new EasyReader();
		int size = rd.readInt("Please enter the N value(4-8): ");
		
		if(size<4 || size>8)
			System.out.println("what a funny number: "+size);
		else{
			NQueensSearch nqs = new NQueensSearch(size);
			Chessboard cb = new Chessboard(size);
			switch(size){//prepare some Queens first, so that search can run fast.
				case 6: cb.placeQueen(4,4);	break;
				case 7: cb.placeQueen(5,5);	cb.placeQueen(1,5);	cb.placeQueen(5,1);	break;//without one of them, it will be years before breadthFirst search is finished.
				case 8: cb.placeQueen(6,6);	cb.placeQueen(1,6);	cb.placeQueen(6,1);	break;
			}
			SearchState ini = new NQueensState(cb,1,1);
			String resd = nqs.runSearch(ini, "depthFirst");//can also try "breadthFirst".
			screen.println(resd);
			
			Chessboard rscb = nqs.getResult();
			char [][] rsb = rscb.getCB();
			EasyGraphics graphic = new EasyGraphics(60*size,60*size);//draw it out
			for(int i=0;i<size;i++){
				for(int j=0;j<size;j++){
					if(rsb[i][j]=='X' || rsb[i][j]=='Q'){
						if((i+j)%2==0)
							graphic.setColor(80, 0, 0);
						else
							graphic.setColor(255, 175, 175);
						}
						else if((i+j)%2==0)
							graphic.setColor(0, 0, 0);
						else
							graphic.setColor(255, 255, 255);
						graphic.fillRectangle(60*j, 60*(size-1-i), 60, 60);
					if(rsb[i][j]=='Q'){
						graphic.setColor(255, 255, 0);
						graphic.fillEllipse(60*j+10, 60*(size-1-i)+10, 40, 40);
					}
					
				}
			}
			
		}
		
		
	}
}