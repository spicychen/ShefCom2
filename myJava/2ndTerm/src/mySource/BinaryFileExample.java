package mySource;
import java.io.*;
public class BinaryFileExample {
	
	public static void main(String[]args) throws IOException{
		String fileName = "binarydata.txt";
		int[]data ={65,66,67};
		
		//open a file
		FileOutputStream fos = new FileOutputStream(fileName);
		OutputStreamWriter osw = new OutputStreamWriter(fos);
//		//write the data from the data array
		for(int byteToWrite: data){
			osw.write(byteToWrite);
		}
		osw.close();
		//close the stream
		fos.close();
		
		//now read the data back in with a FileInputStream
		FileInputStream fis = new FileInputStream(fileName);
		InputStreamReader isr = new InputStreamReader(fis);
		String stringRead = "";
		
		int readByte = fis.read();
		while(readByte !=-1){
			stringRead += (char) readByte;
			readByte = fis.read();
		}

		System.out.println(stringRead);
		//close the stream
		fis.close();
	}

}
