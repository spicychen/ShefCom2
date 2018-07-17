import uk.ac.sheffield.com1003.EasyReader;
import java.util.ArrayList;
import java.util.Iterator;
public class ComplexDataViewer {
	
	public ComplexDataViewer( String[] values){
		this.values = values;
    }
	public static boolean isDouble(String s) {
	    try { 
	        Double.parseDouble(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }

	    return true;
	}
	private static String[] title;
	private String[] values;

	
	public String[] getData(){
			return values;
		    }
	
	public static void main(String[] args){
		
		EasyReader file = new EasyReader("WeatherData.txt");
		title = file.readString().split(",");
		int columnNo = title.length;
		System.out.println(title[9]);
		ArrayList<ComplexDataViewer> myData = new ArrayList<ComplexDataViewer>();
		String[] x = file.readString().split(",");
		String[] sample =x;
		String date = x[13];
				System.out.println(isDouble(x[3]));
		boolean endOfFile = false;
		int dataLength = 0;
		while(!endOfFile){
			myData.add(new ComplexDataViewer(x));
			x = file.readString().split(",");
			dataLength++;
			if(x[0].equals(""))
				endOfFile = true;
		}
		System.out.println(isDouble(sample[1]));
		System.out.println("Welcome to the simple weather record viewer");
		System.out.println("Reading data from WeatherData.txt");
		System.out.println("What data do you want to display?");
			boolean finished = false;
			EasyReader keyboard = new EasyReader();
			String command;
		
		while(!finished){
			System.out.print(">>");
			command = keyboard.readString().trim();
			
			int[] displayNo= new int[columnNo];
			int counting =0;
			if(command.toUpperCase().contains("AVERAGE")){
					String s=command.toUpperCase().replace("AVERAGE", "").trim();
				for(int i=0; i<columnNo;i++){
					if(title[i].toUpperCase().contains(s) && isDouble(sample[i])){
						double d=0;
						System.out.println("displaying average "+title[i]);
					for(ComplexDataViewer j : myData){
							d += Double.parseDouble(j.getData()[i]);
					}
						System.out.println(d/dataLength);
					}

				}
			}
			else{
				for(int i=0; i<columnNo;i++)
					if(title[i].toUpperCase().contains(command.toUpperCase())){
						displayNo[counting]=i;
						counting++;
					}
				if(counting != 0){
					System.out.println("data from "+date);
					System.out.print("displaying ");
					for(int i=0; i<counting;i++)
							System.out.print(title[displayNo[counting]] + "  ");
					System.out.println();
					
					for(ComplexDataViewer j : myData){
						System.out.print(j.getData()[0]+":	");
						for(int i=0; i<counting; i++)
							System.out.print(j.getData()[displayNo[i]]+"	");
						System.out.println();
						}
				}
				else if(command.toUpperCase().equals("QUIT")){
					System.out.println("quitting now...");
					finished = true;
				}
				else
					System.out.println("sorry, no such shit");
			}
		}
	}
}
