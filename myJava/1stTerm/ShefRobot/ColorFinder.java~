import ShefRobot.*;

public class ColorFinder {

    // These are variables that can be used by any of the methods in this class
    // This is not beautiful, Object-Oriented programming style, but we wanted
    // to keep this exercise simple ;)
    private static Robot rob;
    private static Motor left;
    private static Motor right;
    private static Speaker speaker;
    private static ColorSensor sen;
    private static ColorSensor.Color col;			

    // This is the "main" method - this is where the program starts
    public static void main(String[] args) {

	// First, we need to get the Robot object
	rob = new Robot();
	// Then we get our left and right Motor objects and our Speaker object
	left = rob.getLargeMotor(Motor.Port.A);
	right = rob.getLargeMotor(Motor.Port.B);
	speaker = rob.getSpeaker();

	// You can try different speeds for the motors,
	// but if you run them too fast the code that counts
	// the steps won't keep up and you will get less accurate movements.
	left.setSpeed(30);
	right.setSpeed(30);
	
	// Get a colour sensor (note: we are using the US spelling of color in the code...)
	sen = rob.getColorSensor(Sensor.Port.S1);

	// Scan to the left for 20 "steps"
	scan(false,20);
	// Scan to the right for 20 steps 
	// (remember that we will have finished 20 steps to the left, 
	// so this is actually covering 20 steps right from where we started)
	scan(true,40);
	System.out.println(col);
	// The scan method will stop if it finds red
	// The col variable is set to the last colour scanned
	if(col == ColorSensor.Color.BLACK) {
	    // We found red, so play a happy sound
	    speaker.playTone(220,300);
	    speaker.playTone(523,200);
	} 
	else if(col == ColorSensor.Color.RED) {
		speaker.playTone(566,300);
	    speaker.playTone(400,200);
	    forward(1);
	} 
	else {
	    // We didn't, so play a less happy sound...
	    speaker.playTone(493,200);
	    speaker.playTone(369,200);	       
	}

	// Close the robot and clean up all the connections to ports.
	rob.close();
    }

    // This method moves the robot forward by count "steps"
    // Technically, the steps are degrees of rotation of the motors
    // but how this translates to actual distances moved will depend
    // on various factors...
    protected static void forward(int count) {
	left.resetTachoCount();
	right.resetTachoCount();
	left.forward();
	right.forward();
	waitfor(count,false,false);
    }
	
    // This turns the robot to the right by movinf the left
    // motor forward and the right motor backwards.
    // The count value is the number of degrees of rotation 
    // *of the motors*. How this relates to the rotation of the 
    // robot will depend on the size of the wheels, how far
    // apart they are, and various other factors.
    protected static void right(int count) {
	left.resetTachoCount();
	right.resetTachoCount();
	left.forward();
	right.backward();
	waitfor(count,false,true);
    }
    
    // This rotates the robot the other way...
    protected static void left(int count) {
	left.resetTachoCount();
	right.resetTachoCount();
	left.backward();
	right.forward();
	waitfor(count,true,false);
    }
        
    // This is a simple scanning method. It will
    // rotate the robot a short distance either 
    // to the right or "not right" (a.k.a. left!)
    // and check the currently sensed colour.
    // It looks for red and stops if it finds it.
    // It will repeat this a maximum of 'count'
    // times.
    // This method updates the col variable, so 
    // that will be set to the last colour scanned
    // when the method completes.
    private static void scan(boolean right,int count) {
	System.out.println("Scanning " + (right ? "right" : "left") + " for " + count);
	for(int i = 0; i < count; i++) {
	    if(right) {
		right(10);
	    } else {
		left(10);
	    }
	    col = sen.getColor();			
	    if(col == ColorSensor.Color.BLACK || col == ColorSensor.Color.RED) {
		break;
	    }
	}
    }

    // This is a somewhat complicated method for monitoring the motors and 
    // stopping them once they have rotated far enough.
    private static void waitfor(int count, boolean leftback, boolean rightback) {
	int ltc = left.getTachoCount();
	int rtc = right.getTachoCount();
	boolean lf = false; 
	boolean rf = true;
	if(leftback && (ltc < (0-count))) {
	    left.stop();
	    lf = true;
	} else if(ltc > count) {
	    left.stop();
	    lf = true;
	}
	if(rightback && (rtc < (0-count))) {
	    right.stop();
	    rf = true;
	} else if(rtc > count) {
	    right.stop();
	    rf = true;
	}
	if(!(lf && rf)) {
	    waitfor(count,leftback,rightback);	
	}
    }
	
}



