package mySource;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.JPanel;

import mySource.SimpleDataArranger.*;

public class DrawingPanel extends JPanel{
	public enum drawCommand{ NOT, TEM, PRES, WIND, PRECI, NODATA};//enable user to see different graph
	public void takeCommand(drawCommand d){dc = d;}
	private Graphics2D g2;
	private drawCommand dc=drawCommand.NOT;
	private ArrayList<obTime> times;//decides the x-coordinate of the Graph
	private ArrayList<Double> digits, digits2;	//decides the y-coordinate of the Graph
	private final int POINTOX =100, POINTOY = 700, PANELWIDTH = 600, PANELHEIGHT=600;
	private int dataLength, timeGap, maindate, mintime;//helpful-
	private double min, diff, avg, total, dataHead, dataTail;//informations-
	private double min2,diff2;//to draw-
	private boolean drawable=true, drawable2=true;//deal with the error data
	private String title="unknow", title2="unknow";
	public void setTitle(String t){	title = t;	}
	public void setTitle2(String t){title2 = t;	}
	public DrawingPanel(){
		setBackground(Color.YELLOW);
	}
	public void extract(Data d, ArrayList<obTime> t, int datalength) {//extract the helpful data
		drawable=false;
		timeGap = PANELWIDTH/24;
		times = t;
		dataLength = datalength;
		maindate=times.get(dataLength-1).getDate();
		if(d.convert()){
			digits = d.getDigits();
			min = d.getMin();
			diff = d.getDiff();
			avg = d.getAvg();
			total = d.getTol();
			dataHead = d.getHead();
			dataTail = d.getEnd();
			drawable = true;
		}
	}
	public void extract(Data d1, Data d2, ArrayList<obTime> t, int datalength){
		extract(d1, t, datalength);
		drawable2= false;
		if(d2.convert()){
			digits2 = d2.getDigits();
			min2 = d2.getMin();
			diff2 = d2.getDiff();
			drawable2=true;
		}
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g2 = (Graphics2D) g;
		paint();
		repaint();
	}
	private void paint() {//listen to the command from the user and display the wanted graph
		g2.setColor(Color.CYAN);
		switch(dc){
		case TEM: g2.fillRect(POINTOX-10, POINTOY-PANELHEIGHT-10, PANELWIDTH+20, PANELHEIGHT+20);
			temp(); break;
		case PRES: g2.fillRect(POINTOX-10, POINTOY-PANELHEIGHT-10, PANELWIDTH+20, PANELHEIGHT+20);
			pressure();break;
		case WIND: g2.fillRect(POINTOX-10, POINTOY-PANELHEIGHT-10, PANELWIDTH+20, PANELHEIGHT+20);
			wind();break;
		case PRECI: precipitation();break;
		case NODATA: g2.setColor(Color.BLACK);	g2.setFont(new Font(Font.SERIF, Font.ITALIC, 48));
			g2.drawString("No data here :)",  POINTOX,  POINTOY-PANELHEIGHT/2);	break; //deal with error data
		default:break;
		}
		g2.setColor(Color.RED);
		if(!drawable)//deal with error data
			g2.drawString("There are some error in data: "+title,  POINTOX,  POINTOY-PANELHEIGHT-30);
	}
	private void temp(){
		Color temColor=Color.BLACK;
		drawXAxies();
		if(drawable){
			drawLeftAxies();
			showData(new BasicStroke(2),temColor, digits, diff, min);//show the temperature trend
			g2.setColor(temColor);
			label(1, title);
			g2.drawString("Average temperature: "+avg, POINTOX+PANELWIDTH, POINTOY-(PANELHEIGHT/2)-5);
		}//display average temperature
	}
	private void pressure(){
		Color preColor=Color.MAGENTA;
		drawXAxies();
		if(drawable){
			drawLeftAxies();
			showData(new BasicStroke(2),preColor, digits, diff, min);//show the pressure trend line
			g2.setColor(preColor);
			label(1, title);
			String trendInfo = "";
			if(dataHead>dataTail)//show the trend info
				trendInfo = "pressure fall from: "+dataHead+" to "+dataTail;
			else if(dataHead<dataTail)
				trendInfo = "pressure raise from: "+dataHead+" to "+dataTail;
			else
				trendInfo = "pressure keep at: "+dataHead;
			g2.drawString(trendInfo, POINTOX+PANELWIDTH+20, POINTOY-PANELHEIGHT/2);	}}
	
