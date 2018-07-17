import javax.swing.JOptionPane;
public class Exercise2d {
	public static void main(String[] agrs) {
		
		String x = JOptionPane.showInputDialog("Enter First name: ");
		String y = JOptionPane.showInputDialog("Enter Family name: ");
		
		JOptionPane.showMessageDialog(null, "your haircut looks good", "warnning", JOptionPane.ERROR_MESSAGE);
		System.out.println( x + " " +y );
	}
}