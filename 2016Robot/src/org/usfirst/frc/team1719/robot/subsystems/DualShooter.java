package org.usfirst.frc.team1719.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;



public class DualShooter extends Subsystem {
	
	FlyWheel leftFlyWheel;
	FlyWheel rightFlyWheel;
	
	Spark leftHolderMotor;
	Spark rightHolderMotor;
	
	public DualShooter(FlyWheel leftFlyWheel, FlyWheel rightFlyWheel, Spark leftHolderMotor, Spark rightHolderMotor)
	{
		this.leftFlyWheel = leftFlyWheel;
		this.rightFlyWheel = rightFlyWheel;
		this.leftHolderMotor = leftHolderMotor;
		this.rightHolderMotor = rightHolderMotor;
	}
	
	public void spin(double leftPower, double rightPower)
	{
		leftFlyWheel.spin(leftPower);
		rightFlyWheel.spin(rightPower);
	}
	
	public void fire()
	{
		leftHolderMotor.set(1);
		rightHolderMotor.set(-1);
	}
	
	public void reset()
	{
		leftFlyWheel.reset();
		rightFlyWheel.reset();
		leftHolderMotor.set(0);
		rightHolderMotor.set(0);
	}
	
	public boolean isStabilized()
	{
		return leftFlyWheel.isStabilized(100) && rightFlyWheel.isStabilized(100);
	}
	
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
