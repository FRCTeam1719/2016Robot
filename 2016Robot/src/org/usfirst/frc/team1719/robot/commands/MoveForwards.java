package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.RobotMap;
import org.usfirst.frc.team1719.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class MoveForwards extends Command {
	
	private double ANGLE_TOLERANCE = 0.5D;
	
	/** distances measured in feet
	 * 
	 */
	private double desiredDist;
	private double currentDist = 0D;
	
	private double currentAngle = 0D;
	
	public double kP = (1 / 450);
	
	
	DriveSubsystem drive = Robot.drive;
	Gyro gyro = RobotMap.gyro;
	
	public MoveForwards(double distFeet) {
		requires(Robot.drive);
		this.desiredDist = distFeet;
		
		
	}

	@Override
	protected void end() {		
	}

	@Override
	protected void execute() {
		
		
		//Don't let the robot run because we don't have a way to measure distance yet
		//TODO: implement a way to measure distance
		currentDist = desiredDist + 1;
		
		currentAngle = gyro.getAngle();
		
		//If the robot is not turned
		if (Math.abs(currentAngle) <= ANGLE_TOLERANCE) {
			drive.operateDrive(0.5, 0.5);
		}
		//If the robot is turned counterclockwise
		if (currentAngle < 0) {
			drive.operateDrive(0.5, 0.5 - (kP * Math.abs(currentAngle)) );
		}
		//If the robot is turned clockwise
		else if (currentAngle > 0) {
			drive.operateDrive(0.5 - (kP * currentAngle), 0.5);
		}
		
		
	}

	@Override
	protected void initialize() {
		currentDist = 0;
		gyro.reset();
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished() {
		return currentDist >= desiredDist;
	}

}
