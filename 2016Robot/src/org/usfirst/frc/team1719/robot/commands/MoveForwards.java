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
public class MoveForwards extends Command {

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
	public MoveForwards(double distFeet, double speed) {
		requires(Robot.drive);
		this.desiredTime = distFeet;
		this.speed = speed;
		
		timer = new Timer();
	}

	@Override
	protected void end() {		
	}

	@Override
	protected void execute() {
		
        Robot.drive.operateDrive(speed, speed); // drive towards heading 0
	}

	@Override
	
	protected void initialize() {
		timer.reset();
		timer.start();
		
		
		RobotMap.gyro.reset();
		//Robot.drive.resetEncoders();
		if(desiredTime == 0.0D) desiredTime = SmartDashboard.getNumber("MoveDistParam");
	}

	@Override
	protected void interrupted() {
		Robot.drive.operateDrive(0, 0);

	}

	@Override
	protected boolean isFinished() {
		return timer.get() >= desiredTime;
	}

}
