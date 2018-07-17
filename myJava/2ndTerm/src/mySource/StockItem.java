package mySource;


public class StockItem {
	
	private String itemCode;
	private int numberInStock;
	private double price;
	public StockItem(String ic, int nis, double p){
		itemCode = ic;
		numberInStock = nis;
		price = p;
	}

	public String getItemCode() {
		// TODO Auto-generated method stub
		return itemCode;
	}

	public int getNumberInStock() {
		// TODO Auto-generated method stub
		return numberInStock;
	}

	public double getPrice() {
		// TODO Auto-generated method stub
		return price;
	}

}
