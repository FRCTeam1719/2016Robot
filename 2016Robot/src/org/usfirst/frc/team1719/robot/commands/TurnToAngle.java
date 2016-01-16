package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnToAngle extends Command {

	static double SPEED = 0.5D;
	
	private double desiredAngle;
	private double currentAngle = 0D;
	
	AnalogGyro gyro = RobotMap.gyro;
	
    public TurnToAngle(double angle) {
        // Use requires() here to declare subsystem dependencies
        
    	requires(Robot.drive);
    	
    	desiredAngle = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	gyro.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	currentAngle = gyro.getAngle();
    	
    	//turning clockwise
    	if (desiredAngle < 0) {
    		Robot.drive.operateDrive(SPEED, -SPEED);
    	}
    	else if (desiredAngle > 0) {
    		Robot.drive.operateDrive(-SPEED, SPEED);
    	}
    	else {
    		return;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        
    	//turning counterclockwise
    	if (desiredAngle < 0) {
    		return currentAngle <= desiredAngle;
    	}
    	//turning clockwise
    	else if (desiredAngle > 0) {
    		return currentAngle >= desiredAngle;
    	}
    	else {
    		return true;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
