import lejos.hardware.Button;
import lejos.hardware.motor.NXTMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.EncoderMotor;
import lejos.utility.Delay;
/*
 * Code solutions for questions:
 * -2j
 * Move the robot in a straight line. line();
 * Move the robot in a rectangle. rectangle();
 * Move the robot in a circle. circle();
 * Move the robot in a figure-eight. figure_eight();
 * -3
 * Implement a dead reckoning position controller for your robot Reckoning_Position(command);
 * 
 * 
 */
public class test1 {
	//Motors A and D used for this class/java file
	static EncoderMotor motorL = new NXTMotor (MotorPort.A);
	static EncoderMotor motorR = new NXTMotor (MotorPort.D);

	static double wDiameter = 57;//in mm
	static double TrackWidth = 121;// in mm


	public static void main(String[] args) {
		// uncomment if testing line function:
		//		line();
		// uncomment if testing circle function:
		//		circle();
		// uncomment if testing rectangle function:
		//		rectangle();
		// uncomment if testing figure eight function:
		//		figure_eight();
		//default is testing reckoning postion
		int[][] command = {
				{ 0, 0, 1},
				{ 40, 30, 2},
				{ 0, 0, 1}
		};
		Reckoning_Position(command);		
	}
	/*
	 * rectangle function:
	 * (1)sets power to 40 for both motors
	 * (2)moves both forward for 900 msecs (0.9 seconds)
	 * (3)stop and turns a 90 degree angle for 600 msecs
	 * (4)repeat (2-4) 4 times
	 * (5)waits For Any Button Press 
	 */
	public static void rectangle(){
		//Rectangle
		//(1)
		motorL.setPower(40);
		motorR.setPower(40);
		//(4)
		for(int i = 0; i < 4; i++){
			//(2)
			motorL.forward();
			motorR.forward();
			Delay.msDelay(900);
			//(3)
			motorL.stop();
			motorR.stop();
			Delay.msDelay(600);
			motorL.forward();
			motorR.backward();
			motorL.stop();
			motorR.stop();

		}
		//(5)
		Button.waitForAnyPress();
	}
	/*
	 * line function:
	 * (1)sets power to 40 for both motors
	 * (2)moves both forward for 2000 msecs (2 seconds)
	 * (3)stops both motors for 2 secs
	 * (4)waits For Any Button Press 
	 */
	public static void line(){
		//Straight Line
		//(1)
		motorL.setPower(40);
		motorR.setPower(40);
		//(2)
		motorL.forward();
		motorR.forward();
		Delay.msDelay(2000);
		//(3)
		motorL.stop();
		motorR.stop();
		Delay.msDelay(2000);
		//(4)
		Button.waitForAnyPress();
	}
	/*
	 * circle function:
	 * (1)sets power to 50 for the right motor
	 * (2)moves both forward for 5000 msecs (5 seconds)
	 * (3)stops both motors
	 * (4)waits For Any Button Press 
	 */
	public static void circle(){
		//Circle
		//(1)
		motorL.setPower(0);
		motorR.setPower(50);
		//(2)
		motorL.forward();
		motorR.forward();
		Delay.msDelay(5000);
		//(3)
		motorL.stop();
		motorR.stop();
		//(4)
		Button.waitForAnyPress();
	}
	/*
	 * figure eight function:
	 * (1)sets power to 100 for the left motor and right to zero
	 * (2)moves both forward for 2000 msecs (2 seconds)
	 * (3)sets power to 100 for the right motor and left to zero
	 * (4)moves both forward for 2250 msecs
	 * (5)waits For Any Button Press 
	 */
	public static void figure_eight(){
		//Figure 8
		//(1)
		motorL.setPower(100);
		motorR.setPower(0);
		//(2)
		motorL.forward();
		motorR.forward();
		Delay.msDelay(2000);
		//(3)
		motorL.setPower(0);
		motorR.setPower(100);
		//(4)
		Delay.msDelay(2250);
		
		motorR.stop();

		//(5)
		Button.waitForAnyPress();
	}


	public static void Reckoning_Position(int[][] command){
		/*
		 * Input:
		 * Takes in 3x3 array, three rows of:
		 * { L Motor, R motor, Time in Seconds}
		 * 
		 * Output on EV3 screen:
		 * Distance Traveled in mm for x and y
		 * Heading given in Degrees
		 * 
		 * Robot always starts along x-axis,
		 * on y = 0. For example, 
		 * forward command from start = positive x
		 * 
		 * Robot Headings are then given counter
		 * clockwise to the x-axis
		 * */


		//Display Position and Heading from (0,0)
		double tHeading = 0;
		double xPosition = 0;
		double yPosition = 0;
		double dPerTick = Math.PI*wDiameter/360;
		double TicksPerRotation = Math.PI*TrackWidth/dPerTick;
		double RadiansPerTick = 2*Math.PI/TicksPerRotation;
		double cDistance, cxDistance, cyDistance,
		cHeading;
		int prTicks, plTicks, lTicks, rTicks, i, j;
		int crTicks = 0;
		int clTicks = 0;
		int cTime = 10;// in ms

		motorL.resetTachoCount();
		motorR.resetTachoCount();

		//Keeps track of row from int[][] command
		for (i = 0; i<3; i++){
			//initialize motors
			motorL.setPower(command[i][0]);
			motorR.setPower(command[i][1]);
			motorL.forward();
			motorR.forward();

			//delay loop add 10ms until reaching seconds from command[i][2]
			j = 0;
			while(j<(command[i][2])*1000){
				j += cTime;
				Delay.msDelay(cTime);

				//updating previous and current motor tachos
				prTicks = crTicks;
				plTicks = clTicks;
				clTicks = motorL.getTachoCount();
				crTicks = motorR.getTachoCount();

				//change in tacho for this time period
				rTicks = crTicks - prTicks;
				lTicks = clTicks - plTicks;
				cDistance = (rTicks+lTicks)/2*dPerTick;

				//change in heading for this time period
				cHeading = (rTicks-lTicks)/2*RadiansPerTick;
				tHeading += cHeading;

				//change in x and y positions for this time period
				cxDistance = cDistance * Math.cos(tHeading);
				cyDistance = cDistance * Math.sin(tHeading);
				xPosition += cxDistance;
				yPosition += cyDistance;

				//Computes positive heading values > 360
				if(tHeading > 2*Math.PI){
					tHeading -= 2*Math.PI;
				}
				else if(tHeading < 0){
					tHeading += 2*Math.PI;
				}

			}	
		}
		//Ensures Robot stops at calculated position
		motorL.stop();
		motorR.stop();

		//printing output to EV3 screen
		System.out.println("x: " + (int)(xPosition) + "mm");
		System.out.println("y: " + (int)(yPosition) + "mm");
		System.out.println("Heading: " + (tHeading*(180/Math.PI)) + " deg");

		Button.waitForAnyPress();



	}


}
