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
	
	FlyWheel flyWheel;
	
	Spark holderMotor;

	
	public final boolean EJECT = true;
	public final boolean INTAKE = false;
	
	public enum spinMode{
		EJECT,INTAKE,STOP
	}
	
	/**
	 * Give the flyWheels and inner wheels
	 * @param leftFlyWheel FlyWheel Subsystem on the left
	 * @param rightFlyWheel FlyWheel Subsystem on the right
	 * @param leftHolderMotor Inner motor on the left
	 * @param rightHolderMotor Inner motor on the right
	 */
	public DualShooter(FlyWheel flyWheel, Spark holderMotor)
	{
		this.flyWheel = flyWheel;
		
		this.holderMotor = holderMotor;
		}
	
	/**
	 * Spin up the Fly Wheels as specified powers
	 * @param leftPower speed
	 * @param rightPower speed
	 */
	public void spin(spinMode mode)
	{
		switch(mode){
		
		case INTAKE:
			flyWheel.spin(-.7);
			break;
			
		case EJECT:
			flyWheel.spin(1);
			break;
			
		case STOP:
			flyWheel.spin(0);	
			break;
		}
	}
	
	/**
	 * Spin the inner wheels so that they eject the ball
	 */
	public void runInnerMotors(spinMode mode)
	{
		
		switch(mode){
		
		case EJECT:
			holderMotor.set(1);
			break;
			
		case INTAKE:
			holderMotor.set(-1);
			break;
			
		case STOP:
			holderMotor.set(0);
			break;
		}
		
	}
	
	/**
	 * Stop all motors
	 */
	public void reset()
	{
		flyWheel.reset();
	
		holderMotor.set(0);
		
	}
	
	/**
	 * Check to see if both flywheels have reached stable speeds
	 * @return stabilization status
	 */
	public boolean isStabilized()
	{
		return flyWheel.isStabilized(100);
	}
	
	protected void initDefaultCommand() {
		
	}
}
