package org.usfirst.frc.team1719.robot.sensors;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.ControllerPower;

public class Ultrasonic {

	AnalogInput channel;
	public Ultrasonic(AnalogInput channel){
		this.channel = channel;
	}
	
	public double getDistanceCM(){
		return channel.getVoltage()/0.0049;
	}
	
	public double getDistanceFeet(){
		return getDistanceCM() / 30;
	}
	
	
}
