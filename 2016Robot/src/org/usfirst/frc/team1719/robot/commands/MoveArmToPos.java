package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Move the arm to a specified angle
 */
public class MoveArmToPos extends Command {

	final double SPEED = 0.5;

	final boolean DIRECTION_UP = true;
	final boolean DIRECTION_DOWN = false;
	final double ERROR_TOLERANCE = 1;
	double currentAngle;
	double desiredAngle;
	double desiredPotPos; // the value the potentiometer is giving
	boolean direction;
	double speed;

	double kP;
	double kI;
	double kD;

	double integral = 0;
	double derivative = 0;

	double error;
	double previousError;

	double steadyConstant;
	double[] errors = new double[20];

	/**
	 * Move the arm to the desiredAngle
	 * 
	 * @param desiredAngle
	 *            double
	 */
	public MoveArmToPos(double desiredAngle) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.arm);

		this.desiredAngle = desiredAngle;

	}

	// Called just before this Command runs the first time
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.arm.setTargetPos(desiredAngle);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
