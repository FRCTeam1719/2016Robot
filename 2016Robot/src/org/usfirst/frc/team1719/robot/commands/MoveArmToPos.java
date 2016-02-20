package org.usfirst.frc.team1719.robot.commands;

import java.util.ArrayList;

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
	final double ERROR_TOLERANCE = 1;
	double currentPos;
	double desiredAngle;
	double desiredPotPos; // the value the potentiometer is giving
	
	boolean direction;
	double speed;
	
	double kP;
	double kI;
	double kD;

	double integral = 0;
	double derivative = 0;
	double previousError;
	
	ArrayList<Double> errors = new ArrayList<Double>(20);

	/**
	 * Move the arm to the desiredAngle
	 * @param desiredAngle double
	 */
    public MoveArmToPos(double desiredAngle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.arm);

    	this.desiredAngle = desiredAngle;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	kP = SmartDashboard.getNumber("Move arm to pos kP");
    	kI = SmartDashboard.getNumber("Move arm to pos kI");
    	kD = SmartDashboard.getNumber("Move arm to pos kD");
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
    	double error = Robot.arm.getArmAngle();
    	
    	integral += error;
    	
    	if (errors.size() == 20) {
    		errors.remove(0);
    		errors.add(error);
    	}
    	
    	if (Math.abs(error) < ERROR_TOLERANCE) {
    		integral = 0;
    	}
    	
    	derivative = error - previousError;
    	
    	double output = (error * kP) + (integral * kI) + (derivative * kD);
    	
    	//cap output at 1 and -1
    	if (output > 1) {
    		output = 1;
    	}
    	else if (output < 1) {
    		output = -1;
    	}
    	Robot.arm.move(output);
    	previousError = error;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        //make sure arm stops
    	double avgError = 0;
    	for (int i = 0; i < errors.size(); i++) {
    		avgError += errors.get(i);
    	}
    	avgError /= errors.size();
    	return (Math.abs(avgError) < ERROR_TOLERANCE);
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
