import java.awt.*;
import javax.swing.*;
public class SimpleFrame2 extends JFrame{
	public SimpleFrame2(){
		setTitle("Drawing Frame");
		setSize(200,150);
		Container contentPane = this.getContentPane();
		myDrawing = new MyPanel();
		contentPane.add(myDrawing);
	}
	private MyPanel myDrawing;
	public static void main(String[]args){
		SimpleFrame2 a = new SimpleFrame2();
		a.setVisible(true);
	}
}
