import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;
public class SimpleFrame extends JFrame{
		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		final Dimension screenDimensions = toolkit.getScreenSize();
		JPanel cb;
	public SimpleFrame() {
		setTitle("A Centred Frame");
		setSize(screenDimensions.width/2,screenDimensions.height/2);
		setLocation(new Point(screenDimensions.width/4,screenDimensions.height/4));
		
		
		
//		ImageIcon ko = new ImageIcon("Koala.jpg");
//		JLabel pic = new JLabel(ko);
//		pic.setPreferredSize(new Dimension(800,600));
//		JPanel cbPanel = new JPanel();
//		cbPanel.setLayout(new GridLayout(0,1));
		
//		cbPanel.add(new JCheckBox("are you happy?"));
//		cbPanel.add(new JCheckBox("ain't you happy?"));
		
		
		mp = new myPanel();
		Container con = getContentPane();
		con.add(mp, BorderLayout.CENTER);
		
		cb = new JPanel(new GridLayout(8,1));
		
		for(int i =3; i<10; i++){
			final int i2 = i;
					g2image.setColor(new Color(130,3,3));
			makeButton(cb, String.valueOf(i), new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					sides=i2;
					g2image.setStroke(new BasicStroke(3));
					g2image.drawPolygon(xPoints, yPoints, sides);
					repaint();
					g2image.setColor(new Color(0,3,230));
				}
			});
		}
		
		con.add(cb, BorderLayout.EAST);
		
//		con.setLayout(new GridLayout(0,1));
//		con.setLayout(new BorderLayout());
//		con.add(cbPanel, BorderLayout.WEST);
//		con.add(pic, BorderLayout.CENTER);
		
//		ButtonGroup group = new ButtonGroup();
//		JRadioButton r1 = new JRadioButton("1");
//		group.add(r1);
//
//		JRadioButton r2 = new JRadioButton("2");
//		group.add(r2);
//		JRadioButton r3 = new JRadioButton("3");
//		group.add(r3);
//		
//		con.add(r1);
//		con.add(r2);
//		con.add(r3);
		
		
//		JComboBox comboBox = new JComboBox();
//		comboBox.addItem("1");
//		comboBox.addItem("2");
//		comboBox.addItem("3");
//		comboBox.setEditable(true);
//		con.add(comboBox);
		
		
//		JMenuBar jm = new JMenuBar();
//		this.setJMenuBar(jm);
//		JMenu m = new JMenu("help");
//		jm.add(m);
//		JMenuItem mi = new JMenuItem("about");
//		m.add(mi);
		
		
//		JTextField jtf1 = new JTextField(20);
//		JTextField jtf2 = new JTextField(20);
//		JPasswordField jpwf = new JPasswordField(20);
//		jpwf.setText("go away!");
//		JFormattedTextField jftf = new JFormattedTextField();
//		jftf.setValue(new Double(10.3));
//		jtf2.setText("Type here");
//		con.add(jtf1);
//		con.add(jtf2);
//		con.add(jpwf);
//		con.add(jftf);
	}
		private void makeButton(JPanel p, String name, ActionListener target) {
			JButton b = new JButton(name);
			p.add(b);
			b.addActionListener(target);
		
	}
		private myPanel mp;
		private int sides=3;
		private int[] xPoints = {50,70,30,60};
		private int[] yPoints = {50,70,70,90};
		private BufferedImage image =null;
		private Graphics2D g2image;
		public static final int DEFAULT_WIDTH =300;
		public static final int DEFAULT_HEIGHT =200;
		class myPanel extends JPanel {
			public myPanel(){
				setBackground(new Color(0,3,230));
				image =new BufferedImage(screenDimensions.width/4,screenDimensions.height/4,BufferedImage.TYPE_INT_ARGB);
				g2image = image.createGraphics();
			}
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
//				g2.setColor(Color.black);
//				g2.setFont(new Font("Papyrus",Font.PLAIN,48));
//				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//				g2.drawString("tesss", 40, 80);
				g2.drawImage(image,200,200,null);
			}
			
		}
		public static void main(String[]args){
			JFrame frm = new SimpleFrame();
			frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frm.setVisible(true);
		}

}
