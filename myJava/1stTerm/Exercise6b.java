import sheffield.*;
public class Exercise6b{
	public static void main(String[]args){
		EasyReader file = new EasyReader("ex6Bdata.txt");
		int[] numbers = new int[1000];
		int[] counter = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		for(int x=0;x<1000;x++){
			numbers[x]= file.readInt();
			counter[numbers[x]-1]++;
		}
		EasyGraphics g = new EasyGraphics();
		for(int n=0;n<20;n++){
			g.setColor(0,n*10,200);
			g.fillRectangle(n*10,0,10,counter[n]);
			g.setColor(0,0,0);
			g.drawRectangle(n*10,0,10,counter[n]);
		}
	}
}