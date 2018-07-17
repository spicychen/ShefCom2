import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;


public class Arty extends JFrame {
  
  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;
  private static final Dimension dimension = new Dimension(WIDTH, HEIGHT);
  
  private GLCanvas canvas;          // The canvas that will be drawn on.
  private GLEventListener glEventListener;
  
  private FPSAnimator animator; 
  private JButton start, stop, reset;
  private JPanel buttonPanel;
  
  public static void main(String[] args) {
    Arty f = new Arty("Arty");         // Create a subclass of JFrame.
    f.getContentPane().setPreferredSize(dimension);
                                    // setPreferredSize() is used for the content pane. When setPreferredSize is
                                    // used, must remember to pack() the JFrame after all elements have been added.
                                    // Note that the JFrame will be bigger, as the borders and title bar are added.
    f.pack();                       // Without pack(), the use of setPreferredSize() would 
                                    // result in a 0x0 canvas size, as nothing is yet drawn on it.
                                    // Alternative is to use f.setSize(dimension); rather than setPreferredSize and pack.
    f.setVisible(true);             // Finally, set the JFrame to be visible.
  }

  
  public Arty(String textForTitleBar) {
    super(textForTitleBar);        // Call the superclass constructure to set the text for the title bar.
                                   
    GLCapabilities glcapabilities = new GLCapabilities(GLProfile.get(GLProfile.GL3));
                                    // The desktop OpenGL core profile 3.x, with x >= 1
                                    
    canvas = new GLCanvas(glcapabilities);
                                    // Create the canvas for drawing on.
	glEventListener = new Arty_GLEventListener();						
    canvas.addGLEventListener(glEventListener);
                                    // Add the GLEventListener to the canvas.
                                    // A separate class is used to implement the GLEventListener interface,
                                    // which is where all the OpenGL action happens.
    getContentPane().add(canvas, BorderLayout.CENTER);
    
    start=new JButton("Start");
    stop=new JButton("Stop");
    reset=new JButton("Reset");
    
    start.addActionListener(new ActionListener() {
    		
    		public void actionPerformed(ActionEvent e) {
    			((Arty_GLEventListener) glEventListener).sayHi();
    			//System.out.println(canvas.getGLEventListenerCount());
    			animator.start();
    			//animator.resume();
    		}
    });
    
    stop.addActionListener(new ActionListener() {
    		
    		public void actionPerformed(ActionEvent e) {
    			animator.stop();
    		}
    });
    
    GridLayout l = new GridLayout(0,3);
    
    buttonPanel = new JPanel(l);
    buttonPanel.add(start);
    buttonPanel.add(stop);
    buttonPanel.add(reset);
    
    getContentPane().add(buttonPanel, BorderLayout.PAGE_START);
    
                                    // Because a JFrame is used, the child (i.e. the canvas) must be added to the
                                    // content pane. The canvas is added to the centre, using the BorderLayout manager.
                                    // In later examples, we will add buttons near to the borders of the JFrame.
    addWindowListener(new WindowAdapter() {
                                    // Use an anonymous inner class to handle the window closing event.
      public void windowClosing(WindowEvent e) {
        animator.stop();            // Clean up before exiting.
        remove(canvas);
        dispose();                  // equivalent to this.dispose(); - disposes of the JFrame
        System.exit(0);
      }
    });
    animator = new FPSAnimator(canvas, 60);
                                    // Using an FPSAnimator, the aim is to redraw the canvas 60 times a second.
                                    // However, this will depend on how long it takes to draw the canvas.
    //animator.start();               // Start the FPSAnimator.
  }
  
}