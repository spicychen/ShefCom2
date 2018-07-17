import java.lang.Math.*;

public class RandomFloatGenerator{
	public static void main(String[] args) {
		//360.0f*Math.random(), 360.0f*Math.random(), 360.0f*Math.random()
		for(int i=0; i<15; i++){
			float x = 60.0f/15.0f * i, y= 0.0f, z= 0.0f;
			System.out.format("{%.1ff, %.1ff, %.1ff}, \n", x,y,z);
		}
	}
}