	private void wind(){
		Color gustColor=Color.DARK_GRAY;	Color windColor=Color.RED;
		drawXAxies();
		if(drawable){
			drawLeftAxies();
			showData(new BasicStroke(2),gustColor, digits, diff, min);//show the gust speed trend line
			g2.setColor(gustColor);
			label(1, title);
		}
		if(drawable2){
			drawRightAxies();//show average wind speed
			Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
			showData(dashed, windColor, digits2, diff2, min2);
			g2.setColor(windColor);
			label(2, title2);
		}
	}
	private void precipitation(){
			g2.setFont(new Font(Font.SERIF, Font.ITALIC, 36));
		if(drawable){
			g2.setColor(Color.MAGENTA);
			g2.drawString(String.valueOf("The total precipitation is: "+total+"mm"),  POINTOX,  POINTOY-PANELHEIGHT/2);
		}
	}
	private void drawRightAxies(){//draw the Right Y-Axies
		g2.setStroke(new BasicStroke(2));		g2.setColor(Color.BLUE);
		g2.drawLine(POINTOX+PANELWIDTH, POINTOY-PANELHEIGHT, POINTOX+PANELWIDTH, POINTOY);
		g2.setStroke(new BasicStroke(1));	String value;
		for(int i=0; i<30; i++){
			value = String.valueOf((min2+((double)i)*diff2/30));//mark the axies from the minimum value in the data
			g2.drawString(value.substring(0, value.indexOf('.')+2),  POINTOX+PANELWIDTH+10,  POINTOY+5-i*PANELHEIGHT/30);
			g2.drawLine(POINTOX+PANELWIDTH, POINTOY-i*PANELHEIGHT/30, POINTOX, POINTOY-i*PANELHEIGHT/30);
		}
	}
	private void drawLeftAxies(){//draw the Left Y-Axies
		g2.setStroke(new BasicStroke(2));		g2.setColor(Color.BLUE);
		g2.drawLine(POINTOX, POINTOY-PANELHEIGHT, POINTOX, POINTOY);
		g2.setStroke(new BasicStroke(1));	String value;
		for(int i=0; i<30; i++){
			value = String.valueOf((min+((double)i)*diff/30));//mark the axies from the minimum value in the data
			g2.drawString(value.substring(0, value.indexOf('.')+2),  POINTOX-40,  POINTOY+5-i*PANELHEIGHT/30);
			g2.drawLine(POINTOX, POINTOY-i*PANELHEIGHT/30, POINTOX+PANELWIDTH, POINTOY-i*PANELHEIGHT/30);
		}
	}
	private void drawXAxies(){//draw the time axies
		g2.setStroke(new BasicStroke(2));	g2.setColor(Color.BLUE);
		g2.drawLine(POINTOX, POINTOY, POINTOX+PANELWIDTH, POINTOY);
		g2.setStroke(new BasicStroke(1));	mintime = times.get(0).getHour();
		for(int i=0; i<24; i++){
			if(mintime>=24){//deal with the date transfer
				mintime-=24;	g2.setStroke(new BasicStroke(2));	g2.setColor(Color.RED);
				g2.drawString(times.get(i+1).getFullDate(),  POINTOX-25+i*timeGap,  POINTOY+35);
				g2.drawLine(POINTOX+i*timeGap, POINTOY+20, POINTOX+i*timeGap, POINTOY-PANELHEIGHT);
				g2.setStroke(new BasicStroke(1));	g2.setColor(Color.BLUE);
			}
			else{
				g2.drawString(String.valueOf(mintime),  POINTOX+i*timeGap,  POINTOY+20);
				g2.drawLine(POINTOX+i*timeGap, POINTOY, POINTOX+i*timeGap, POINTOY-PANELHEIGHT);//draw the lattice
			}
			mintime+=1;
		}
		g2.setColor(Color.RED);
		g2.drawString("date UTC: "+times.get(dataLength-1).getFullDate(),  POINTOX+PANELWIDTH,  POINTOY+20);//show the date
	}
	private void showData(Stroke s, Color c, ArrayList<Double> dig, double diff, double min){
		int  currentX = 0, currentY;
		g2.setColor(c);	g2.setStroke(s);
		if(!dig.isEmpty()){
			int preX=timeToCo(times.get(0)), preY=toCoY(dig.get(0),diff,min);
			for(int i=0; i< dataLength; i++){
				currentX=timeToCo(times.get(i));	currentY=toCoY(dig.get(i),diff,min);//draw the trend
				g2.drawLine(preX, preY, currentX, currentY);
				preX=currentX;	preY=currentY;
			}
		}
		else
			g2.drawString("some error is in the data",  (POINTOX+PANELWIDTH)/2,  POINTOY-PANELHEIGHT/2);//report error data
	}
	private void label(int orders, String title){
			g2.drawLine(POINTOX+PANELWIDTH, POINTOY-PANELHEIGHT-20*orders, POINTOX+PANELWIDTH-80, POINTOY-PANELHEIGHT-20*orders);
			g2.drawString(title, POINTOX+PANELWIDTH+10, POINTOY-PANELHEIGHT-20*orders);//label the different data
			g2.drawString(title,  POINTOX-60+(orders-1)*PANELHEIGHT,  POINTOY-PANELHEIGHT);
	}
	private int timeToCo(obTime t){//convert the time to the coordinate of the graph
		int nextmonth = t.getDate()-maindate;
		if(nextmonth>1) nextmonth=-1;
		int co = POINTOX+(24-mintime)*timeGap+nextmonth*24*timeGap+t.getHour()*timeGap+(int)((double)t.getMinute()/60.0*timeGap);
		return co;
	}
	private int toCoY(double value, double diff, double min){//convert value of data to the coordinate of the graph
		int co = (int) (POINTOY-(PANELHEIGHT/diff*(value-min)));
		return co;
	}
}