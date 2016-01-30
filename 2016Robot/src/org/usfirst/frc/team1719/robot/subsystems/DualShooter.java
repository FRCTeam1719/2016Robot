package org.usfirst.frc.team1719.robot.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;



public class DualShooter extends Subsystem {
	
	FlyWheel leftFlyWheel;
	FlyWheel rightFlyWheel;
	
	Talon holderMotor;
	
	public DualShooter(FlyWheel leftFlyWheel, FlyWheel rightFlyWheel, Talon controller)
	{
		this.leftFlyWheel = leftFlyWheel;
		this.rightFlyWheel = rightFlyWheel;
		holderMotor = controller;
	}
	
	public void spin(double leftPower, double rightPower)
	{
		leftFlyWheel.spin(leftPower);
		rightFlyWheel.spin(rightPower);
	}
	
	public void fire()
	{
		holderMotor.set(1);
	}
	
	public void reset()
	{
		leftFlyWheel.reset();
		rightFlyWheel.reset();
		holderMotor.set(0);
	}
	
	public boolean isStabilized()
	{
		return leftFlyWheel.isStabilized(100) && rightFlyWheel.isStabilized(100);
	}
	
	protected void initDefaultCommand() {
		
		
	}
}
