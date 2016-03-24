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
public class MoveForwardsRockWall extends Command {

	final int STAGE_START = 0;
	final int STAGE_POINTED_UP = 1;
	final int STAGE_POINTED_DOWN = 2;
	final int STAGE_END = 3;
	double HALF_SPEED = 0.5D;
	double speed;
	int currentStage;
	
	//TODO: Fix encoders and use them instead, going by time is ugly
	Timer timer;
	Timer endStageTimer;

	/** distances measured in feet
	 * 
	 */
	private double desiredTime;
	
	/**
	 * 
	 * @param distFeet
	 *            to move forward
	 */
	public MoveForwardsRockWall(double distFeet, double speed) {
		requires(Robot.drive);
		this.desiredTime = distFeet;
		this.speed = speed;
		
		timer = new Timer();
		endStageTimer = new Timer();
	}

	@Override
	protected void end() {	
		//STop the drive
		Robot.drive.operateDrive(0, 0);
		System.out.println("Ended");
	}

	@Override
	protected void execute() {
		
		switch(currentStage) {
		case STAGE_START:
			if (RobotMap.verticalGyro.getAngle() > 30) {
				currentStage = STAGE_POINTED_UP;
			}
			break;
		case STAGE_POINTED_UP:
			if (RobotMap.verticalGyro.getAngle() < -30) {
				currentStage = STAGE_POINTED_DOWN;
			}
			break;
		case STAGE_POINTED_DOWN:
			if (Math.abs(RobotMap.verticalGyro.getAngle()) < 10) {
				currentStage = STAGE_END;
				endStageTimer.start();
			}
			break;			
		}
		System.out.println("Gyro: " + RobotMap.verticalGyro.getAngle());
        Robot.drive.operateDrive(-speed, -speed); // drive towards heading 0
	}

	@Override
	
	protected void initialize() {
		timer.reset();
		timer.start();
		endStageTimer.reset();
		RobotMap.verticalGyro.reset();
		currentStage = 0;
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
		if (currentStage == STAGE_END) {
			return (endStageTimer.get() > 2);
		}
		else if (RobotMap.ultrasonic.getDistanceFeet() < 1) {
			System.out.println("TOO CLOSE");
			return true;
		}
		else {
			return timer.get() >= desiredTime;
		}
	}

}
