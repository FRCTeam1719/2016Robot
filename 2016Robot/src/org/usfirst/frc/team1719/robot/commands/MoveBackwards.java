package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.RobotMap;
import org.usfirst.frc.team1719.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MoveBackwards extends Command {
	
	double HALF_SPEED = 0.5D;
	double speed;
	/** distances measured in feet
	 * 
	 */
	private double desiredDist;
	
	
	
	DriveSubsystem drive = Robot.drive;
	Gyro gyro = RobotMap.gyro;
	
	public MoveBackwards(double distFeet, double speed) {
		requires(Robot.drive);
		this.desiredDist = distFeet;
		this.speed = speed;
		
	}

	@Override
	protected void end() {		
	}

	@Override
	protected void execute() {
        Robot.drive.driveStraight(-speed); // drive towards heading 0
	}

	@Override
	protected void initialize() {
		Robot.drive.resetEncoders();
		gyro.reset();
		if(desiredDist == 0.0D) desiredDist = SmartDashboard.getNumber("MoveDistParam");
	}

	@Override
	protected void interrupted() {
		
	}

	@Override
	protected boolean isFinished() {
		return Robot.drive.getDistanceDriven() >= desiredDist;
	}

}
