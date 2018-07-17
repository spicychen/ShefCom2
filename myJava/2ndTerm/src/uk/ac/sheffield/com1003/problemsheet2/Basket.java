package uk.ac.sheffield.com1003.problemsheet2;

/** Basket.java
*
* Model of a shopping basket containing items of shopping
*
*/

public class Basket {
	public Basket(Item[] it) {
		items = it;
		}
	public double total() {
		double tot = 0.0;
		for (int i = 0; i < items.length; i++)
			tot += items[i].getPrice();
		return tot;
		}
		
	private Item[] items;
}
