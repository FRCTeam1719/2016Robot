package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

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

    public MoveArmToPos(double desiredPos) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.arm);
    	this.desiredPos = desiredPos;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	currentPos = Robot.arm.getPos();
    	
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
    	System.out.println("Current Pos: " + currentPos + " | DesiredPos: " + desiredPos);

    	
    	if (desiredPos < currentPos) {
    		System.out.println("moving up!");
    		Robot.arm.move(-SPEED);
    	}
    	else if (desiredPos > currentPos) {
    		System.out.println("MOVING DOWN");
    		Robot.arm.move(SPEED);
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
