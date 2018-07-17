import sheffield.*;
public class Exercise5e {
	public static void main(String[]args){
		EasyReader keyboard = new EasyReader();
		int[][] matrix = new int[2][3];
		int i=0, j=0;
		for (i=0; i<matrix.length; i++)
			for (j=0; j<matrix[i].length; j++)
			matrix[i][j] = keyboard.readInt("Enter a number: ");
		int[][] mat = new int[2][3];
		for (i=0; i<mat.length; i++)
			for (j=0; j<mat[i].length; j++)
			mat[i][j] = keyboard.readInt("Enter a number: ");
		int[][] newMatrix = new int[2][3];
		for ( int x=0; x<matrix.length; x++)
			for ( int y=0; y<matrix[x].length; y++){
				newMatrix[x][y] = matrix[x][y]+mat[x][y]; 
				System.out.println(newMatrix[x][y]);
		}
	}
}