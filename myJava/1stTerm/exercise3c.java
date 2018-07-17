import sheffield.*;

public class exercise3c {
	public static void main(String[] args) {
		
		EasyReader keyboard =new EasyReader();
		
		int T = keyboard.readInt( "What is the temperature? " );
		
		if ( T < 40 )
			System.out.println( "Too cold" );
		else if ( T < 70 )
			System.out.println( "Gleaming" );
		else if ( T < 80 )
			System.out.println( "Glowing" );
		else if ( T < 90 )
			System.out.println( "Perspiring" );
		else if ( T < 100 )
			System.out.println( "Sweating" );
		else if ( T < 120 )
			System.out.println( "Dripping" );
		else
			System.out.println( "Too hot" );
	}
}
		