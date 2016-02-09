package org.usfirst.frc.team1719.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 * Our shooter subsystem, composed of:
 * 		2 of our flywheel subsystems
 * 		2 inner wheels used for intake and launching of stored balls
 * @author aaroneline
 *
 */
public class DualShooter extends Subsystem {
	
	FlyWheel leftFlyWheel;
	FlyWheel rightFlyWheel;
	
	Spark leftHolderMotor;
	Spark rightHolderMotor;
	
	public final boolean EJECT = true;
	public final boolean INTAKE = false;
	
	/**
	 * Give the flyWheels and inner wheels
	 * @param leftFlyWheel FlyWheel Subsystem on the left
	 * @param rightFlyWheel FlyWheel Subsystem on the right
	 * @param leftHolderMotor Inner motor on the left
	 * @param rightHolderMotor Inner motor on the right
	 */
	public DualShooter(FlyWheel leftFlyWheel, FlyWheel rightFlyWheel, Spark leftHolderMotor, Spark rightHolderMotor)
	{
		this.leftFlyWheel = leftFlyWheel;
		this.rightFlyWheel = rightFlyWheel;
		this.leftHolderMotor = leftHolderMotor;
		this.rightHolderMotor = rightHolderMotor;
	}
	
	/**
	 * Spin up the Fly Wheels as specified powers
	 * @param leftPower speed
	 * @param rightPower speed
	 */
	public void spin(double leftPower, double rightPower)
	{
		leftFlyWheel.spin(leftPower);
		rightFlyWheel.spin(rightPower);
	}
	
	/**
	 * Spin the inner wheels so that they eject the ball
	 */
	public void runInnerMotors(boolean direction)
	{
		if(direction==EJECT){
			leftHolderMotor.set(1);
			rightHolderMotor.set(-1);
		}else if(direction==INTAKE){
			leftHolderMotor.set(-1);
			rightHolderMotor.set(1);
		}
	}
	
	/**
	 * Stop all motors
	 */
	public void reset()
	{
		leftFlyWheel.reset();
		rightFlyWheel.reset();
		leftHolderMotor.set(0);
		rightHolderMotor.set(0);
	}
	
	/**
	 * Check to see if both flywheels have reached stable speeds
	 * @return stabilization status
	 */
	public boolean isStabilized()
	{
		return leftFlyWheel.isStabilized(100) && rightFlyWheel.isStabilized(100);
	}
	
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
