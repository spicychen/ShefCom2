import java.util.ArrayList;

import uk.ac.sheffield.com1003.EasyReader;

public class Wvov {
	/**
	 * instance variable
	 * title ID
	 */
	enum ComandType {
		DATA, TOTAL, AVERAGE, TREND
	}
	private static final int[] TEMPERATURE_ID = {1};
	private static final int[] DEW_POINT_ID = {2};
	private static final int[] HUMIDITY_ID = {3};
	private static final int[] PRESSURE_ID = {4};
	private static final int[] VISIBILITY_ID = {5};
	private static final int[] WIND_ID = {6,7,12};
	private static final int[] GUST_SPEED_ID = {8};
	private static final int[] PRECIPITATION_ID = {9};
	private static final int[] EVENTS_ID = {10};
	private static final int[] WEATHER_ID = {11};
	private static final int DATE_ID = 13;
	private static final boolean SUM = false;
	private static final boolean AVERAGE = true;
	private String[] title;
	private ArrayList<String[]> myData= new ArrayList<String[]>();
	private String date;
	private int dataLength;
	public Wvov(EasyReader file){
		title = file.readString().split(",");
		boolean endOfFile = false;
		dataLength = 0;
		String[] x = file.readString().split(",");
		date = x[DATE_ID];
		while(!endOfFile){
			myData.add(x);
			x = file.readString().split(",");
			dataLength++;
			if(x[0].equals(""))
				endOfFile = true;
		}
	}
	/**void methods which do the print stuff*/
	public static void prompt(){
		System.out.println("Welcome to the simple weather record viewer");
		System.out.println("Reading data from WeatherData.txt");
		System.out.println("What data do you want to display?");
	}
	public void printInfo(String type, int[] titleId){
		System.out.println("data from "+this.getDate());
		System.out.print("displaying "+type);
		if(type.equals("")){
			for(int i: titleId){
				System.out.println(this.getTitle()[i]+"	");
			} 
			for(String[] i: this.getData()){
				System.out.print(i[0]+":	");
				for(int j: titleId)
					System.out.print(i[j]+"	");
				System.out.println();
			}
		}
	}
	public void takeCommand(String command){
		switch(command){
		case "TEMPERATURE": this.printInfo("",TEMPERATURE_ID);	break;
		case "DEW POINT": this.printInfo("",DEW_POINT_ID);	break;
		case "PRESSURE": this.printInfo("",PRESSURE_ID);	break;
		case "WIND": this.printInfo("",WIND_ID);	break;
		case "HUMIDITY": this.printInfo("",HUMIDITY_ID);	break;
		case "VISIBILITY": this.printInfo("",VISIBILITY_ID);	break;
		case "PRECIPITATION": this.printInfo("",PRECIPITATION_ID);	break;
		case "EVENTS": this.printInfo("",EVENTS_ID);	break;
		case "WEATHER": this.printInfo("",WEATHER_ID);	break;
		case "GUST SPEED": this.printInfo("",GUST_SPEED_ID);	break;
		case "TOTAL PRECIPITATION": 
			this.printInfo("Total ",PRECIPITATION_ID);
			System.out.println(this.sumOrAvg(SUM, PRECIPITATION_ID[0]));	break;
		case "AVERAGE TEMPERATURE": 
			this.printInfo("average ", TEMPERATURE_ID );
			System.out.println(this.sumOrAvg(AVERAGE, TEMPERATURE_ID[0]));	break;
		case "PRESSURE TREND":
			this.printInfo("trend of ",PRESSURE_ID);
			this.doTrend();
			break;
		default : System.out.println("sorry, no such shit.");
		}
	}
	public void looper(){
		EasyReader keyboard = new EasyReader();
		String command;
		prompt();
		boolean finished = false;
		while(!finished){
			System.out.print(">>");
			command = keyboard.readString().trim().toUpperCase();
			if(command.equals("QUIT")){
				finished = true;
				System.out.println("see you later");
			}
			else
				this.takeCommand(command);
		}
	}
	public void doTrend(){
		double[] models ={Double.parseDouble(this.getData().get(0)[PRESSURE_ID[0]]),
				Double.parseDouble(this.getData().get(this.getLength()-1)[PRESSURE_ID[0]])};
		String trend = "";
		if(models[0]!=models[1]){
			if(models[0]>models[1])
				trend = "falling";
			else
				trend = "rasing";
			System.out.println("pressure "+trend+" from "+models[0]+" to "+models[1]+"hPa");
		}
		else
			System.out.println("pressure keeps at "+models[0]+"hPa");
	}
	/**method
	 * @param doAVG
	 * @param titleID
	 * @return sum or average in that column
	 */
	public double sumOrAvg(boolean doAVG, int titleID){
		double sum=0;
		for(String[] i : this.getData()){
		    try { 
		        Double.parseDouble(i[titleID]); 
		    } catch(NumberFormatException e) { 
		    	System.out.println("some shit is in the datafile");
		        return 0; 
		    }
			sum += Double.parseDouble(i[titleID]);
		}
		if(doAVG)
			return sum/this.getLength();
		return sum;
	}
	/**Accessors @return title */
	public String[] getTitle(){return title;}
	/**Accessors @return title */
	public String getDate(){return date;}
	/**Accessors @return title */
	public ArrayList<String[]> getData(){return myData;}
	/**Accessors @return title */
	public int getLength(){return dataLength;}
	
	public static void main(String[]args){
		EasyReader file = new EasyReader("WeatherData.txt");
		Wvov x = new Wvov(file);
		x.looper();

	}

}
