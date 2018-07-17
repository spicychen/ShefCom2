package mySource;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class FileRecorder{
	private String filename;
	public String getName(){return filename;}
	private String url;
	
	public FileRecorder(String n){
		filename = n;
	}
	
	public void setURL(String s){
		url = s;
		System.out.println(url);
	}
	
	public void write() throws IOException{//download the file from a url
		URL u = new URL(url); 
		URLConnection connection = u.openConnection();
		
		InputStream inStream = connection.getInputStream();
		Scanner in = new Scanner(inStream);
		
		PrintWriter pw = new PrintWriter(filename);
		
		in.nextLine();
		while(in.hasNextLine()){
			String inputLine = in.nextLine();
			String input = inputLine.replace("<br />", "").trim();//organise the file
			pw.println(input);
		}
		pw.flush();
		pw.close();
		
	}
	
	public static void main(String[]args) throws IOException{
		FileRecorder fr = new FileRecorder("weather.txt");
		fr.setURL("https://www.wunderground.com/history/airport/sheffield/2015/11/11/DailyHistory.html?HideSpecis=1&format=1");
		fr.write();
	}

}
