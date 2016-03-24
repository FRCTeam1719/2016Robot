package org.usfirst.frc.team1719.robot.customSensors;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Counter;

public class MB1220UltrasonicPWM {
	
	private AnalogInput ultrasonic;
	
	public MB1220UltrasonicPWM(int port) {
		ultrasonic = new AnalogInput(port);
	}
	
	public double getDistanceFeet() {
		double voltage = ultrasonic.getVoltage();
		double mv = voltage * 1000;
		double cm = mv / 4.9;
		double in = cm / 2.54;
		return in / 12;
	}
	

}
