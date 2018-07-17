package uk.ac.sheffield.acc15jc;

import java.io.*;
import java.util.*;

public class SimpleDataArranger {
	//instant field.
	private Scanner file;
	private String[] title;
	private Data[] myDatas= new Data[14];
	private ArrayList<obTime> times= new ArrayList<obTime>();
	private int dataLength;
	private boolean displayable;
	//enable the user to read command

	public SimpleDataArranger(Scanner file){
		this.file = file;
		arrange();
	}
	public void arrange(){
		displayable=false;
		title = file.nextLine().split(",");		//read the first line of the file as title.
		dataLength = 0;
		String[] lines = file.nextLine().split(",");
		if(lines.length>=13){	//deal with the error
			displayable=true;	//ensure there is some data in the file
			int columns = lines.length;
			for(int i=0; i<columns; i++)
				myDatas[i] = new Data(new ArrayList<String>());
			obTime time;
			while(file.hasNextLine()){
				for(int i=0; i<columns; i++)
					myDatas[i].addData(lines[i]);
				time = new obTime(lines[13]);
				times.add(time);
				lines = file.nextLine().split(",");
				//counting the number of (data) lines in the file.
				dataLength++;
			}
		}
		else
			System.out.println("no data here :)");
	}
	/**Accessor @return title */
	public String[] getTitle(){return title;}
	/**Accessor @return myData */
	public Data[] getData(){return myDatas;}
	/**Accessor @return dataLength */
	public int getLength(){return dataLength;}
	public ArrayList<obTime> getTimes(){return times;}
	public boolean isDisplayable(){return displayable;}
	
	class obTime{
		private String yearmonthdate;
		private int date, hour, minute;
		public String getFullDate(){return yearmonthdate;}//infer the full date to the user
		public int getDate(){return date;}
		public int getHour(){return hour;}
		public int getMinute(){return minute;}
		public obTime(String timeStr){
			convertTime(timeStr);
		}
		private void convertTime(String timeStr) {//convert the string of time to numbers
			yearmonthdate=timeStr.substring(0,10);
			date=Integer.parseInt(timeStr.substring(8, 10));
			String time = timeStr.substring(11);
			String[] times = time.split(":");
			hour = Integer.parseInt(times[0]);
			minute = Integer.parseInt(times[1]);
		}
	}
	
	class Data{
		private ArrayList<String> datas;
		private ArrayList<Double> digits;
		double min, max, diff, total, periodHead, periodEnd, avg;
		public int getLength(){return dataLength;}
		public double getMin(){return min;}
		public double getMax(){return max;}
		public double getDiff(){return diff;}
		public double getTol(){return total;}
		public double getHead(){return periodHead;}
		public double getEnd(){return periodEnd;}
		public double getAvg(){return avg;}
		public Data(ArrayList<String> dt){
			datas =dt;
			digits = new ArrayList<Double>();
		}
		ArrayList<String> getData(){
			return datas;
		}
		ArrayList<Double> getDigits(){
			return digits;
		}
		private void addData(String d){
			datas.add(d);
		}
		public boolean convert(){
			boolean convertable = true;
			digits.clear();
			min = Double.POSITIVE_INFINITY; max=0; total=0;
			int j=0;
			try {
			for(String i: datas){
				if(i.equals("Calm")){
					digits.add(0.0);
					if(min>0.0)
						min=0.0;
				}
				else{
					double currentDigit = Double.parseDouble(i);
					digits.add(currentDigit);
					if(min>currentDigit)
						min = currentDigit;
					if(max<currentDigit)
						max = currentDigit;
					total+=currentDigit;
					periodEnd = currentDigit;
				}
				j++;
				}
			periodHead = digits.get(0);
			avg = total/j;
			} catch(NumberFormatException e) { 
				System.out.println("some shit is in the data");
				convertable=false;
			}
				diff = max-min;
				System.out.println(digits.size());
				return convertable;
		}
		
	}
}
