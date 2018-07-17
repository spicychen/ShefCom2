import sheffield.*;
public class Exercise5d {
	public static void main(String[]args) {
		EasyReader fileInput = new EasyReader("ex5Data.txt");
		String[] line = new String[5];
		for (int x=0; x<line.length; x++)
			line[x] = fileInput.readString();
		
		for (String l : line) {
				String[] splits = l.split(" ");
				for( String s : splits) System.out.println(s);
		}
			
	}
}