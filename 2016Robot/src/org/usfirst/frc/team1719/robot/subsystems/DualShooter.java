package org.usfirst.frc.team1719.robot.subsystems;

import org.usfirst.frc.team1719.robot.subsystems.logical.IDualShooter;
import org.usfirst.frc.team1719.robot.subsystems.logical.IFlyWheel;
import org.usfirst.frc.team1719.robot.subsystems.logical.LogicalDualShooter;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 * Our shooter subsystem, composed of:
 * 		2 of our flywheel subsystems
 * 		2 inner wheels used for intake and launching of stored balls
 * @author aaroneline
 *
 */
public class DualShooter extends Subsystem implements IDualShooter{
	
	/**
	 * Give the flyWheels and inner wheels
	 * @param leftFlyWheel FlyWheel Subsystem on the left
	 * @param rightFlyWheel FlyWheel Subsystem on the right
	 * @param leftHolderMotor Inner motor on the left
	 * @param rightHolderMotor Inner motor on the right
	 */
	
	private LogicalDualShooter logic;
	
	public DualShooter(IFlyWheel leftFlyWheel, IFlyWheel rightFlyWheel, 
			SpeedController leftHolderMotor, SpeedController rightHolderMotor)
	{
		logic = new LogicalDualShooter(leftFlyWheel, rightFlyWheel, leftHolderMotor, rightHolderMotor);
	}

	@Override
	public void spin(spinMode speed) {
		logic.spin(speed);
	}

	@Override
	public void runInnerMotors(spinMode speed) {
		logic.runInnerMotors(speed);
	}

	@Override
	public void reset() {
		logic.reset();
	}

	@Override
	public boolean isStabilized() {
		return logic.isStabilized();
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

	
}