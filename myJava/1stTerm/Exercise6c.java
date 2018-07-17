import sheffield.*;
public class Exercise6c{
	public static void main(String[]args){
		EasyGraphics g = new EasyGraphics();
		for(int x=0;x<300;x++){
			double v = 255*Math.random();
			double t = 255*Math.random();
			double i = 255*Math.random();
			double o = 200*Math.random();
			double y = 200*Math.random();
			double z = 60*Math.random();
			g.setColor((int)v,(int)t,(int)i);
			g.fillEllipse((int)o-10,(int)y-10,(int)z,(int)z);
		}
	}
}