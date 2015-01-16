import lejos.hardware.Button;
import lejos.hardware.motor.NXTMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.EncoderMotor;
import lejos.utility.Delay;
public class test1 {
	static EncoderMotor motorA = new NXTMotor (MotorPort.A);
	static EncoderMotor motorC = new NXTMotor (MotorPort.C);
	public static void main(String[] args) {
		figure_eight();		
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
		
			

}