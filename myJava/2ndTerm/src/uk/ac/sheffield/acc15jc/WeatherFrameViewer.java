package uk.ac.sheffield.acc15jc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.awt.*;

import javax.swing.*;

import uk.ac.sheffield.acc15jc.SimpleDataArranger.Data;
import uk.ac.sheffield.com1003.EasyReader;

public class WeatherFrameViewer extends JFrame implements ActionListener{
	
	private FileRecorder fr;
	private JPanel showPanel;
	private final int WINWID=300, WINHEI = 200, WINX = 800, WINY = 200;
	private Container contentPane;
	private enum drawCommand{ NOT, TEM, PRES, WIND, PRECI};
	private drawCommand dc = drawCommand.NOT;
	public String[] ICAOCodes = {"EBAM","EBAR","EBAW","EBBE","EBBL"};
	private JComboBox<String> comboBox1 = new JComboBox<String>(), comboBox2 = new JComboBox<String>(), 
			comboBox3 = new JComboBox<String>(), comboBox4 = new JComboBox<String>();
	
	public WeatherFrameViewer(){
		setTitle("WeatherViewer 4.0");
		setLocation(WINX,WINY);
		showPanel = new JPanel();
		contentPane = getContentPane();
		contentPane.add(showPanel);
		fr = new FileRecorder("weather.txt");
		start();
		setVisible(true);
	}
	
	private void start(){
		setSize(WINWID,WINHEI);
		
		showPanel.removeAll();
		showPanel.setLayout(new FlowLayout());
		for(String i: ICAOCodes)
			comboBox1.addItem(i);
		for(int i=2000; i<2017; i++)
			comboBox2.addItem(String.valueOf(i));
		for(int i=1; i<=12; i++)
			comboBox3.addItem(String.valueOf(i));
		for(int i=1; i<=31; i++)
			comboBox4.addItem(String.valueOf(i));
		showPanel.add(comboBox1);
		showPanel.add(comboBox2);
		showPanel.add(comboBox3);
		showPanel.add(comboBox4);
		JButton a= new JButton("submit");
		showPanel.add(a);
		a.addActionListener(this);
	}

