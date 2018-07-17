package WeatherViewer;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;

public class GUIWeatherViewer extends JFrame{
	public GUIWeatherViewer(){
		setTitle("Weather Viewer 1.0");
		setSize(600, 800);
		Container c = this.getContentPane();
		c.setBackground(Color.BLUE);
	}
	public static void main(String[]args){
		GUIWeatherViewer a = new GUIWeatherViewer();
		a.setVisible(true);
	}

}
