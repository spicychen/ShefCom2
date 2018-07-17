import sheffield.EasyReader;
import java.util.ArrayList;
import java.util.Iterator;
public class SimpleDataViewer {
	
	public SimpleDataViewer( String[] values){
		this.values = values;
    }
	private static String[] title;
	private String[] values;

	
	 public String[] getData(){
			return values;
		    }
	/*	    
		    public String getColour(){
			return colour;
		    }
		    
		    public int getPrice(){
			return price;
		    }
		    
		    public int getMileAge(){
		    return mileAge;
		    }
		    
		    public boolean isSold() {
			return sold;
		    }
*/
	
	public static void main(String[] args){
		
		EasyReader file = new EasyReader("WeatherData.txt");
		title = file.readString().split(",");
		System.out.println(title[9]);
		ArrayList<SimpleDataViewer> myData = new ArrayList<SimpleDataViewer>();
		for(int i=0; i<48; i++){
			String[] x = file.readString().split(",");
			myData.add(new SimpleDataViewer(x));
		
		}
		System.out.println("Welcome to the simple weather record viewer");
		System.out.println("Reading data from WeatherData.txt");
		System.out.println("What data do you want to display?");
			boolean finished = false;
			EasyReader keyboard = new EasyReader();
			String command;
		
		while(!finished){
			command = keyboard.readString(">>");
			switch(command) {
			case "temperature":
				for(SimpleDataViewer i : myData){
					System.out.println(i.getData()[0]+":	"+i.getData()[1]);
				}
			case "wind":
				for(SimpleDataViewer i : myData){
					System.out.println(i.getData()[0]+":	"+i.getData()[1]);
				}
			default :
				System.out.println("nothing");
					
			}
		}
			
	}
}