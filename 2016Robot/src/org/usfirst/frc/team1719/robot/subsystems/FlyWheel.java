package org.usfirst.frc.team1719.robot.subsystems;

import java.util.stream.DoubleStream;

import org.usfirst.frc.team1719.robot.settings.PIDData;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Flywheel subsystem Composed of: 1 Spark motor controller for spinning the
 * wheel An encoder for measuring wheel speed The subsystem is used as a
 * component of the larger shooter mechanism The Flywheel is required to be able
 * to spin and consistent speeds independent of battery voltage
 * 
 * @author aaroneline
 *
 */
public class FlyWheel extends Subsystem {

	Spark motor;

	double errors[] = new double[30];

	PIDData pidData;
	double previousError = 0;
	double output = 0;

	double integral = 0;
	double derivative = 0;

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
	public FlyWheel(Spark controller, PIDData pidData) {

		this.pidData = pidData;
		motor = controller;
	}

	@Override
	protected void initDefaultCommand() {}

	/**
	 * Stop motors and reset encoders
	 */
	public void reset() {
		output = 0;
		motor.set(output);
	}

	/**
	 * Spin wheel at desired feet per second Uses PID loop for consistency
	 * 
	 * @param feetPerSecond
	 *            double
	 */
	public void spin(double speed) {
		motor.set(speed);
		//System.out.println("Output: " + output);
		//System.out.println("Error: " + error);
		//System.out.println("kP: "+ pidData.kP);
	}

	/**
	 * Check if the FlyWheel has reached a stable speed
	 * 
	 * @param tolerance
	 *            Stabilization check tolerance
	 * @return Stabilization status
	 */
	public boolean isStabilized(double tolerance)
	{
		return (DoubleStream.of(errors).sum()/30)<tolerance;
	}

	/**
	 * Get rate of the FlyWheel's encoder
	 * 
	 * @return encoder rate
	 */
	

}
