

import lejos.hardware.Button;

import lejos.hardware.motor.NXTMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.EncoderMotor;
import lejos.robotics.SampleProvider;
import lejos.hardware.sensor.EV3ColorSensor;

/*
 * Code solutions for questions:
 * -5
 * Convert your differential drive vehicle into a Braitenberg vehicle:
 * --coward:coward();
 * --aggression:aggression();
 * --love:love();
 * --explore:explore();
 * 
 * Using two light sensors that are connected to S2 and S4.
 */
public class lightbehavior {
	//Sensors 2 and 4 used for this class/java file
	static EV3ColorSensor sen1 = new EV3ColorSensor(SensorPort.S2);
	static EV3ColorSensor sen2 = new EV3ColorSensor(SensorPort.S4);
	//Motors A and C used for this class/java file
	static EncoderMotor motorA = new NXTMotor (MotorPort.A);
	static EncoderMotor motorC = new NXTMotor (MotorPort.C);

	public static void main(String[] args) {
		// uncomment if testing love function:
		//love();
		// uncomment if testing coward function:
		//coward();
		// uncomment if testing explore function:
		//explore();
		// default
		aggression();
	}
	/*
	 * love: the closer the light source is to you the slower you steer towards it.
	 * 
	 */
	public static void love(){
		System.out.println("LOVE");
		//inf loop
		while(true){

			//gets values from 0-100 from both light sensors
			float source1 = get_source(sen1);
			float source2 = get_source(sen2);


			if(source1 != 0.0f && source2 != 0.0f){
				//Mathematical function used to convert value from sensors to
				//power setting on the individual motors with small buffering
				double power1 = 70*Math.sin(((15*(source1/100))/Math.PI)  +.7);
				double power2 = 70*Math.sin(((15*(source2/100))/Math.PI)  +.7);

				//if source value on either motor is < 10 just setting the motors to zero
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
			//if either sensor senses zero brightness, keep ev3 stationary
			else{
				motorA.setPower(0);
				motorC.setPower(0);
				motorA.stop();
				motorC.stop();
			}
			//breaks loop and ends program if any button on EV3 is pressed
			if(Button.readButtons() != 0){
				break;
			}

		}

	}
	/*
	 * Explore: the closer the light source is to you the slower you steer away from it.
	 * similar function from love with the values of the motors reversed.
	 */
	public static void explore(){
		System.out.println("EXPLORE");
		//inf loop
		while(true){

			//gets values from 0-100 from both light sensors
			float source1 = get_source(sen1);
			float source2 = get_source(sen2);

			if(source1 != 0.0f && source2 != 0.0f){
				//Mathematical function used to convert value from sensors to
				//power setting on the individual motors with small buffering
				double power1 = 70*Math.sin(((15*(source2/100))/Math.PI)  +.7);
				double power2 = 70*Math.sin(((15*(source1/100))/Math.PI)  +.7);

				//if source value on either motor is < 10 just setting the motors to zero
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
			//if either sensor senses zero brightness, keep ev3 stationary
			else{
				motorA.setPower(0);
				motorC.setPower(0);
				motorA.stop();
				motorC.stop();
			}
			//breaks loop and ends program if any button on EV3 is pressed
			if(Button.readButtons() != 0){
				break;
			}

		}

	}
	/*
	 * aggression: the closer the light source is to you the faster you steer towards it.
	 * 
	 */
	public static void aggression(){
		System.out.println("AGGRESSION");
		//inf loop
		while(true){

			//gets values from 0-100 from both light sensors
			float source1 = get_source(sen1);
			float source2 = get_source(sen2);

			if(source1 != 0.0f && source2 != 0.0f){
				//set motors power to a realtive value as the brightness of the
				//individual sensors
				motorA.setPower((int)source2);
				motorC.setPower((int)source1);
				motorA.forward();
				motorC.forward();
			}
			//if either sensor senses zero brightness, keep ev3 stationary
			else{
				motorA.setPower(0);
				motorC.setPower(0);
				motorA.stop();
				motorC.stop();
			}
			//breaks loop and ends program if any button on EV3 is pressed
			if(Button.readButtons() != 0){
				break;
			}

		}
	}

	/*
	 * coward: the closer the light source is to you the faster you steer away from it.
	 * similar function from aggression with the values of the motors reversed.
	 */
	public static void coward(){
		System.out.println("COWARD");
		//inf loop
		while(true){

			//gets values from 0-100 from both light sensors
			float source1 = get_source(sen1);
			float source2 = get_source(sen2);

			if(source1 != 0.0f && source2 != 0.0f){
				//set motors power to a realtive value as the brightness of the
				//individual sensors
				motorA.setPower((int)source1);
				motorC.setPower((int)source2);
				motorA.forward();
				motorC.forward();
			}
			//if either sensor senses zero brightness, keep ev3 stationary
			else{
				motorA.setPower(0);
				motorC.setPower(0);
				motorA.stop();
				motorC.stop();
			}
			//breaks loop and ends program if any button on EV3 is pressed
			if(Button.readButtons() != 0){
				break;
			}

		}
	}
	/*
	 * get_source is a helper function that gives you a value 0-100 based of the value
	 * of the light sensor being used (sensor) by setting the light sensor into ambient mode
	 * 
	 */
	public static float get_source(EV3ColorSensor sensor){
		SampleProvider ambi = sensor.getAmbientMode();
		int sampleSize = ambi.sampleSize();
		float[] sample = new float[sampleSize];
		ambi.fetchSample(sample, 0);
		//takes the only entry from the float array
		float source = sample[0]*100;
		return source;
	}

}
