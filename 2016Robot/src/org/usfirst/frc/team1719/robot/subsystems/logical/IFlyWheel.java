package org.usfirst.frc.team1719.robot.subsystems.logical;

public interface IFlyWheel extends LogicalSubsystem{

	/**
	 * Stop subsystem
	 */
	void reset();
	
	void spin(double speed);
	
	boolean isStabilized();
}
