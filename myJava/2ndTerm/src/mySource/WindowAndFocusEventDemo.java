package mySource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;
public class WindowAndFocusEventDemo extends JFrame{
	JLabel label;
	JButton jb;
	
	
	public WindowAndFocusEventDemo(){
		setSize(300,300);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		label = new JLabel("", JLabel.CENTER);
		jb = new JButton("Exit");
		jb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			dispose();
		}
		});
		getContentPane().add(label);
		
		this.addWindowListener(new WindowListener(){
			public void windowOpened(WindowEvent e){
				label.setText("Howdy");
			}

			@Override
			public void windowActivated(WindowEvent e) {}

			@Override
			public void windowClosed(WindowEvent e) {}

			@Override
			public void windowClosing(WindowEvent e) {
				label.setText("Don't leave me?");
			}

			@Override
			public void windowDeactivated(WindowEvent e) {}

			@Override
			public void windowDeiconified(WindowEvent e) {}

			@Override
			public void windowIconified(WindowEvent e) {
				label.setText("Ignore me, huh?");
				
			}

		});
		
		
		this.addFocusListener(new FocusListener(){
			public void focusLost(FocusEvent e){
				label.setText("Give me some attention!");
				System.out.println("10010");
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				label.setText("here you are");
			}

		});
		setTitle("Window and Focus Event Demo");
		setVisible(true);
	}
	
	
	public static void main(String[] args){
		new WindowAndFocusEventDemo();

	}
}
