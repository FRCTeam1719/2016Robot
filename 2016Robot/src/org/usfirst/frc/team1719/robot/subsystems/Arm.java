package org.usfirst.frc.team1719.robot.subsystems;

import org.usfirst.frc.team1719.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Arm extends Subsystem {
	
	//Define limit switches
	DigitalInput lowerLimitSwitch = RobotMap.armLowerLimitSwitch;
	DigitalInput upperLimitSwitch = RobotMap.armUpperLimitSwitch;
	
	Talon motor = RobotMap.armController;
	
	AnalogPotentiometer pot = RobotMap.armPot;
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	public Arm() {
		motor.set(0);
	}
	
	public void move(double speed) {
		//set speed to stop if speed exceeds the maximum the motors can output
		
		if ( speed < -1 || speed > 1) {
			motor.set(0);
			return;
		}
		//stop if limit switches are hit
		if (speed < 0) {
			if ((lowerLimitSwitch.get())) {
				motor.set(0);
				return;
			}
		}
		if (speed > 0) {
			if ((upperLimitSwitch.get())) {
				motor.set(0);
				return;
			}
		}
		
		motor.set(speed);
	}
	
	public double getPos() {
		return pot.get();
	}
}
