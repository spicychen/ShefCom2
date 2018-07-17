package uk.ac.sheffield.com1003.problemsheet2;

/** ItemByWeight.java
*
* Subclass of Item, where price is specified by unit weight
*
*/

public class ItemByWeight extends Item {
	public ItemByWeight(String n, double p, double w) {
		super(n, p);
		weight = w;
		}
		
	public double getPrice() {
		return weight*super.getPrice();
		}
		
	public double getWeight() {
		return weight;
	}
	public String toString() {
		return (getName() + " (weight : " + weight + "kg and unitPrice : "
		+ super.getPrice() + "ukp/kg) = ukp"
		+ getPrice());
		}

	// equals method to be added here
	public boolean equals(Object obj){
		if(!super.equals(obj)
				|| !(this.getWeight() == ((ItemByWeight)obj).getWeight()))
			return false;
		return true;
	}
	
	// instance field
	private double weight;
}

