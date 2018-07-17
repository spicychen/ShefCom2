import javax.swing.JOptionPane;
public class Exercise2f {
	public static void main(String[] args) {
		
		String Tel = JOptionPane.showInputDialog("Enter your UK telephone number: ");
			
		int a = Tel.indexOf(" ");		
			
		String AC = Tel.substring(0,a);
		
		String T = Tel.substring(a++);
		
		int b = T.indexOf(" ");
		
		System.out.println( "the area code is: " + AC ) ;
		System.out.println( "the Telephone number is: " + T );
	}
}