package org.usfirst.frc.team1719.robot.subsystems.logical;

public interface IFlyWheel {

	/**
	 * Stop subsystem
	 */
	void reset();
	
	void spin(double speed);
	
	boolean isStabilized();
}
