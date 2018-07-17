import sheffield.*;
public class Exercise2c {
	public static void main(String[] args) {
		EasyReader fileInput =
					new EasyReader("pounds.txt");
					double p1 = fileInput.readDouble();
					double p2 = fileInput.readDouble();
		EasyWriter file = new EasyWriter("Euros.txt");
					double e1 = p1*1.25 ;
					double e2 = p2*1.25 ;
					double E = e1 + e2 ;
					file.println(e1) ;
					file.println(e2) ;
					file.println(E) ;
	}
}