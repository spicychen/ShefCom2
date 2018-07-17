package uk.ac.sheffield.com1003.objectville.abstractclasses;
import uk.ac.sheffield.com1003.EasyGraphics;
public class MyDrawShapes {
	public static void main(String[]args){
		EasyGraphics g = new EasyGraphics();
		Shape[] MyShapes = {new Rectangle(50,60,60,50),
				new EquilateralTriangle(100,100,20),
				new EquilateralTriangle(60,100,20),
				new Rectangle(55,65,50,10)
		};
		for(Shape i: MyShapes)
		i.draw(g);
	}

}
