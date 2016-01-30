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
	private double currentDist = 0D;
	
	
	
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
		
		
		int rightEncoderVal = RobotMap.rightDriveWheelEncoder.get();
		int leftEncoderVal = RobotMap.leftDriveWheelEncoder.get();
		
		currentDist = -((rightEncoderVal + leftEncoderVal) / 2);
        
        Robot.drive.driveStraight(-speed); // drive towards heading 0
		
	}

	@Override
	protected void initialize() {
		currentDist = 0;
		gyro.reset();
		if(desiredDist == 0.0D) desiredDist = SmartDashboard.getNumber("MoveDistParam");
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
