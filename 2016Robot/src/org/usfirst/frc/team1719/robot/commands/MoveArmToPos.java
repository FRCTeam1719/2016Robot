package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class MoveArmToPos extends Command {
	
	final double SPEED = 0.5;
	final boolean DIRECTION_UP = true;
	final boolean DIRECTION_DOWN = false;
	double currentPos;
	double desiredPos;
	
	boolean direction;
	double speed;

    public MoveArmToPos(double desiredPos, double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.arm);
    	this.desiredPos = desiredPos;
    	this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	currentPos = Robot.arm.getPos();
    	if(desiredPos == -1337) desiredPos = SmartDashboard.getNumber("MoveArmParam");
    	if (currentPos < desiredPos) {
    		direction = DIRECTION_UP;
    	}
    	else if (currentPos > desiredPos) {
    		direction = DIRECTION_DOWN;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	currentPos = Robot.arm.getPos();

    	
    	if (direction == DIRECTION_DOWN) {
    		Robot.arm.move(-speed);
    	}
    	else if (direction == DIRECTION_UP) {
    		Robot.arm.move(speed);
    	}
    	else {
    		return;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        
    	if (direction == DIRECTION_UP) {
    		if (currentPos >= desiredPos) {
    			Robot.arm.move(0);
    			return true;
    		}
    		else {
    			return false;
    		}
    	}
    	else if (direction == DIRECTION_DOWN) {
    		if (currentPos <= desiredPos) {
    			Robot.arm.move(0);
    			return true;
    		}
    		else {
    			return false;
    		}
    	}
    	else {
    		Robot.arm.move(0);
    		System.out.println("HOLY FUCK EVERYTHING IS BROKEN");
    		return true;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.arm.move(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.arm.move(0);
    }
}
