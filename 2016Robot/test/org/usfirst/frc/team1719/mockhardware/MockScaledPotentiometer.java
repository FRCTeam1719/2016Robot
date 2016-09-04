package org.usfirst.frc.team1719.mockhardware;

import org.usfirst.frc.team1719.robot.sensors.ScaledPotentiometer;

import edu.wpi.first.wpilibj.AnalogInput;

public class MockScaledPotentiometer extends ScaledPotentiometer{

	public double raw;
	public double reading;
	
	public MockScaledPotentiometer(int channel) {
		super(new AnalogInput(channel));
		reading = 0;
		raw = 0;
	}
	
	public double getRaw(){
		return raw;
	}
	
	public double get(){
		return reading;
	}

}
