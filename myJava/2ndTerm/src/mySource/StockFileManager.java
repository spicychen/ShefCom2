package mySource;

import java.util.*;
import java.io.*;
public class StockFileManager {
	private String fileName;
	public StockFileManager(String fileName){
		this.fileName = fileName;
		
	}
	
	public void writeData(ArrayList<StockItem> stockItems) throws IOException {
		FileOutputStream fos = new FileOutputStream(fileName);
		DataOutputStream dos = new DataOutputStream(fos);
		
		for(StockItem stockItem: stockItems){
			dos.writeChars(stockItem.getItemCode());
			dos.writeInt(stockItem.getNumberInStock());
			dos.writeDouble(stockItem.getPrice());
		}
		
		dos.close();
	}
	
	public ArrayList<StockItem> readData() throws IOException {
		ArrayList<StockItem> stockItems =new ArrayList<StockItem>();
		
		FileInputStream fis = new FileInputStream(fileName);
		DataInputStream dis = new DataInputStream(fis);
		
		
		dis.close();
		
		return stockItems;
	}
	
	public static void main(String[]args) throws IOException{
		ArrayList<StockItem> items = new ArrayList<StockItem>();
		int nitems = (int)(50*Math.random());
		for(int i=0; i<nitems; i++){
			double price = 100*Math.random();
			int nis = (int)(25*Math.random());
			String stnm = "";
			for(int j=0; j<7; j++){
				stnm += (char)((int)(26*Math.random())+65);
			}
			items.add(new StockItem(stnm, nis, price));
		}
		
		
		String filename = "stockinfo.txt";
		StockFileManager sfm = new StockFileManager(filename);
		sfm.writeData(items);
	}

}
