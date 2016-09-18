package org.usfirst.frc.team1719.mockhardware;

import org.usfirst.frc.team1719.robot.sensors.IScaledPotentiometer;

public class MockScaledPotentiometer implements IScaledPotentiometer {

	public double raw;
	public double reading;
	
	public MockScaledPotentiometer() {
		reading = 0;
		raw = 0;
	}
	
	public double getRaw(){
		return raw;
	}
	
	public double get(){
		return reading;
	}
	
	public void setReading(double newReading) {
		reading = newReading;
	}

}
