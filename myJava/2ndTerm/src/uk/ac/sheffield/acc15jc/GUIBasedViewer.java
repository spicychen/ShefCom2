package uk.ac.sheffield.acc15jc;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;

public class GUIBasedViewer extends JFrame{
	private static final int DEFAULT_HEIGHT = 600;
	private static final int DEFAULT_WIDTH = 800;

	public GUIBasedViewer(){
		setTitle("WeatherViewer");
		setBackground(Color.YELLOW);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		Container contentPane = this.getContentPane();
//		viewerPanel = new ViewerPanel();
//		contentPane.add(viewerPanel);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	private ViewerPanel viewerPanel;
	private Graphics2D g2image;

	class ViewerPanel extends JPanel{
		public ViewerPanel(){
		}
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(20));
			g2.setPaint(new Color(128,0,0));
			Rectangle2D.Double s =new Rectangle2D.Double(20.0, 20.0, 100.0, 50.0);
			g2.draw(s);
		}
	}
}
