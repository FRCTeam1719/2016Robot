package org.usfirst.frc.team1719.robot.subsystems;

import org.usfirst.frc.team1719.robot.RobotMap;
import org.usfirst.frc.team1719.robot.commands.UseArm;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Arm extends Subsystem {
	
	//Define limit switches
	DigitalInput lowerLimitSwitch = RobotMap.armLowerLimitSwitch;
	DigitalInput upperLimitSwitch = RobotMap.armUpperLimitSwitch;
	Spark motor = RobotMap.armController;
	
	AnalogPotentiometer pot = RobotMap.armPot;
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new UseArm());
		
	}
	
	public Arm() {
		motor.set(0);
	}
	
	public void move(double speed) {
		motor.set(speed);
		System.out.println("MOTOR SPEED: "+speed);
	}
	
	public double getPos() {
		return pot.get();
	}
	
	
}
