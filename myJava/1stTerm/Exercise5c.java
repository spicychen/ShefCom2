import sheffield.*;
public class Exercise5c {
	public static void main(String[]args) {
		int[][] yourArray = new int[2][5];
		EasyReader keyboard = new EasyReader();
		for (int j=0; j<yourArray.length; j++){
			for (int i=0; i<yourArray[j].length; i++)
				yourArray[j][i] = keyboard.readInt(
					"Enter array value " + i + " of list " + (j+1) + " : ");
			
		}
		boolean matched = false;
		for (int k=0; k<yourArray[0].length; k++)
			for(int l=0; l<yourArray[1].length; l++) {
				if (yourArray[0][k]==yourArray[0][l]) {
					matched = true;
				}
			}
		if (matched)
			System.out.print("The two lists have same content(s)");
	}
}