import sheffield.*;
public class Exercise5b {
	public static void main(String args[]){
		int[] myArray = new int[10];
		EasyReader keyboard = new EasyReader();
		for (int i=0; i<10; i++)
			myArray[i] = keyboard.readInt("Enter the number" + i + ": ");
		int total=0;
		System.out.println("Your numbers were:");
		for (int j=0; j<10; j++){
			System.out.println(myArray[j]);
			total += myArray[j];
		}
		double average = total/myArray.length;
		System.out.println("The average of your number is: " + average);
	}
}