import sheffield.*;

public class BeanMachine {
	
	public static void main(String[]args){
		
		EasyReader keyboard = new EasyReader();
		int numOfBean = keyboard.readInt("Please enter the number of Balls:");
		int numOfBuckets = keyboard.readInt(
			"Please enter the number of ballsInBuckets:");
		//ask the user for the numbers.
		
		int[] ballsInBuckets = new int[numOfBuckets];
		//the array for counting number of balls of each buckets.
		
		char[][] distribution = new char[numOfBuckets][numOfBean];
		//the array for displaying the distribution graph.
		
		System.out.println("Path taken by each ball:");
		
		for (int i=0; i<numOfBean; i++){
			//the loop for dropping each ball.
			
			int path = 0;
			//initiallise the path each ball will go.
			
			for (int chance=0; chance<numOfBuckets-1; chance++){
				//the loop to describe the path of each ball.
				
				double c = 2*Math.random();
				// random a number representing the chance to go left or right.
				
				path = path + (int)c;
				// counting the path the ball has been.
				
				if (c < 1.0)
					System.out.print('L');
				else
					System.out.print('R');
				//display the path.
			}
			
			System.out.println();
			
			ballsInBuckets[path] += 1;
			//counting the balls in the buckets.
		}
		
		int top=0;
		int modelBucket=0;
		int valueOfBuckets=0;
		
		for (int i=0; i<numOfBuckets; i++){
			if(top < ballsInBuckets[i]){
				top = ballsInBuckets[i];
				/*compare to number of balls In each Bucket, and take the most
				one.*/
				modelBucket = i+1;
				//meanwhile take the bucket number.
			}
			valueOfBuckets += ballsInBuckets[i]*(i+1);
			/*the	sum	of	the	number	of	balls	in	each	bucket
			multiplied	by	the	bucket	number.*/
		}
		double meanBuckets = valueOfBuckets/(double)numOfBean;
		//divided	by	the	total	number	of	balls to get meanBuckets number.
		
		System.out.println("Distribution of balls:");
		for (int i=0; i<numOfBuckets; i++){
			if(ballsInBuckets[i]==0)
				distribution[i][0]='_';
			for(int j=0; j<ballsInBuckets[i]; j++)
				distribution[i][j]='*';
		}//assign the balls to the 2d array.
		
		for(int j=0; j<top; j++){
			for(int i=0; i<numOfBuckets; i++)
				System.out.print(distribution[i][top-1-j] + " ");
			System.out.println();
		}//present the distribution diagram using the 2d array.
		
		System.out.println("The modal bucket is number "+ modelBucket);
		System.out.print("The mean bucket is "+ meanBuckets);
		//print the model bucket number and the mean bucket number.
	}
}
