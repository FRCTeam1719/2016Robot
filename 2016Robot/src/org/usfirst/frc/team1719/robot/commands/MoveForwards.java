package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.RobotMap;
import org.usfirst.frc.team1719.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Move forward a specified distance
 * 
 * @author aaroneline
 *
 */
public class MoveForwards extends Command {

	final double HALF_SPEED = 0.5;
	/**
	 * distances measured in feet
	 * 
	 */
	private double desiredDist;
	private double currentDist = 0D;
	public double kP = 0.1;

	DriveSubsystem drive = Robot.drive;

	/**
	 * 
	 * @param distFeet
	 *            to move forward
	 */
	public MoveForwards(double distFeet) {
		requires(Robot.drive);
		this.desiredDist = distFeet;

	}

	@Override
	protected void end() {
		Robot.drive.operateDrive(0, 0);
	}

	@Override
	protected void execute() {

		// Don't let the robot run because we don't have a way to measure
		// distance yet
		// TODO: implement a way to measure distance
		currentDist++;
		//
		// currentAngle = gyro.getAngle();
		// System.out.println(currentAngle);
		// //If the robot is not turned
		// if (Math.abs(currentAngle) <= ANGLE_TOLERANCE) {
		// drive.operateDrive(0.5, 0.5);
		// }
		// //If the robot is turned counterclockwise
		// else if (currentAngle > 0) {
		// drive.operateDrive(0.5, 0.5 - (kP * Math.abs(currentAngle)) );
		// }
		// //If the robot is turned clockwise
		// else if (currentAngle < 0) {
		// drive.operateDrive(0.5 - (kP * currentAngle), 0.5);
		// }

		Robot.drive.driveStraight(HALF_SPEED); // drive towards heading 0

	}

	@Override
	protected void initialize() {
		currentDist = 0;
		if (desiredDist == 0.0D)
			desiredDist = SmartDashboard.getNumber("MoveDistParam");
		RobotMap.gyro.reset();
	}

	@Override
	protected void interrupted() {
		Robot.drive.operateDrive(0, 0);

	}

	@Override
	protected boolean isFinished() {
		return currentDist >= desiredDist;
	}

}
