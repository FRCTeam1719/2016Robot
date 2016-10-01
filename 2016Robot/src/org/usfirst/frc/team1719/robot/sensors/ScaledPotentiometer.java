package org.usfirst.frc.team1719.robot.sensors;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.ControllerPower;

public class ScaledPotentiometer extends AnalogPotentiometer{

	AnalogInput channel;
	public ScaledPotentiometer(AnalogInput channel, double fullRange, double offset) {
		super(channel, fullRange, offset);
		this.channel = channel;
	}
	
	public ScaledPotentiometer(AnalogInput channel, double scale){
		super(channel, scale);
		this.channel = channel;
	}
	
	public ScaledPotentiometer(AnalogInput channel){
		super(channel);
		this.channel = channel;
	}

	
	
	public double getRaw(){
		return channel.getVoltage() / ControllerPower.getVoltage5V();
	}

}
