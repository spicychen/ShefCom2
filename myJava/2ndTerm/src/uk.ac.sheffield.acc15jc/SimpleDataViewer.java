import java.util.ArrayList;

import uk.ac.sheffield.com1003.EasyReader;

public class SimpleDataViewer {
	enum CommandType {
		DATA, TOTAL, AVERAGE, TREND
	}
	//title IDs.
	private static final int[] TEMPERATURE_ID = {1}, DEW_POINT_ID = {2},
	HUMIDITY_ID = {3},	PRESSURE_ID = {4},	VISIBILITY_ID = {5},
	WIND_ID = {6,7,12},	GUST_SPEED_ID = {8},	PRECIPITATION_ID = {9},
	EVENTS_ID = {10},	WEATHER_ID = {11},	DATE_ID = {13};
	//instant field.
	private String[] title;
	private ArrayList<String[]> myData= new ArrayList<String[]>();
	private String date;
	private int dataLength;
	//enable the user to read command
	private EasyReader keyboard;
	/**constructor
	 * @param file*/
	public SimpleDataViewer(EasyReader file){
		//read the first line of the file as title.
		title = file.readString().split(",");
		//read lines till the end of file.
		boolean endOfFile = false;
		dataLength = 0;
		String[] lines = file.readString().split(",");
		//get the date of second line.
		date = lines[DATE_ID[0]].substring(0, 10);
		
		while(!endOfFile){
			myData.add(lines);
			lines = file.readString().split(",");
			//counting the number of (data) lines in the file.
			dataLength++;
			if(lines[0].equals(""))
				endOfFile = true;
		}
	}
	/**void method which print the head*/
	public static void prompt(){
		System.out.println("Welcome to the simple weather record viewer");
		System.out.println("Reading data from WeatherData.txt");
		System.out.println("What data do you want to display?");
	}
	/**void method which print data*/
	public void printInfo(CommandType type, int[] titleId){
		//print date and titles
		System.out.println("data from "+this.getDate());
		System.out.print("displaying "+type.name().toLowerCase()+":	");
			for(int i: titleId){
				System.out.print(this.getTitle()[i]+"	");
			}
			System.out.println();
		//print data
		if(type==CommandType.DATA){
			for(String[] i: this.getData()){
				System.out.print(i[0]+":	");
				for(int j: titleId){
					//handle the error data
					if(i[j].equals("N/A")|| i[j].equals("") ||
						i[j].equals("Calm")|| i[j].substring(0,1).equals("-"))
						System.out.print("Unknown	");
					else
						System.out.print(i[j].replace("<br />", "")+"	");
				}
				System.out.println();
			}
		}
	}
	/**take command and give information accordingly
	* @param command
	* */
	public void takeCommand(String command){
		switch(command){
		case "TEMPERATURE": this.printInfo(CommandType.DATA,TEMPERATURE_ID);	break;
		case "DEW POINT": this.printInfo(CommandType.DATA,DEW_POINT_ID);	break;
		case "PRESSURE": this.printInfo(CommandType.DATA,PRESSURE_ID);	break;
		case "WIND": this.printInfo(CommandType.DATA,WIND_ID);	break;
		case "HUMIDITY": this.printInfo(CommandType.DATA,HUMIDITY_ID);	break;
		case "VISIBILITY": this.printInfo(CommandType.DATA,VISIBILITY_ID);	break;
		case "PRECIPITATION": this.printInfo(CommandType.DATA,PRECIPITATION_ID);	break;
		case "EVENTS": this.printInfo(CommandType.DATA,EVENTS_ID);	break;
		case "WEATHER": this.printInfo(CommandType.DATA,WEATHER_ID);	break;
		case "GUST SPEED": this.printInfo(CommandType.DATA,GUST_SPEED_ID);	break;
		case "UTC DATE": this.printInfo(CommandType.DATA,DATE_ID);	break;
		case "TOTAL PRECIPITATION": 
			this.printInfo(CommandType.TOTAL,PRECIPITATION_ID);
			System.out.println(this.calculate(CommandType.TOTAL, PRECIPITATION_ID[0]));	break;
		case "AVERAGE TEMPERATURE": 
			this.printInfo(CommandType.AVERAGE, TEMPERATURE_ID );
			System.out.println(this.calculate(CommandType.AVERAGE, TEMPERATURE_ID[0]));	break;
		case "PRESSURE TREND":
			this.printInfo(CommandType.TREND,PRESSURE_ID);
			System.out.println(this.calculate(CommandType.TREND, PRESSURE_ID[0])+"hPa");	break;
		default : System.out.println("sorry, no such shit. (command is not recogised)");
		}
	}
	/**repeat taking command from user util he quit */
	public void looper(){
		keyboard = new EasyReader();
		//read command from user
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
	/**method which calculate the sum or average in that column
	 * @param doAVG
	 * @param titleID
	 * @return sum or average
	 */
	public double calculate(CommandType t, int titleID){
		//make sure that the data is a number
		for(String[] i : this.getData()){
		    try { 
		        Double.parseDouble(i[titleID]); 
		    } catch(NumberFormatException e) { 
		    	System.out.println("some data is not number");
		        return 0; 
		    }
		}
		//do calculation, convert the data to double
		if(t!=CommandType.TREND){
			double sum=0;
			for(String[] i : this.getData()){
				sum += Double.parseDouble(i[titleID]);
			}
			if(t==CommandType.AVERAGE)
				return sum/this.getLength();
			return sum;
		}
		//compare first data and last one, then print the trend accordingly
		else{
			double[] trendData ={Double.parseDouble(this.getData().get(0)[titleID]),
				Double.parseDouble(this.getData().get(this.getLength()-1)[titleID])};
			String trend = "";
				if(trendData[0]!=trendData[1]){
					if(trendData[0]>trendData[1])
						trend = "falling";
					else
						trend = "rasing";
					System.out.print("pressure "+trend+" from "+trendData[0]+" to ");
				}
				else
					System.out.print("pressure keeps at ");
		    return trendData[1];
		}
			
		
	}
	/**Accessor @return title */
	public String[] getTitle(){return title;}
	/**Accessor @return date */
	public String getDate(){return date;}
	/**Accessor @return myData */
	public ArrayList<String[]> getData(){return myData;}
	/**Accessor @return dataLength */
	public int getLength(){return dataLength;}
	
}