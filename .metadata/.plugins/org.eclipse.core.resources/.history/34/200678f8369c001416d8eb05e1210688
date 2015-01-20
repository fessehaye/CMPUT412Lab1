import lejos.hardware.Button;
import lejos.hardware.motor.NXTMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.EncoderMotor;
import lejos.utility.Delay;
public class test1 {

	public static void main(String[] args) {

		EncoderMotor motorA =new NXTMotor (MotorPort.A);
		
		motorA.resetTachoCount();
		System.out.println("Motor A tachometer:" + motorA.getTachoCount());
		motorA.setPower(50);
		motorA.forward();
		Delay.msDelay(2000);
		motorA.stop();
		System.out.println("Motor A t:" + motorA.getTachoCount());

		motorA.resetTachoCount();
		//System.out.println(motorA.getTachoCount()-motorA.getTachoCount());
		Delay.msDelay(2000);
		Button.waitForAnyPress();
		
	}

}