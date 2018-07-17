package uk.ac.sheffield.acc15jc;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JPanel;

import uk.ac.sheffield.acc15jc.SimpleDataArranger.*;

public class DrawingPanel extends JPanel{

	public enum drawCommand{ NOT, TEM, PRES, WIND, PRECI, NODATA};//enable user to see different graph
	public void takeCommand(drawCommand d){dc = d;}
	
	private Graphics2D g2;
	private drawCommand dc=drawCommand.NOT;
	private ArrayList<obTime> times;//decides the x-coordinate of the Graph
	private ArrayList<Double> digits;	//decides the y-coordinate of the Graph
	private final int POINTOX =100, POINTOY = 700, PANELWIDTH = 600, PANELHEIGHT=600;
	private int dataLength, timeGap, maindate, mintime;//helpful-
	private double min, diff, avg, total, dataHead, dataTail;//informations-
	private double min2,diff2, avg2;//to draw-
	private int headX=0, tailX=0;//the Graph
	private boolean drawable=true, drawable2=true;//deal with the error data
	private String title="unknow", title2="unknow";
	public void setTitle(String t){	title = t;	}
	public void setTitle2(String t){title2 = t;	}
	public DrawingPanel(){
		setBackground(Color.YELLOW);
	}
	public void extract(Data d, ArrayList<obTime> t) {//extract the helpful data
		drawable=false;
		timeGap = PANELWIDTH/24;
		times = t;
		if(d.convert()){
			dataLength = d.getLength();
			maindate=times.get(dataLength-1).getDate();
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
	public void extract(Data d1, Data d2, ArrayList<obTime> t){
		extract(d1, t);
		drawable2= false;
		if(d2.convert()){
			min2 = d2.getMin();
			diff2 = d2.getDiff();
			avg2 = d2.getAvg();
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
		if(drawable){
			drawLeftAxies();
			showData(temColor);
			label(1, title);
			g2.setColor(temColor);
			g2.drawString("Average temperature: "+avg, POINTOX+PANELWIDTH, POINTOY-(PANELHEIGHT/2)-5);
		}
	}
	private void pressure(){
		Color preColor=Color.MAGENTA;
		if(drawable){
			drawLeftAxies();
			showData(preColor);
			label(1, title);
			g2.setColor(preColor);
			String trendInfo = "";
			if(dataHead>dataTail)
				trendInfo = "pressure fall from: "+dataHead+" to "+dataTail;
			else if(dataHead<dataTail)
				trendInfo = "pressure raise from: "+dataHead+" to "+dataTail;
			else
				trendInfo = "pressure keep at: "+dataHead;
			g2.drawString(trendInfo, POINTOX+PANELWIDTH+20, POINTOY-PANELHEIGHT/2);	}}
	
	private void wind(){
		Color windColor=Color.DARK_GRAY;
		Color gustColor=Color.RED;
		if(drawable){
			drawLeftAxies();
			showData(gustColor);
			label(1, title);
			g2.setColor(gustColor);
		}
		g2.setColor(windColor);
		if(drawable2){
			label(2, title2);
			showState(avg2,avg2,diff2,min2);
			drawRightAxies();
			g2.drawString("AVERAGE WIND SPEED: "+avg2, POINTOX, toCoY(avg2,diff2,min2));
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
		drawXAxies();
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
		drawXAxies();
	}
	private void drawXAxies(){//draw the time axies
		g2.setStroke(new BasicStroke(2));	g2.setColor(Color.BLUE);
		g2.drawLine(POINTOX, POINTOY, POINTOX+PANELWIDTH, POINTOY);
		g2.setStroke(new BasicStroke(1));	mintime = times.get(0).getHour();
		for(int i=0; i<24; i++){
			if(mintime>=24){//distinct the date transfer
				mintime-=24;	g2.setStroke(new BasicStroke(2));	g2.setColor(Color.RED);
				g2.drawString(times.get(i+1).getFullDate(),  POINTOX-3+i*timeGap,  POINTOY-PANELHEIGHT);
				g2.drawLine(POINTOX+i*timeGap, POINTOY, POINTOX+i*timeGap, POINTOY-PANELHEIGHT); g2.setStroke(new BasicStroke(1));	g2.setColor(Color.BLUE);
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
	private void showData(Color c){
		int  currentX = 0, currentY;
		g2.setColor(c);	g2.setStroke(new BasicStroke(2));
		if(!digits.isEmpty()){
			int preX=timeToCo(times.get(0)), preY=toCoY(digits.get(0),diff,min);
			headX=preX;
			for(int i=0; i< dataLength; i++){
				currentX=timeToCo(times.get(i));	currentY=toCoY(digits.get(i),diff,min);//draw the trend
				g2.drawLine(preX, preY, currentX, currentY);
				preX=currentX;	preY=currentY;
			}
		}
		else
			g2.drawString("some shit is in the data",  (POINTOX+PANELWIDTH)/2,  POINTOY-PANELHEIGHT/2);//report error data
		tailX = currentX;
	}
	private void label(int orders, String title){
			g2.drawLine(POINTOX+PANELWIDTH, POINTOY-PANELHEIGHT-20*orders, POINTOX+PANELWIDTH-80, POINTOY-PANELHEIGHT-20*orders);
			g2.drawString(title, POINTOX+PANELWIDTH+10, POINTOY-PANELHEIGHT-20*orders);//label the axies
			g2.drawString(title,  POINTOX-60+(orders-1)*PANELHEIGHT,  POINTOY-PANELHEIGHT);
	}
	private void showState(double st, double end, double diff, double min){//shows some other information about the graph
		g2.setStroke(new BasicStroke(2));
		int stCo = toCoY(st, diff, min), endCo = toCoY(end,diff,min);
		g2.drawLine(headX, stCo, tailX, endCo);
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