		public void actionPerformed(ActionEvent e) {
			String url = "https://www.wunderground.com/history/airport/"
					+ comboBox1.getSelectedItem()+"/"+ comboBox2.getSelectedItem()+"/"+ comboBox3.getSelectedItem()+"/"+ comboBox4.getSelectedItem()
					+ "/DailyHistory.html?HideSpecis=1&format=1";
				fr.setURL(url);
				try {
					fr.write();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			this.dispose();
			try {
				ViewerFrame vf =new ViewerFrame();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		private class ViewerFrame extends JFrame implements ActionListener{
			
			private final int WINWID=800, WINHEI=800, WINX=600, WINY=100;
			private Container viewerCon;
			private File datafile = new File("weather.txt");
			private Scanner file;
			private SimpleDataArranger sdv;
			private MyPanel mp = new MyPanel();
			String[] title = {"Temperature","Atomospheric Pressure","Wind","total Precipitation"};
			private Data data, time = sdv.getData()[0];
			public ViewerFrame() throws FileNotFoundException{
				setTitle("WeatherViewer 4.0");
				setSize(WINWID,WINHEI);
				setLocation(WINX,WINY);
				this.setResizable(false);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				viewerCon = getContentPane();
				file = new Scanner(datafile);
				sdv = new SimpleDataArranger(file);
				view();
				setVisible(true);
			}
			
			public void view(){
				System.out.println("hell");
				viewerCon.add(showPanel, BorderLayout.SOUTH);
				viewerCon.add(mp, BorderLayout.CENTER);
				showPanel.removeAll();
				showPanel.setLayout(new GridLayout(1,0));
				for(String s: title)
					makeButton(showPanel, s, this);
				
			}

			private void makeButton(JPanel p, String name, ActionListener target) {
				JButton b = new JButton(name);
				p.add(b);
				b.addActionListener(target);
			}
			
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals(title[0]))
					dc = drawCommand.TEM;
				else if(e.getActionCommand().equals(title[1]))
					dc = drawCommand.PRES;
				else if(e.getActionCommand().equals(title[2]))
					dc = drawCommand.WIND;
				else if(e.getActionCommand().equals(title[3]))
					dc = drawCommand.PRECI;
				else
					dc = drawCommand.NOT;
			}

			class MyPanel extends JPanel{
				private Graphics2D g2;
				private DrawGraph dg = new DrawGraph();
				public MyPanel(){
					setBackground(Color.YELLOW);
				}
				public void paintComponent(Graphics g){
					super.paintComponent(g);
					g2 = (Graphics2D) g;
					paint();
					repaint();
				}
				private void paint(){
					switch(dc){
						case TEM: temp(); break;
						case PRES: pressure();break;
						case WIND: wind();break;
						case PRECI: precipitation();
					}
				}
				
				class DrawGraph{
					private final int POINTOX =100, POINTOY = 700, PANELWIDTH = 600, PANELHEIGHT=600;
					private int dataLength, timeGap, tileCo;
					private Data currentData;
					private void setData(Data d){
						currentData =d;
						dataLength = currentData.getLength();
						min = currentData.getMin();
						diff = currentData.getDiff();
						}
					private double min, diff;
					
					private void drawAxies(){
						timeGap=PANELWIDTH/(dataLength-4);
						
						g2.setColor(Color.WHITE);
						g2.fillRect(POINTOX-10, POINTOY-PANELHEIGHT-10, PANELWIDTH+20, PANELHEIGHT+20);
						int shinkLength = sdv.getLength()/3;

						g2.setStroke(new BasicStroke(2));		g2.setColor(Color.BLUE);
						g2.drawLine(POINTOX, POINTOY-PANELHEIGHT, POINTOX, POINTOY);
						g2.drawLine(POINTOX, POINTOY, POINTOX+PANELWIDTH, POINTOY);

						String value;
						for(int i=0; i<30; i++){
							value = String.valueOf((min+((double)i)*diff/30));
							g2.drawString(value.substring(0, value.indexOf('.')+2),  POINTOX-40,  POINTOY-i*PANELHEIGHT/30);
							g2.drawLine(POINTOX, POINTOY-i*PANELHEIGHT/30, POINTOX+20, POINTOY-i*PANELHEIGHT/30);
						}
						for(int i=0; i<shinkLength; i++){
							value = time.getData().get(i*3);
							g2.drawString(value.substring(0, value.indexOf(':')),  POINTOX+3*i*timeGap,  POINTOY+20);
							g2.drawLine(POINTOX+3*i*timeGap, POINTOY, POINTOX+3*i*timeGap, POINTOY-20);
						}

						g2.setColor(Color.RED);
						g2.drawString("time",  POINTOX+PANELWIDTH,  POINTOY);
						
					}
					
					private void showData(Color c){
						
						data.convert();
						int  currentX = 0, currentY;
						g2.setColor(c);
						if(!data.getDigits().isEmpty()){
							int preX=POINTOX, preY=toCo(data.getDigits().get(0));
							
							int timeGap = PANELWIDTH/(sdv.getLength()-4);
							
							
							for(int i=0; i< dataLength; i++){
								currentX=POINTOX+timeGap*i;	currentY=toCo(data.getDigits().get(i));
								g2.drawLine(preX, preY, currentX, currentY);
								preX=currentX;	preY=currentY;
							}
							
							g2.drawLine(POINTOX+PANELWIDTH, POINTOY-PANELHEIGHT, POINTOX+PANELWIDTH-80, POINTOY-PANELHEIGHT);
							g2.drawString("sfa", POINTOX+PANELWIDTH+10, POINTOY-PANELHEIGHT);
						}
						else{	
							g2.drawString("some shit is in the data",  (POINTOX+PANELWIDTH)/2,  POINTOY-PANELHEIGHT/2);
							
						}
						
						tileCo = currentX;
						
					}
					
					private void showState(double st, double end){
						int stCo = toCo(st), endCo = toCo(end);
						g2.drawLine(POINTOX, stCo, tileCo, endCo);
					}
					
					private int toCo(double value){
						int co = (int) (POINTOY-(PANELHEIGHT/diff*(value-min)));
						return co;
					}
					
				}
				private final int POINTOX =100, POINTOY = 700, PANELWIDTH = 600, PANELHEIGHT=600;
				private void temp(){
					final int columnNo = 1;
					data = sdv.getData()[columnNo];
					dg.setData(data);
					dg.drawAxies();
					dg.showData(Color.GRAY);
					dg.showState(data.getHead(), data.getEnd());
					g2.drawString(sdv.getTitle()[columnNo], POINTOX, POINTOY-PANELHEIGHT);
					g2.drawString("Average temperature: "+String.valueOf(data.getAvg()), POINTOX+PANELWIDTH-80, POINTOY-(PANELHEIGHT/2));
					
				}
				private void pressure(){
					final int columnNo = 4;
					data = sdv.getData()[columnNo];
					dg.setData(data);
					dg.drawAxies();
					dg.showData(Color.GREEN);
					g2.drawString(sdv.getTitle()[columnNo], POINTOX, POINTOY-PANELHEIGHT);
					double head = data.getHead(), end = data.getEnd();
					String trendInfo = "";
					if(head>end)
						trendInfo = "pressure fall from: "+head+" to "+end;
					else if(head<end)
						trendInfo = "pressure raise from: "+head+" to "+end;
					else
						trendInfo = "pressure keep at: "+head;
						
					g2.drawString(trendInfo, POINTOX+PANELWIDTH-150, POINTOY-PANELHEIGHT/2);
				}
				private void wind(){
					final int columnNo = 7;
					data = sdv.getData()[columnNo];
					dg.setData(data);
					dg.drawAxies();
					dg.showData(Color.ORANGE);
					dg.showState(data.getAvg(), data.getAvg());
					g2.drawString(sdv.getTitle()[columnNo], POINTOX, POINTOY-PANELHEIGHT);
					data = sdv.getData()[8];
					dg.showData(Color.PINK);
					
				}
				private void precipitation(){
					final int columnNo = 9;
					data = sdv.getData()[columnNo];
					data.convert();
					g2.setFont(new Font(Font.SERIF, Font.ITALIC, 48));
					if(data.getDigits().isEmpty()){		g2.setColor(Color.RED);
						g2.drawString("some shit is in the data",  POINTOX,  POINTOY-PANELHEIGHT/2);
					}
					else{		g2.setColor(Color.GREEN);
						g2.drawString(String.valueOf("The total precipitation is: "+data.getTol()),  POINTOX,  POINTOY-PANELHEIGHT/2);
						
					}
				}
			}
			
		}
		
	public static void main(String[]args){
		WeatherFrameViewer wv= new WeatherFrameViewer();
	}


}
