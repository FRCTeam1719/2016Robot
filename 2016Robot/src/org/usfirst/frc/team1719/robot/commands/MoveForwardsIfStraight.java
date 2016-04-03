package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Move forward a specified distance
 * 
 * @author aaroneline
 *
 */
public class MoveForwardsIfStraight extends Command {

	double HALF_SPEED = 0.5D;
	double speed;
	
	//TODO: Fix encoders and use them instead, going by time is ugly
	Timer timer;

	/** distances measured in feet
	 * 
	 */
	private double desiredTime;
	
	/**
	 * 
	 * @param distFeet
	 *            to move forward
	 */
	public MoveForwardsIfStraight(double distFeet, double speed) {
		requires(Robot.drive);
		this.desiredTime = distFeet;
		this.speed = speed;
		
		timer = new Timer();
	}

	@Override
	protected void end() {	
		//STop the drive
		Robot.drive.operateDrive(0, 0);
		System.out.println("Ended");
	}

	@Override
	protected void execute() {
		
		if (RobotMap.gyro.getAngle() >= 5) {
			Robot.drive.operateDrive(-speed * 0.7, -speed * 0.7);
		}
        Robot.drive.operateDrive(-speed, -speed); // drive towards heading 0
	}

	@Override
	
	protected void initialize() {
		timer.reset();
		timer.start();
		
		
		
		System.out.println("MoveForwardStarted");
		//Robot.drive.resetEncoders();
		if(desiredTime == 0.0D) desiredTime = SmartDashboard.getNumber("MoveDistParam");
	}

	@Override
	protected void interrupted() {
		Robot.drive.operateDrive(0, 0);

	}

	@Override
	protected boolean isFinished() {
		if (Math.abs(RobotMap.gyro.getAngle()) > 10) {
			return true;
		}
		System.out.println(timer.get());
		return timer.get() >= desiredTime;
	}

}
