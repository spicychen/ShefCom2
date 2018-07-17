import sheffield.*;
public class Exercise4e {
	public static void main(String args[]) {
		EasyReader fileInput = new EasyReader("ex4Edata.txt");

		String c = fileInput.readString();
		c = c.replace('a', 'u'); 
		System.out.print(c);
		EasyWriter file = new EasyWriter("ex4Edataout.txt");
		file.println(c);
	}
}