package uk.ac.sheffield.com1003.problemsheet2;

/** Item.java
*
* Model of an item of shopping
*
*/

  public class Item {
	public Item(String n, double p) {
		name = n; price = p;
		}
	
	// methods
	public String getName() { return name; }
	public double getPrice() { return price; }
	
	// using ukp to denote pounds sterling as unicode pound does not display
	// properly in MS Command Window
	public String toString() { return (name + " = ukp" + price); }
		
		
	// equals method to be added here
	public boolean equals(Object obj){
		// check if identical objects
		if(this == obj){
			System.out.println("  **reference copy**  "+this.getName()+" and "+((Item)obj).getName());
			return true;
		}
		// must be false if parameter is null
		if(obj == null){
			System.out.println("  **void object**  ");
			return false;
		}
		// must be false if objects have different classes
		if(this.getClass() != obj.getClass()){
			System.out.println("  **different class**  "+this.getName()+" and "+((Item)obj).getName());
			return false;
		}
		// now we can cast and do something specific for Item
		Item i = (Item)obj;
		if(this.getName() == i.getName() && this.getPrice() == i.getPrice()){
			System.out.println("  **different object but the same value**  "+this.getName()+" and "+i.getName());
			return true;
		}
		System.out.println("no why");
		return false;
	}
	// instance fields
	private double price;
	private String name;
	
	public static void main( String[] args){
		final String TESTNAME = "testObject";
		final double TESTPRICE = 10.0;
		Item testObject = new Item(TESTNAME,TESTPRICE);
		System.out.println("Name:");
		System.out.println("Actual field " + testObject.getName());
		System.out.println("Expected " + TESTNAME);
		System.out.println("Price:");
		System.out.println("Actual field " + testObject.getPrice());
		System.out.println("Expected " + TESTPRICE);
	}
}