import sheffield.*;
public class Exercise4b {
	public static void main(String args[]) {
		EasyReader keyboard = new EasyReader();
		int x = keyboard.readInt("Enter a number: ");
		int i=1, j=1;
		while ( i<=x ) {		
			int z=i;
			while ( z<x ) {
			System.out.print(" ");
			z++;
			}
			while ( j<i ) {
			j++;
			System.out.print("**");
			}
			j=1;
		System.out.println("*");

			i++;
		}
	}
}