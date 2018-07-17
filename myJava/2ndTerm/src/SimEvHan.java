import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import uk.ac.sheffield.com1003.graphics.CentredFrame;
public class SimEvHan extends CentredFrame implements ActionListener{
	JButton button, button2;
	String[] labels = {"who take the cheese", "who ask me"};
	int currentLabel =0;
	
	public SimEvHan(){
		button = new JButton(labels[0]);
		button2 = new JButton(labels[1]);
		button.addActionListener(this);
		button2.addActionListener(this);
		setTitle("who did this");
		getContentPane().add(button,BorderLayout.NORTH);
		getContentPane().add(button2,BorderLayout.SOUTH);
		setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		button2.setText(labels[currentLabel]);
		currentLabel++;
		if(currentLabel >= labels.length){
			currentLabel =0;
			
		}
		button.setText(labels[currentLabel]);
		// TODO Auto-generated method stub
		
	}
	public static void main(String[]args){
		new SimEvHan();
	}

}
