//name: Junjin Chen
//registration No.: 150144892

import sheffield.*;
public class Assignment2 {
	public static void main(String args[]){
		
		//input the file.
		EasyReader fileInput = new EasyReader("picture.txt");
		
		//draw a window.
		EasyGraphics g = new EasyGraphics(1800,150);
		
		//fill the background.
		g.setColor(128,180,245);
		g.fillRectangle(0,0,1799,149);
		
		//read charaters from the file one by one.
		char[] trees = new char[45000];
		for (int n = 0; n<45000; n++)
			trees[n] = fileInput.readChar();
		
		//draw 30 copies of trees.
		for (int copies = 0; copies < 30; copies++){
			
			//add some random elements to the position of trees.
			double position = -200*Math.random();
			double level = 10*Math.random()-5;
			
			//use the data to draw a tree.
			for (int i = 0; i < 45000; i++){
				
				//ignore '0'. (otherwise trees will be covered.)
				if (trees[i] != '0'){
					
					//convert the charater to int and set the color.
					//if use condition, the leaves and trunks will not connect.
					g.setColor((trees[i]-49)*10,180-(trees[i]-49)*170,0);
					
					//use those element to draw dots of the tree.
					//i%300 and i/300 means the x,y-coordinates of the dots.
					//149-i/300 means to draw dots from top left corner.
					g.fillRectangle(copies*60 + i%300 + (int)position,
						149-i/300+(int)level,1,1);
				}
			}
		}
	}
}
		