package WeatherViewer;

import uk.ac.sheffield.com1003.EasyReader;

public class RunWeatherViewer {
	public static void main(String[]args){

		EasyReader file = new EasyReader("WeatherData.txt");
		SimpleDataViewer x = new SimpleDataViewer(file);
		x.looper();
	}

}
