import sheffield.*;

public class Exercise3a {
	public static void main(String[] args){
		
		EasyReader keyboard = new EasyReader();
		
		int age = keyboard.readInt ( "How old are you? " );
		
		if ( age < 18 )
			System.out.println( "You cannot buy alcohol." );
		else if ( age < 25 )
			System.out.println( "Please show me your passport or something" );
		else
			System.out.println( "enjoy your alcohol" );
		
	}
}