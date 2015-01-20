import lejos.hardware.Button;
import lejos.hardware.motor.NXTMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.EncoderMotor;
import lejos.utility.Delay;
public class test1 {
	static EncoderMotor motorA = new NXTMotor (MotorPort.A);
	static EncoderMotor motorC = new NXTMotor (MotorPort.C);
	public static void main(String[] args) {
		int[][] command = {
			      { 80, 60, 2},
			      { 60, 60, 1},
			      {-50, 80, 2}
			    };
		Reckoning_Position(command);		
	}
	public static void rectangle(){
		//Rectangle
		motorA.resetTachoCount();
		motorA.setPower(50);
		motorC.setPower(50);
		
		for(int i = 0; i < 4; i++){
			motorA.forward();
			motorC.forward();
			Delay.msDelay(750);
			motorA.stop();
			motorC.stop();
			motorA.forward();
			motorC.backward();
			Delay.msDelay(535);
			motorA.stop();
			motorC.stop();
			
		}
		
	
		motorA.resetTachoCount();
		Delay.msDelay(2000);
		
		Button.waitForAnyPress();
	}
	
	public static void line(){
		//Straight Line
			motorA.resetTachoCount();
			motorA.setPower(50);
			motorC.setPower(50);
			motorA.forward();
			motorC.forward();
			Delay.msDelay(2000);
			motorA.stop();
			motorC.stop();
				

			motorA.resetTachoCount();
				
			Delay.msDelay(2000);
			
			Button.waitForAnyPress();
	}
	
	public static void circle(){
		//Circle
		motorA.resetTachoCount();
		motorA.setPower(100);
		motorC.setPower(50);
		motorA.forward();
		motorC.forward();
		Delay.msDelay(4000);
		motorA.stop();
		motorC.stop();
		

		motorA.resetTachoCount();

		Delay.msDelay(2000);
		
		Button.waitForAnyPress();
	}
	
	public static void figure_eight(){
		//Figure 8
		motorA.setPower(100);
		motorC.setPower(0);
		motorA.resetTachoCount();
		motorA.forward();
		motorC.forward();
		Delay.msDelay(2000);
		motorA.setPower(0);
		motorC.setPower(100);
		Delay.msDelay(2250);
		motorC.stop();
		Delay.msDelay(2000);
		
		
		Button.waitForAnyPress();
	}
		
	public static void Array_Reader(int[][] command){
        //Reads the 3x3 input arrays Motor A, Motor C, Time in Seconds
        motorA.setPower(command[0][0]);
        motorC.setPower(command[0][1]);
        motorA.resetTachoCount();
        motorA.forward();
        motorC.forward();
        Delay.msDelay((command[0][2])*1000);
        
        motorA.setPower(command[1][0]);
        motorC.setPower(command[1][1]);
        motorA.resetTachoCount();
        motorA.forward();
        motorC.forward();
        Delay.msDelay((command[1][2])*1000);
        
        for (int i = 0; i<3; i++){
        	motorA.setPower(command[2][0]);
            motorC.setPower(command[2][1]);
            motorA.resetTachoCount();
            motorA.forward();
            motorC.forward();
            Delay.msDelay((command[2][2])*1000);	
        }
        
        Button.waitForAnyPress();
    }	

	public static void Reckoning_Position(int[][] command){

        //Display Position and Heading from (0,0)
		double tHeading = 0;
		double xPosition = 0;
		double yPosition = 0;
		double cRVel = 0;
		double cAngPos = 0;
        double wDiameter = 56;//in mm
        double TrackWidth = 148;// in mm
        double dPerTick = Math.PI*wDiameter/360;
        double TicksPerRotation = Math.PI*TrackWidth/dPerTick;
        double RadiansPerTick = 2*Math.PI/TicksPerRotation;
        double cDistance, cxDistance, cyDistance,
        cHeading, cAngAccel, lRVel, lAngPos;
        int prTicks, plTicks, lTicks, rTicks, i, j;
        int crTicks = 0;
        int clTicks = 0;
        int cTime = 10;// in ms
        
        motorA.resetTachoCount();
        
        for (i = 0; i<3; i++){
        	motorA.setPower(command[i][0]);
            motorC.setPower(command[i][1]);
            motorA.forward();
            motorC.forward();
            
            //delay loop
            j = 0;
            while(j<(command[i][2])*1000){
            	j += cTime;
                Delay.msDelay(cTime);
                prTicks = crTicks;
                plTicks = clTicks;
                clTicks = motorA.getTachoCount();
                crTicks = motorC.getTachoCount();
                
                rTicks = crTicks - prTicks;
                lTicks = clTicks - plTicks;
                cDistance = (rTicks+lTicks)/2*dPerTick;
                
                
                //counter-clockwise
                cHeading = (rTicks-lTicks)/2*RadiansPerTick;
                tHeading += cHeading;
                cxDistance = cDistance + Math.cos(tHeading);
                cyDistance = cDistance + Math.sin(tHeading);
                xPosition += cxDistance;
                yPosition += cyDistance;
                
                //maybe this was an over think?
                //lRVel = cRVel;
                //cRVel = cHeading/cTime;
                //cAngAccel = (cRVel - lRVel)/2;
                //lAngPos = cAngPos;
                //check my summing of angle logic
                //cAngPos = lAngPos+(lRVel*cTime)+(cTime*cTime*cAngAccel/2);
                
                
                
            }	
        }
        
		System.out.println("x: " + xPosition + "cm");
		System.out.println("y: " + yPosition + "cm");
        
        Button.waitForAnyPress();
        
        
        
	}
	
	
}