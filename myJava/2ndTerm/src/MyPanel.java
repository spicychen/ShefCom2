import java.awt.*;

import javax.swing.*;
public class MyPanel extends JPanel{
	public MyPanel(){
		setBackground(Color.YELLOW);
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(4));
		g2.setPaint(Color.black);
		g2.setFont(new Font("Paprus", Font.PLAIN, 48));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		String text = "Text massage";
		g2.drawString(text, 40, 80);
//		Shape[] shapeList = new Shape[3];
//		shapeList[0] = new Rectangle2D.Double(20.0,20.0,40.0,70.0);
//		shapeList[1] = new Ellipse2D.Double(20.0, 20.0, 40.0, 70.0);
//		int[] x ={10,50,70};
//		int[] y = {20,70,20};
//		shapeList[2] = new Polygon(x,y,3);
//		for(int i=0; i<3; i++)
//			g2.draw(shapeList[i]);
		
	}

}
