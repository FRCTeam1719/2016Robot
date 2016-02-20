package org.usfirst.frc.team1719.robot.subsystems;

import java.util.stream.DoubleStream;

import org.usfirst.frc.team1719.robot.settings.PIDData;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	Encoder encoder;

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
	public FlyWheel(Spark controller, Encoder enc, PIDData pidData) {

		this.pidData = pidData;
		motor = controller;
		encoder = enc;
		encoder.reset();
	}

	@Override
	protected void initDefaultCommand() {}

	/**
	 * Stop motors and reset encoders
	 */
	public void reset() {
		encoder.reset();
		output = 0;
		motor.set(output);
	}

	/**
	 * Spin wheel at desired feet per second Uses PID loop for consistency
	 * 
	 * @param feetPerSecond
	 *            double
	 */
	public void spin(double feetPerSecond) {
		double desiredSpeed = feetPerSecond;
		pidData.kP = SmartDashboard.getNumber("Right flywheel kP: ");
		pidData.kI = SmartDashboard.getNumber("Right flywheel kI: ");
		pidData.kD = SmartDashboard.getNumber("Right flywheel kD: ");

		if (desiredSpeed == 0) {
			motor.set(0);
			return;
		}
		double currentSpeed = encoder.getRate();
		double error = desiredSpeed - currentSpeed;
		integral += error;
		derivative = error - previousError;

		if (Math.abs(error) < TOLERANCE_FEET_PER_SECOND) {
			integral = 0;
		}

		output += (error * pidData.kP) + (integral * pidData.kI) + (derivative * pidData.kD);

		if (output > 1) {
			output = 1;
		} else if (output < -1) {
			output = -1;
		}
		motor.set(output);
		for (int i = (errors.length - 1); i > 0; i--) {                
		    errors[i] = errors[i-1];
		} 
		errors[0] = error;
		previousError = error;
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
	public double getRate() {
		return encoder.getRate();
	}

}
