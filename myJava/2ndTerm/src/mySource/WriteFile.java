package mySource;

import java.io.*;

public class WriteFile {
	public static void main(String[]args) throws IOException{
		String filename = "file1.txt";
		int[] data = {66,67,68};
		FileOutputStream fos = new FileOutputStream(filename);
		OutputStreamWriter osw = new OutputStreamWriter(fos);

		for(int byteToWrite: data){
			osw.write(byteToWrite);
		}
		osw.close();
		
		
		FileInputStream fis = new FileInputStream(filename);
		InputStreamReader isr = new InputStreamReader(fis);
		String stringRead = "";
		int charRead = isr.read();
		while(charRead != -1){
			stringRead += (char) charRead;
			charRead = isr.read();
		}
		isr.close();
		System.out.println(stringRead);
	}
}
