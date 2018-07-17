import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyPolygon extends JFrame implements ActionListener{
	final Toolkit toolkit = Toolkit.getDefaultToolkit();
	final Dimension screenDimensions = toolkit.getScreenSize();
	private ThePolygon tp;
	
	public MyPolygon() {
		setTitle("A Centred Frame");
		setSize(screenDimensions.width/2,screenDimensions.height/2);
		setLocation(new Point(screenDimensions.width/4,screenDimensions.height/4));
	JPanel cb = new JPanel(new GridLayout(0,1));
	tp = new ThePolygon();
	

	for(int i =3; i<10; i++)
		makeButton(cb, String.valueOf(i), this);
	makeButton(cb, "Exits", this);

	Container con = getContentPane();
	con.add(cb, BorderLayout.EAST);
	con.add(tp, BorderLayout.CENTER);

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Exits"))
			this.dispose();
		// TODO Auto-generated method stub
		else{
		tp.clear();
		repaint();
		tp.praw(Integer.parseInt(e.getActionCommand()));
		}
		System.out.println(e.getActionCommand());
	}
	private void makeButton(JPanel p, String name, ActionListener target) {
		JButton b = new JButton(name);
		p.add(b);
		b.addActionListener(target);
	
}
	
	public static void main(String[]args){

		JFrame frm = new MyPolygon();
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.setVisible(true);
	}
	class ThePolygon extends JPanel{
		private BufferedImage image =null;
		private Graphics2D g2image;
		public ThePolygon(){
			setBackground(Color.YELLOW);
			image =new BufferedImage(screenDimensions.width/4,screenDimensions.height/4,BufferedImage.TYPE_INT_ARGB);
			g2image = image.createGraphics();
		}

		public void paintComponent(Graphics g){
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(image,100,100,null);
			this.repaint();
		}
		public void praw(int side){
			int[] x = new int[side];
			int[] y = new int[side];
			for(int i=0; i<side; i++){
				x[i]=(int) (200*Math.random());
				y[i]=(int) (250*Math.random());
			}
		g2image.setStroke(new BasicStroke(3));
		g2image.setColor(new Color(0,0,0));
			Polygon po = new Polygon(x,y,side);
			g2image.drawPolygon(po);
		}
		public void clear(){
			g2image.setColor(Color.YELLOW);
			g2image.fillRect(0, 0, 600, 600);
		}
		
	}

}
