package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Move the arm to a specified angle
 */
public class MoveArmToPos extends Command {

	final double SPEED = 0.5;
	final boolean DIRECTION_UP = true;
	final boolean DIRECTION_DOWN = false;
	double currentPos;
	double desiredAngle;
	double desiredPotPos; // the value the potentiometer is giving
	boolean direction;
	double speed;


	/**
	 * Move the arm to the desiredAngle
	 * 
	 * @param desiredAngle
	 *            double
	 */
    public MoveArmToPos(double desiredAngle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.arm);

    	this.desiredAngle = desiredAngle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//TODO Math to turn desiredAngle into desiredPotPos 
        desiredPotPos = desiredAngle;
    	currentPos = Robot.arm.getArmAngle();
    	if(desiredAngle == -1337) desiredAngle = SmartDashboard.getNumber("MoveArmParam");
    	if (currentPos < desiredAngle) {
    		direction = DIRECTION_UP;
    	}
    	else if (currentPos > desiredPotPos) {
    		direction = DIRECTION_DOWN;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	currentPos = Robot.arm.getArmAngle();
    	
    	if (direction == DIRECTION_DOWN) {
    		Robot.arm.move(-speed);
    		currentPos = Robot.arm.getArmAngle();
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
		// make sure arm stops
		if (direction == DIRECTION_UP) {
			if (currentPos >= desiredPotPos) {
				Robot.arm.move(0);
				return true;
			} else {
				return false;
			}
		} else if (direction == DIRECTION_DOWN) {
			if (currentPos <= desiredPotPos) {
				Robot.arm.move(0);
				return true;
			} else {
				return false;
			}
		} else {
			Robot.arm.move(0);
			System.out.println("ERROR: Boolean is neither true nor false in MoveArmToPos, call reality police.");
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
