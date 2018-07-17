package uk.ac.sheffield.acc15jc;
import java.awt.Graphics;
import java.io.*;

import javax.swing.*;

import uk.ac.sheffield.com1003.EasyReader;
import uk.ac.sheffield.com1003.io.URLDownloader;

public class WeatherFileViewer {
	public static void main(String[]args) throws IOException{
//		EasyReader file = new EasyReader("WeatherData.txt");
//		SimpleDataArranger x = new SimpleDataArranger(file);
////		x.looper();
//		JFrame frm = new FrameViewer(x);
		String fileName = "weather.txt";
		FileRecorder fr = new FileRecorder(fileName);
		fr.setURL("EGCN");
		fr.write();
	}
}
