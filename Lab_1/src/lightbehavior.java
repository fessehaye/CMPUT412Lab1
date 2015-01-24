

import lejos.hardware.Button;

import lejos.hardware.motor.NXTMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.EncoderMotor;
import lejos.robotics.SampleProvider;
import lejos.hardware.sensor.EV3ColorSensor;

public class lightbehavior {
	static EV3ColorSensor sen1 = new EV3ColorSensor(SensorPort.S2);
	static EV3ColorSensor sen2 = new EV3ColorSensor(SensorPort.S4);
	static EncoderMotor motorA = new NXTMotor (MotorPort.A);
	static EncoderMotor motorC = new NXTMotor (MotorPort.C);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		explore();
	}

	public static void love(){
		System.out.println("LOVE");
		while(true){

			float source1 = get_source(sen1);
			float source2 = get_source(sen2);


			if(source1 != 0.0f && source2 != 0.0f){
				double power1 = 70*Math.sin(((15*(source1/100))/Math.PI)  +.7);
				double power2 = 70*Math.sin(((15*(source2/100))/Math.PI)  +.7);
				if(source1 <= 10){
					motorC.setPower(0);
				}
				else{
					motorC.setPower((int)power1);
				}
				if(source2 <= 10){
					motorA.setPower(0);
				}
				else{
					motorA.setPower((int)power2);
				}
				motorA.forward();
				motorC.forward();
			}
			else{
				motorA.setPower(0);
				motorC.setPower(0);
				motorA.stop();
				motorC.stop();
			}
			if(Button.readButtons() != 0){
				break;
			}

		}

	}

	public static void explore(){
		System.out.println("EXPLORE");
		while(true){

			float source1 = get_source(sen1);
			float source2 = get_source(sen2);

			//System.out.println("sample1:" + source1 );
			//System.out.println("sample2:" + source2 );

			if(source1 != 0.0f && source2 != 0.0f){
				double power1 = 70*Math.sin(((15*(source2/100))/Math.PI)  +.7);
				double power2 = 70*Math.sin(((15*(source1/100))/Math.PI)  +.7);
				if(source1 <= 10){
					motorA.setPower(0);
				}
				else{
					motorA.setPower((int)power1);
				}
				if(source2 <= 10){
					motorC.setPower(0);
				}
				else{
					motorC.setPower((int)power2);
				}
				motorA.forward();
				motorC.forward();
			}
			else{
				motorA.setPower(0);
				motorC.setPower(0);
				motorA.stop();
				motorC.stop();
			}
			if(Button.readButtons() != 0){
				break;
			}

		}

	}
	public static void aggression(){
		System.out.println("AGGRESSION");
		while(true){

			float source1 = get_source(sen1);
			float source2 = get_source(sen2);

			//System.out.println("sample1:" + source1 );
			//System.out.println("sample2:" + source2 );

			if(source1 != 0.0f && source2 != 0.0f){
				motorA.setPower((int)source2);
				motorC.setPower((int)source1);
				motorA.forward();
				motorC.forward();
			}
			else{
				motorA.setPower(0);
				motorC.setPower(0);
				motorA.stop();
				motorC.stop();
			}
			if(Button.readButtons() != 0){
				break;
			}

		}
	}
	public static void coward(){
		System.out.println("COWARD");
		while(true){

			float source1 = get_source(sen1);
			float source2 = get_source(sen2);

			//System.out.println("sample1:" + source1 );
			//System.out.println("sample2:" + source2 );

			if(source1 != 0.0f && source2 != 0.0f){
				motorA.setPower((int)source1);
				motorC.setPower((int)source2);
				motorA.forward();
				motorC.forward();
			}
			else{
				motorA.setPower(0);
				motorC.setPower(0);
				motorA.stop();
				motorC.stop();
			}
			if(Button.readButtons() != 0){
				break;
			}

		}
	}

	public static float get_source(EV3ColorSensor sensor){
		SampleProvider ambi = sensor.getAmbientMode();
		int sampleSize = ambi.sampleSize();
		float[] sample = new float[sampleSize];
		ambi.fetchSample(sample, 0);

		float source = sample[0]*100;
		return source;
	}

}
