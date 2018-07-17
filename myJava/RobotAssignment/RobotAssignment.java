import ShefRobot.*;

/**
 *
 * @author Junjin Chen, Ping Zhou.
 */
public class RobotAssignment {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Create a robot object to use and connect to it
        Robot myRobot = new Robot();
       

        //Connect to the motors and the speaker.
        Motor leftMotor = myRobot.getLargeMotor(Motor.Port.B);
        Motor rightMotor = myRobot.getLargeMotor(Motor.Port.C);
        Speaker speaker = myRobot.getSpeaker();
        //connect to the color sensor and the touch sensor.
        ColorSensor colour = myRobot.getColorSensor(Sensor.Port.S3);
        TouchSensor touch;
        //By doing this, the value of the colour that is detected can be displayed.
        System.out.println(colour.getRed());
        double l, r;
        boolean nest = false;
        
        //at the begining the robot car will move randomly until the sensor detects the black.   
        while (colour.getRed() > 0.1){
            colour = myRobot.getColorSensor(Sensor.Port.S3);
            //creating an Touch object. 
            touch = myRobot.getTouchSensor(Sensor.Port.S4);
            l = 200*Math.random();
            r = 200*Math.random();
            leftMotor.setSpeed((int)l);
            rightMotor.setSpeed((int)r);
            leftMotor.forward();
            rightMotor.forward();
            myRobot.sleep(250);
            
           // when the robot car touch the wall, fistly the car will move backward and then turn around.
			if (touch.isTouched()){
				leftMotor.setSpeed(500);
				rightMotor.setSpeed(500);
				leftMotor.backward();
				rightMotor.backward();
				myRobot.sleep(1000);
				leftMotor.forward();
				rightMotor.backward();
				myRobot.sleep(500);
			}
        }
        
        int journey = 0;
        int blackArea = 0;
        //the situation of car that is not in the nest
        while (!nest){
            colour = myRobot.getColorSensor(Sensor.Port.S3);
            
            //the value of brightness of black is less than 0.1.           
            if (colour.getRed() < 0.1){
			    while (colour.getRed() < 0.1){
				    journey+=1;
				    
				//if the sensor detect the scent trail, the car will move forward for a while.
					if ( journey < 16 ){
						leftMotor.setSpeed(100);
						rightMotor.setSpeed(100);
						leftMotor.forward();
						rightMotor.forward();
						myRobot.sleep(250);
					}
				
				//after a while, the robot will detect whether the surroundings are black area or not.
					else{
						leftMotor.setSpeed(20);
						rightMotor.setSpeed(20);
						leftMotor.backward();
						rightMotor.forward();
						myRobot.sleep(500);
					
					//it will keep detecting until the Robot make sure that its position is on the nest, and the loop will end.
						if (colour.getRed() < 0.3)
							blackArea +=1;
						if (blackArea > 4){
							nest = true;
							break;
			            	} 
			        	}
			    }
			}
			//when the car run out of the scent trail , the car will turn around until it dectects the scent trail.
			  else {
					
				  //initiallise the times of its 'turns' action, and reset the journey.
					int turnTimes = 0;
					journey = 0;
					while (colour.getRed() >= 0.1){
						
						
						if ( turnTimes < 12 && blackArea == 0){
						leftMotor.setSpeed(20);
						rightMotor.setSpeed(20);
						leftMotor.backward();
						rightMotor.forward();
						myRobot.sleep(250);
						}
						
						// in the movement the sensor detect the scent trail, the car will turn around in a nother direction and move.
						else{
						leftMotor.setSpeed(40);
						rightMotor.setSpeed(40);
						leftMotor.forward();
						rightMotor.backward();
						myRobot.sleep(250);
						}
							
						turnTimes+=1;
						
					}
					blackArea = 0;
				}
			}
        
        //after the robot find the nest , the music will be played.
        speaker.playTone(932, 200);
        speaker.playTone(932, 200);
        speaker.playTone(932, 200);
        speaker.playTone(830, 200);
        speaker.playTone(932, 200);
        speaker.playTone(830, 200);
        speaker.playTone(932, 200);
        speaker.playTone(1109, 200);
        speaker.playTone(932, 200);
        speaker.playTone(830, 400);
        speaker.playTone(932, 500);
        speaker.playTone(830, 200);
        speaker.playTone(740, 200);
        speaker.playTone(932, 200);
        speaker.playTone(932, 500);
        speaker.playTone(740, 200);
        speaker.playTone(740, 500);
        speaker.playTone(932, 200);
        speaker.playTone(932, 500);
        speaker.playTone(698, 200);
        speaker.playTone(698, 500);

    }
    
}
