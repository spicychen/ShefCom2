import java.util.ArrayList;

import uk.ac.sheffield.com1003.EasyReader;
public class WeatherViewer {

	public WeatherViewer( String[] values){
		this.values = values;
    }
	
	public static void printInfo(int titleID, String date, ArrayList<WeatherViewer> myData){
		System.out.println("data from "+date);
		System.out.println("displaying "+title[titleID]);
		for(WeatherViewer j : myData){
			System.out.println(j.getData()[0]+":	"+j.getData()[titleID]);
		}
	}
	public static void printInfo(int t1, int t2, int t3, String date, ArrayList<WeatherViewer> myData){
		System.out.println("data from "+date);
		System.out.println("displaying "+title[t1]+"	"+title[t2]+"	"+title[t3]);
		for(WeatherViewer j : myData){
			System.out.println(j.getData()[0]+":	"+j.getData()[t1]+"	"+j.getData()[t2]+"	"+j.getData()[t3]);
		}
	}
	public static void sum(int titleID, String date, ArrayList<WeatherViewer> myData){
		System.out.println("data from "+date);
		System.out.println("displaying "+title[titleID]);
		double sum=0;
		for(WeatherViewer j : myData){
			sum += Double.parseDouble(j.getData()[titleID]);
		}
		System.out.println(sum/48);
	}
	public String getDate(){
		return values[13];
	}
	private static String[] title;
	private String[] values;
	private int dataLength;

	public String[] getData(){
			return values;
		    }
	public static void main(String[] args){
				EasyReader file = new EasyReader("WeatherData.txt");
				title = file.readString().split(",");

				ArrayList<WeatherViewer> myData = new ArrayList<WeatherViewer>();
				boolean endOfFile = false;
				int dataLength = 0;
				String[] x = file.readString().split(",");
				String date = x[13];
				
				while(!endOfFile){
					myData.add(new WeatherViewer(x));
					x = file.readString().split(",");
					dataLength++;
					if(x[0].equals(""))
						endOfFile = true;
				}
				System.out.println("\n Welcome to the simple weather record viewer"+ "/n");
				System.out.println("Reading data from WeatherData.txt");
				System.out.println("What data do you want to display?");

				boolean finished = false;
				EasyReader keyboard = new EasyReader();
				String command;
			
			while(!finished){
				System.out.print(">>");
				command = keyboard.readString().trim();
				switch(command.toUpperCase()){
				case "TEMPERATURE":
					printInfo(1,date,myData); break;
				case "PRESSURE":
					printInfo(4,date,myData); break;
				case "WEATHER":
					printInfo(11,date,myData); break;
				case "WIND":
					printInfo(6,7,8,date,myData); break;
				case "TOTAL PRECIPITATION":
					sum(9,date,myData); break;
				case "AVERAGE TEMPERATURE":
					sum(1,date,myData); break;
				
					
				default :
					System.out.println("sorry, no such shit.");
				}
			}
	}
}
