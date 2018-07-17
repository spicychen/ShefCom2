public class transformationSequence{
	
	private char transformation;
	private float in_x;
	private float in_y;
	private float in_z;
	private float angle;
	
	public transformationSequence(char t, float x, float y, float, z){
		transformation = t;
		in_x = x;
		in_y = y;
		in_z = z;
	}
	
	public transformationSequence(char t, float a){
		transformation = t;
		angle = a;
	}
	
}