package uk.ac.sheffield.com1003.problemsheet2;

public class TestBasket {
	public static void main(String[]args){
		Item[] shopping = {new Item("bananas", 0.7),
				new ItemByWeight("bananas", 0.7, 0.0)
		};
		shopping[0] = shopping[1];
		System.out.println(shopping[0]);
		if (shopping[0].equals(shopping[1]))
		System.out.println("you have identical items");
	}

}
