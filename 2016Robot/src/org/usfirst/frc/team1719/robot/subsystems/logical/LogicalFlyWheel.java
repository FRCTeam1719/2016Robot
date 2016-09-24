package org.usfirst.frc.team1719.robot.subsystems.logical;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * Flywheel subsystem Composed of: 1 Spark motor controller for spinning the
 * wheel An encoder for measuring wheel speed The subsystem is used as a
 * component of the larger shooter mechanism The Flywheel is required to be able
 * to spin and consistent speeds independent of battery voltage
 * 
 * @author aaroneline
 *
 */
public class LogicalFlyWheel implements IFlyWheel{

	SpeedController motor;


	final double ONEROTATION = 1.57075;
	final double TOLERANCE_FEET_PER_SECOND = 1;

	/**
	 * Takes hardware
	 * 
	 * @param controller
	 *            Spark
	 * @param enc
	 *            Encoder
	 * @param pidData
	 *            PIDData object
	 */
	public LogicalFlyWheel(SpeedController controller) {

		motor = controller;
	}


	/**
	 * Stop motors and reset encoders
	 */
	public void reset() {
		motor.set(0);
	}

	/**
	 * Spin wheel at desired feet per second Uses PID loop for consistency
	 * 
	 * @param feetPerSecond
	 *            double
	 */
	public void spin(double feetPerSecond) {
		motor.set(feetPerSecond);
	}

	/**
	 * Check if the FlyWheel has reached a stable speed
	 * 
	 * @param tolerance
	 *            Stabilization check tolerance
	 * @return Stabilization status
	 */
	public boolean isStabilized()
	{
		return true;
	}


	/**
	 * Get rate of the FlyWheel's encoder
	 * 
	 * @return encoder rate
	 */

}
