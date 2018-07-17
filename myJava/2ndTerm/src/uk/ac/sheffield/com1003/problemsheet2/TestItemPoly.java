package uk.ac.sheffield.com1003.problemsheet2;


/** Basket.java
*
* Model of a shopping basket containing items of shopping
*
*/
public class TestItemPoly{

	public static void main(String[]args){


		Item[] testingList = {
				
			new Item("SENSATIONS", 1.00),
			new Item("SENSATIONS", 1.00), //testing for identical object
			
			new Item("HARIBO", 4.52),
			new ItemByWeight("HARIBO",  4.52, 0.215), //testing for different classes
			
			new Item("COLA", 0.55),
			new ItemByWeight("Double Chocolate SWISS ROLL", 4.35, 0.230) //testing for reference copy
		};
		
		Item a=null; //testing for the null object;
		
		testingList[4]=testingList[5];	//reference copy
		
		for(int i=0; i<testingList.length-1; i++){
			for(int j=i+1; j<testingList.length; j++){
				System.out.print("Item "+(i+1)+" and "+(j+1)+" :");
				if(testingList[i].equals(testingList[j]))
					System.out.println("y' got Identical Items No."+(i+1)+" and No."+(j+1));
			}
			System.out.print("Item "+(i+1)+" and unspecified item :");
			if(testingList[i].equals(a))
				System.out.println("y' got Identical Items.");
		}
		
		System.out.println();
		System.out.println("Your Items :");
		for(int i=0; i<testingList.length; i++)
			System.out.println("Item No.: "+(i+1)+' '+testingList[i]);
		
		Basket s = new Basket(testingList);
		System.out.println(s.total() + " pounds please.");

	}
	
}