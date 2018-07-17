import sheffield.*;

public class Exercise2a {
	public static void main(String[] args) {
		/*input the miles and yards*/
		EasyReader keyboard = new EasyReader();
		int m = keyboard.readInt("Enter the miles: ");
		int y = keyboard.readInt("Enter the yards: ");
		
		/*converts*/
		double tm = m + y/1760.0 ;
		double km = tm*1.60934 ;
		
		/*output the result*/
		System.out.println("the total distance in kilometre is: ");
		EasyWriter screen = new EasyWriter();
		screen.println(km,2);
	}
}
		