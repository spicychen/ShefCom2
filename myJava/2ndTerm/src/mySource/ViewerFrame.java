package mySource;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

import mySource.DrawingPanel.drawCommand;

import java.util.*;

public class ViewerFrame extends JFrame implements ActionListener{

	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	private Dimension screenDimensions = toolkit.getScreenSize();
	private final int TEMPERATURE_ID = 1, PRESSURE_ID = 4, GUST_SPEED_ID = 8, WIND_ID = 7, PRECIPITATION_ID = 9;
	private final int VFWIDTH = 400, VFHEIGHT = 200, VFX = (screenDimensions.width-VFWIDTH)/2, VFY = (screenDimensions.height-VFHEIGHT)/2;
	private JPanel myPanel;
	private HashMap<String, String> ICAOcode = new HashMap<String,String>();
	private JComboBox<String> ICAObox;
	private JComboBox<Integer> yearBox, monthBox, dateBox;
	private Container ctn;
	private FileRecorder fr;
	
	public ViewerFrame(){
		setTitle("WeatherViewer4.0");
		setSize(VFWIDTH, VFHEIGHT);
		setResizable(false);
		setLocation(VFX, VFY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ctn = getContentPane();
	}
	
	public void buildCode(HashMap<String, String> c) {//build the ICAOcode
		ICAOcode=c;
		
		start();
		setVisible(true);
	}
	
	private void start() {
		fr = new FileRecorder("weather.txt");//prepare to write a file
		
		myPanel = new JPanel();
		ctn.add(myPanel);
		myPanel.setLayout(new FlowLayout());//ComboBox JPanel
		
		ICAObox = new JComboBox<String>();

		myPanel.add(new JLabel("City"));
		for(String s: ICAOcode.keySet())//add ICAOcode to comboBox
			ICAObox.addItem(s);
		ICAObox.setSelectedIndex(5);
		myPanel.add(ICAObox);
		
		myPanel.add(new JLabel("Year"));
		yearBox = new JComboBox<Integer>();
		int currentYear=Calendar.getInstance().get(1);//link to System's current Year
		for(int i=currentYear; i>=currentYear-10; i--)
			yearBox.addItem(i);
		myPanel.add(yearBox);

		myPanel.add(new JLabel("Month"));
		monthBox = new JComboBox<Integer>();
		for(int i=1; i<=12; i++)
			monthBox.addItem(i);
		monthBox.setSelectedItem(Calendar.getInstance().get(Calendar.MONTH)+1);
		myPanel.add(monthBox);

		myPanel.add(new JLabel("Date"));
		dateBox = new JComboBox<Integer>();
		for(int i=1; i<=31; i++)//(31, Feb, 2016) is fine, the same as 2nd March 2016.
			dateBox.addItem(i);
		dateBox.setSelectedItem(Calendar.getInstance().get(Calendar.DATE));
		myPanel.add(dateBox);
		
		JButton submit = new JButton("Submit");
		myPanel.add(submit);
		submit.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		String code = ICAOcode.get(ICAObox.getSelectedItem());//map the city name to the ICAOcode
		String urlStr = "https://www.wunderground.com/history/airport/"
				+ code+"/"+ yearBox.getSelectedItem()+"/"+ monthBox.getSelectedItem()+"/"+ dateBox.getSelectedItem()
				+ "/DailyHistory.html?HideSpecis=1&format=1";
		fr.setURL(urlStr);	//set the url link to what user choose
		try {
			URL url = new URL(urlStr); 
			URLConnection connection = url.openConnection();
			HttpURLConnection htCon = (HttpURLConnection) connection;
			int repcode = htCon.getResponseCode();
			String message = htCon.getResponseMessage();
			System.out.println(repcode + " " + message);
			if (repcode == HttpURLConnection.HTTP_OK)//check if network is ok
				fr.write();//store data to a file
			else
				System.out.println("url not regonised");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		this.setVisible(false);
		try {
			new View();	//open another Frame to view data
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	class View extends JFrame implements ActionListener{
		
		private final int vWidth=1000, vHeight=800, vX=(screenDimensions.width-vWidth)/2, vY=(screenDimensions.height-vHeight)/2;
		private Container vcon;
		private DrawingPanel dp;
		private JPanel buttonPanel;
		private String[] title = {"Temperature","Atomospheric Pressure","Wind","total Precipitation","Clean","go Back"};
		private SimpleDataArranger sda;
		
		public View() throws FileNotFoundException{
			sda = new SimpleDataArranger(new Scanner(new File("weather.txt")));//read the data file
			setTitle("WeatherViewer4.0");
			setSize(vWidth, vHeight);
			setResizable(false);
			setLocation(vX, vY);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			vcon = getContentPane();
			
			dp = new DrawingPanel();
			vcon.add(dp, BorderLayout.CENTER);
			
			doViewing();//enable the buttons to view data
			
			setVisible(true);
		}
		public void doViewing(){
			buttonPanel = new JPanel();
			vcon.add(buttonPanel,BorderLayout.SOUTH);
			for(String s: title)//buttons to get different info
				makeButton(buttonPanel, s, this);
		}
		public void makeButton(JPanel p, String name, ActionListener target) {
			JButton b = new JButton(name);
			p.add(b);
			b.addActionListener(target);
		}
		public void actionPerformed(ActionEvent e) {
			if(sda.isDisplayable()){//if data available
				if(e.getActionCommand().equals(title[0])){
					dp.setTitle("Temperature, C");
					dp.extract(sda.getData()[TEMPERATURE_ID], sda.getTimes(), sda.getLength());//extract the data and convert to coordinates on the Graph
					dp.takeCommand(drawCommand.TEM);//take the command of the button and display
				}
				else if(e.getActionCommand().equals(title[1])){
					dp.setTitle("Pressure, Pa");
					dp.extract(sda.getData()[PRESSURE_ID], sda.getTimes(), sda.getLength());
					dp.takeCommand(drawCommand.PRES);
				}
				else if(e.getActionCommand().equals(title[2])){
					dp.setTitle("Gust Speed, Km/h");
					dp.setTitle2("Wind Speed, Km/h");
					dp.extract(sda.getData()[GUST_SPEED_ID],sda.getData()[WIND_ID], sda.getTimes(), sda.getLength());
					dp.takeCommand(drawCommand.WIND);
				}
				else if(e.getActionCommand().equals(title[3])){
					dp.setTitle("Precipitation mm");
					dp.extract(sda.getData()[PRECIPITATION_ID], sda.getTimes(), sda.getLength());
					dp.takeCommand(drawCommand.PRECI);
				}
				else
					dp.takeCommand(drawCommand.NOT);
			}
			else
				dp.takeCommand(drawCommand.NODATA);
			if(e.getActionCommand().equals(title[5])){
					this.dispose();//disable the frame
					ViewerFrame.this.setVisible(true);//and return to the previous frame
				}
		}
	}
}