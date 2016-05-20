package org.usfirst.frc.team1719.robot.sensors;

import edu.wpi.first.wpilibj.AnalogInput;


public class AnalogUltrasonic {

	AnalogInput channel;
	public AnalogUltrasonic(AnalogInput channel){
		this.channel = channel;
	}
	
	public double getDistanceCM(){
		return channel.getVoltage()/0.0049;
	}
	
	public double getDistanceFeet(){
		return getDistanceCM() / 30;
	}
	
	
}
