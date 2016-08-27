package org.usfirst.frc.team1719.robot.commands;

import java.util.stream.DoubleStream;

import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Turns the drive to the specified angle
 */
public class TurnToAngle extends Command implements PIDOutput {
	// in degrees
	private static final double TOLERANCE = 5;
	
	private double leftOutput;
	private double rightOutput;

	double kP;
	double kD;
	double kI;
	PIDController pidController;
	private double desiredAngle;
	private double currentAngle = 0D;
	double previousError;
	// In second
	private final double TIMEOUT_TIME = 5.0;
	private Timer timeout;
	double errors[] = new double[20];

	private boolean shouldResetGyro;

	//AnalogGyro gyro = RobotMap.gyro;

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
		RobotMap.navX.zeroYaw();
		kP = SmartDashboard.getNumber("Turn kP");
		kD = SmartDashboard.getNumber("Turn kD");
		kI = SmartDashboard.getNumber("Turn kI");
		if (desiredAngle == -1337)
			desiredAngle = SmartDashboard.getNumber("TurnToAngleParam");
		if (shouldResetGyro) {
			//gyro.reset();
		}
		System.out.println("Turning command started");
		timeout.start();
		// Initialize our error array with the default error
		for (int i = 0; i < errors.length; i++) {
			errors[i] = desiredAngle-RobotMap.navX.getAngle();
		}
		
		pidController = new PIDController(kP, kI, kD, RobotMap.navX, this);
		pidController.setInputRange(-180, 180);
		pidController.setOutputRange(-1, 1);
		pidController.setContinuous(true);
		pidController.reset();
		
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		currentAngle = RobotMap.navX.getAngle();
		double error = desiredAngle - currentAngle;
		for (int i = (errors.length - 1); i > 0; i--) {
			errors[i] = errors[i - 1];
		}
		errors[0] = error;

		pidController.setSetpoint(desiredAngle);
		pidController.enable();
		currentAngle = RobotMap.navX.getYaw();
		
		Robot.drive.operateDrive(leftOutput, rightOutput);
		
		System.out.println(RobotMap.navX.getAngle());

		
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

	@Override
	public void pidWrite(double output) {
		leftOutput = output;
		rightOutput = -output;
		
	}
}
