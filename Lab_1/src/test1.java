import lejos.hardware.Button;
//import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.EncoderMotor;
//import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;
public class test1 {
	static EncoderMotor motorL = new NXTMotor (MotorPort.A);
	static EncoderMotor motorR = new NXTMotor (MotorPort.D);

    static double wDiameter = 57;//in mm
    static double TrackWidth = 121;// in mm
//	static DifferentialPilot pilot = new DifferentialPilot(wDiameter, TrackWidth, Motor.A, Motor.C, true);

	public static void main(String[] args) {
//		line();
//		line();
//		line();
//		circle();
//		rectangle();
//		rectangle();
//		rectangle();
//		figure_eight();
//		int[][] command = {
//			      { 80, 60, 2},
//			      { 60, 60, 1},
//			      {-50, 80, 2}
//			    };
//		Reckoning_Position(command);	
		int[][] command = {
			      { 0, 0, 1},
			      { 40, 30, 2},
			      { 0, 0, 1}
			    };
		Reckoning_Position(command);	
		//int[][] command2 = {
		//	      { 60, 70, 2},
		//	      { -60, -60, 1},
		//	      {-60, 0, 1}
		//	    };
		//Reckoning_Position(command2);		
	}
	public static void rectangle(){
		//Rectangle
		motorL.resetTachoCount();
		motorR.resetTachoCount();
		motorL.setPower(40);
		motorR.setPower(40);
		
		for(int i = 0; i < 4; i++){
			motorL.forward();
			motorR.forward();
			Delay.msDelay(900);
			motorL.stop();
			motorR.stop();
			Delay.msDelay(600);
			motorL.forward();
			motorR.backward();
			Delay.msDelay(900);
			motorL.stop();
			motorR.stop();
			
//			pilot.travel(40);
//			pilot.rotate(90);
			
		}
		

		motorL.resetTachoCount();
		motorR.resetTachoCount();
		Delay.msDelay(2000);
		
		Button.waitForAnyPress();
	}
	
	public static void line(){
		//Straight Line
			motorL.resetTachoCount();
			motorR.resetTachoCount();
			motorL.setPower(40);
			motorR.setPower(40);
			motorL.forward();
			motorR.forward();
			Delay.msDelay(2000);
			motorL.stop();
			motorR.stop();
				

			motorL.resetTachoCount();
			motorR.resetTachoCount();
				
			Delay.msDelay(2000);
			
			Button.waitForAnyPress();
	}
	
	public static void circle(){
		//Circle
		motorL.resetTachoCount();
		motorR.resetTachoCount();
		motorL.setPower(0);
		motorR.setPower(50);
		motorL.forward();
		motorR.forward();
		Delay.msDelay(5000);
		motorL.stop();
		motorR.stop();
		

		Button.waitForAnyPress();
	}
	
	public static void figure_eight(){
		//Figure 8
		motorL.setPower(100);
		motorR.setPower(0);
		motorL.resetTachoCount();
		motorR.resetTachoCount();
		motorL.forward();
		motorR.forward();
		Delay.msDelay(2000);
		motorL.setPower(0);
		motorR.setPower(100);
		Delay.msDelay(2250);
		motorR.stop();
		Delay.msDelay(2000);
		
		
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
