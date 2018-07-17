import sheffield.*;
public class Exercise6a{
	public static void main(String[]args){
		EasyGraphics g = new EasyGraphics();
		g.setColor(230,230,230);
		g.fillRectangle(89,149,30,30);//head
		g.fillRectangle(99,79,10,70);//neck and below
		g.fillRectangle(79,39,20,100);//left chest
		g.fillRectangle(109,39,20,100);// right chest
		g.fillRectangle(69,99,10,40);//left arm
		g.fillRectangle(129,99,10,40);//right arm
		g.setColor(100,100,100);
		g.fillRectangle(69,89,10,10);
		g.fillRectangle(129,89,10,10);
		g.fillRectangle(79,29,20,10);
		g.fillRectangle(109,29,20,10);
		g.setColor(0,0,100);
		for (int x=9; x<=189; x+=10){
			g.moveTo(x,9);
			g.lineTo(x,189);
			g.moveTo(9,x);
			g.lineTo(189,x);
		}
	}
}