import sheffield.*;
public class Exercise4d {
	public static void main(String args[]) {
		EasyReader keyboard = new EasyReader();
		int n = keyboard.readInt(
			"please enter a number: ");
		int m = n%2;
		while ( n != 1 ) {
			if ( m == 1 )
				n = n*3+1;
			else
				n = n/2;
			System.out.println(n);
			m = n%2;
		}
	}
}