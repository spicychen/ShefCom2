import sheffield.*;

public class exercise3b {
	public static void main(String[] args) {
		
		EasyReader keyboard = new EasyReader();
	
		char x = keyboard.readChar( "Please enter a letter in A~F. " );
		
		if ( x == 'C' || x == 'c' )
			System.out.println( "doh" );
		else if ( x == 'D' || x == 'd' )
			System.out.println( "ray" );
		else if ( x == 'E' || x == 'e' )
			System.out.println( "Me" );
		else if ( x == 'F' || x == 'f' )
			System.out.println( "Fah" );
		else if ( x == 'G' || x == 'g' )
			System.out.println( "Soh" );
		else if ( x == 'A' || x == 'a' )
			System.out.println( "La" );
		else if ( x == 'B' || x == 'b' )
			System.out.println( "Ti" );
		else
			System.out.println( " BiBiiii! *Warning sound* " );
			
	}
}