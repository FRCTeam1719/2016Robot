package org.usfirst.frc.team1719.robot.commands;

import java.util.stream.DoubleStream;

import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Turns the drive to the specified angle
 */
public class TurnToAngle extends Command {
	// in degrees
	private static final double TOLERANCE = 5;
	private static final double MAX_PWR = 1.0D;

	double kP;
	double kD;
	double kI;

	private double desiredAngle;
	private double currentAngle = 0D;
	private double integral;
	double previousError;
	// In second
	private final double TIMEOUT_TIME = 5.0;
	private Timer timeout;
	double errors[] = new double[20];

	private boolean shouldResetGyro;

	AnalogGyro gyro = RobotMap.gyro;

	/**
	 * 
	 * Negative Angles move to the left, Positive to the Right
	 * 
	 * @param desiredAngle
	 *            to turn to
	 */

	public TurnToAngle(double desiredAngle, boolean shouldResetGyro) {
		// Use requires() here to declare subsystem dependencies

		requires(Robot.drive);

		this.desiredAngle = desiredAngle;
		this.shouldResetGyro = shouldResetGyro;
		timeout = new Timer();
		timeout.reset();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		integral = 0;
		kP = SmartDashboard.getNumber("Turn kP");
		kD = SmartDashboard.getNumber("Turn kD");
		kI = SmartDashboard.getNumber("Turn kI");
		if (desiredAngle == -1337)
			desiredAngle = SmartDashboard.getNumber("TurnToAngleParam");
		if (shouldResetGyro) {
			gyro.reset();
		}
		System.out.println("Turning command started");
		timeout.start();
		// Initialize our error array with the default error
		for (int i = 0; i < errors.length; i++) {
			errors[i] = desiredAngle-RobotMap.gyro.getAngle();
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		currentAngle = gyro.getAngle();
		double error = desiredAngle - currentAngle;
		for (int i = (errors.length - 1); i > 0; i--) {
			errors[i] = errors[i - 1];
		}
		errors[0] = error;

		double derivative = error - previousError;
		previousError = error;
		integral += error;
		if (error < TOLERANCE) {
			integral = 0;
		}
		double output = Math.max(Math.min((error * kP) + (derivative * kD) + (integral * kI), MAX_PWR), -MAX_PWR);
		System.out.println(output);
		Robot.drive.operateDrive(output, -output);
		System.out.println("Angle: " + RobotMap.gyro.getAngle());

		
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return ((Math.abs(DoubleStream.of(errors).sum() / 20)) < TOLERANCE || timeout.get() > TIMEOUT_TIME);
		// return (timeout.get() > TIMEOUT_TIME &&
		// !Robot.oi.getDontStopButton());
	}

	// Called once after isFinished returns true
	protected void end() {
		// RobotMap.gyro.reset();
		System.out.println("Ending");
		Robot.drive.operateDrive(0, 0);
		timeout.reset();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
