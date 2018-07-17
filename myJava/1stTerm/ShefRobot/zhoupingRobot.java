import ShefRobot.*;

/**
 *
 * @author sdn
 */
public class RobotSample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Create a robot object to use and connect to it
        Robot myRobot = new Robot();
       
        //The robot is made of components which are themselves objects.
        //Create references to them as useful shortcuts
        Motor leftMotor = myRobot.getLargeMotor(Motor.Port.B);
        Motor rightMotor = myRobot.getLargeMotor(Motor.Port.C);
        Speaker speaker = myRobot.getSpeaker();
        //use touch sensor create an object to represent it and this depends on the port that sensor is attached to.
        ColorSensor colour = myRobot.getColorSensor(Sensor.Port.S3);
        //By doing this, the value of the colour that is detected can be printed.
        System.out.println(colour.getRed());
        double l, r;
        boolean nest = false;
        //when the sensor does not detect the black colour value ,the robot car will move randomly
        while (colour.getRed() > 0.1){
        colour = myRobot.getColorSensor(Sensor.Port.S3);
        l = 200*Math.random();
        r = 200*Math.random();
        leftMotor.setSpeed((int)l);
        rightMotor.setSpeed((int)r);
        leftMotor.forward();
        rightMotor.forward();
        myRobot.sleep(250);
        }
        int i = 0;
        int black = 0;
        while (!nest){
        colour = myRobot.getColorSensor(Sensor.Port.S3);
        //the value of black is less than 0.1.
        	if (colour.getRed() < 0.1){
			while (colour.getRed() < 0.1){
				i+=1;
				//if the sensor detect the black value, the car will move forward.
				if ( i < 16 ){
					leftMotor.setSpeed(200);
					rightMotor.setSpeed(200);
					leftMotor.forward();
					rightMotor.forward();
					myRobot.sleep(250);
				}
				//if the sensor does not detect the black value, the car will turn around slowly until detect the value of blackblack 
				else{
					leftMotor.setSpeed(200);
					rightMotor.setSpeed(200);
					leftMotor.backward();
					rightMotor.forward();
					myRobot.sleep(500);
						if (colour.getRed() < 0.3)
							black +=1;
			if (black > 4){
				nest = true;
				break;
			}
				}
			}
			}
			else {
				int j = 0;
				i = 0;
				while (colour.getRed() >= 0.1){
					//if (i%2==0){
					if ( j < 12 && black == 0){
					leftMotor.setSpeed(2500);
					rightMotor.setSpeed(1200);
					leftMotor.backward();
					rightMotor.forward();
					myRobot.sleep(250);
					}
					else{
					leftMotor.setSpeed(2500);
					rightMotor.setSpeed(1200);
					leftMotor.forward();
					rightMotor.backward();
					myRobot.sleep(250);
					}
						
					j+=1;
					
				}
				black = 0;
			}
        }
        /*Go Forwards
        leftMotor.setSpeed(150);
        rightMotor.setSpeed(150);
        leftMotor.forward();
        rightMotor.forward();

        //Keep going for 5 seconds
        myRobot.sleep(5000);

        //Stop
        leftMotor.stop();
        rightMotor.stop();

        //Beep at 1000Hz for half a second
        */
        //sounds
        speaker.playTone(50, 400);
        speaker.playTone(200, 400);
        speaker.playTone(300, 400);
        speaker.playTone(400, 400);
        speaker.playTone(500, 400);
        speaker.playTone(600, 400);
        speaker.playTone(700, 400);
        speaker.playTone(700, 300);
        speaker.playTone(600, 300);
        speaker.playTone(500, 300);
        speaker.playTone(400, 300);
        speaker.playTone(300, 300);
        speaker.playTone(200, 300);
        speaker.playTone(50, 300);
        speaker.playTone(500, 200);
        speaker.playTone(600, 200);
        speaker.playTone(700, 200);
        speaker.playTone(700, 200);
        speaker.playTone(600, 200);
        speaker.playTone(500, 200);
        speaker.playTone(500, 200);
        speaker.playTone(600, 200);
        speaker.playTone(700, 200);
        speaker.playTone(700, 200);
        speaker.playTone(600, 200);
        speaker.playTone(500, 200);
        
        
        
        /*
        speaker.playTone(0, 1000);
        speaker.playTone(50, 1000);
        speaker.playTone(200, 1000);
        speaker.playTone(300, 1000);
        speaker.playTone(400, 1000);
        speaker.playTone(500, 1000);
        speaker.playTone(600, 1000);
        speaker.playTone(700, 1000);
        */
        
/*
        //Go Backwards
        leftMotor.backward();
        rightMotor.backward();

        //Keep going for 5 seconds
        myRobot.sleep(5000);

        //Stop
        leftMotor.stop();
        rightMotor.stop();
        
        //Disconnect from the Robot
        myRobot.close();
*/
    }

}

