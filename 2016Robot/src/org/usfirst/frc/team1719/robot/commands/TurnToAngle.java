package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Turns the drive to the specified angle
 */
public class TurnToAngle extends Command {

	final double SPEED = 0.75D;

	
	private double tunedAngle;
	private double currentAngle = 0D;
	
	
	AnalogGyro gyro = RobotMap.gyro;
	/**
	 * 
	 * @param desiredAngle
	 */
    public TurnToAngle(double desiredAngle) {
        // Use requires() here to declare subsystem dependencies
        
    	requires(Robot.drive);
    	//TODO make this better
    	
    	tunedAngle = desiredAngle;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	gyro.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	
    	
    	
    	currentAngle = gyro.getAngle();
    	//turning clockwise
    	if (tunedAngle < 0) {
    		Robot.drive.operateDrive(SPEED, -SPEED);
    	}
    	else if (tunedAngle > 0) { //turning counter clockwise
    		Robot.drive.operateDrive(-SPEED, SPEED);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        
    	//turning counterclockwise
    	if (tunedAngle < 0) {
    		return currentAngle <= tunedAngle;
    	}
    	//turning clockwise
    	else if (tunedAngle > 0) {
    		return currentAngle >= tunedAngle;
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
