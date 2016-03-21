package org.usfirst.frc.team1719.robot.sensors;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class ScaledPotentiometer extends AnalogPotentiometer{

	AnalogInput channel;
	public ScaledPotentiometer(AnalogInput channel, double fullRange, double offset) {
		super(channel, fullRange, offset);
		this.channel = channel;
	}
	

	
	public double get(boolean reportRaw){
		if(reportRaw){
			return channel.getVoltage();
		}else{
			return super.get();
		}
	}

}
