package uk.ac.sheffield.acc15jc;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.ac.sheffield.acc15jc.SimpleDataArranger.Data;
import uk.ac.sheffield.com1003.EasyReader;

public class FrameViewer extends JFrame implements ActionListener{
	final Toolkit toolkit = Toolkit.getDefaultToolkit();
	final Dimension screenDimensions = toolkit.getScreenSize();
	private MyPanel mp;
	private Container contentPane;
	private SimpleDataArranger sdv;
	private boolean drawTem = false;
	public FrameViewer(SimpleDataArranger sdv){
		
		setTitle("Weather Viewer 3.0");
		setSize(800,600);
		setLocation(new Point(screenDimensions.width/4,screenDimensions.height/4));
		
		mp = new MyPanel();
		this.sdv = sdv;
		JPanel btns = new JPanel(new GridLayout(0,1));
		contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(btns, BorderLayout.WEST);
		contentPane.add(mp, BorderLayout.CENTER);
		String[] title = sdv.getTitle();
		for(int i=0; i<title.length;i++)
			makeButton(btns, title[i], this);
		setVisible(true);
	}
	private void makeButton(JPanel p, String name, ActionListener target) {
		JButton b = new JButton(name);
		p.add(b);
		b.addActionListener(target);
	}
	public void actionPerformed(ActionEvent e) {
		drawTem = true;
		
	}
	private class MyPanel extends JPanel{
		public MyPanel(){
			JLabel bcg = new JLabel(new ImageIcon("Tulips.jpg"));
			
		}
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			if(drawTem)
				paint(g2);
			repaint();
		}
		public void paint(Graphics2D g2){
			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.BLUE);
			g2.drawLine(100, 100, 100, 400);
			g2.drawLine(100, 400, 600, 400);
			Data tem = sdv.getData()[1];
			tem.convert();
			int preX=100, preY=400, currentX, currentY;
			for(int i=0; i< sdv.getLength(); i++){
				currentX=100+9*i;	currentY=(int) (100+(double)(tem.getDigits().get(i)));
				g2.drawLine(preX, preY, currentX, currentY);
				preX=currentX;	preY=currentY;
			}
			
		}
	}

}
