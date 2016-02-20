package org.usfirst.frc.team1719.robot.commands;

import java.util.ArrayList;

import org.usfirst.frc.team1719.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Move the arm to a specified angle
 */
public class MoveArmToPos extends Command {
	
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
	
	double error;
	double previousError;
	
	double[] errors = new double[20];

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
    	error = desiredAngle - Robot.arm.getArmAngle();
    	
    	integral += error;
    	
    	
    	
    	if (Math.abs(error) < ERROR_TOLERANCE) {
    		integral = 0;
    	}
    	
    	derivative = error - previousError;
    	

    	
    	
    	double output = (error * kP) + (integral * kI) + (derivative * kD) + 0.2;
    	
    	//cap output at 1 and -1
    	if (output > .7) {
    		output = .7;
    	}
    	else if (output < -.7) {
    		output = -.7;
    	}
    	Robot.arm.move(output);
    	previousError = error;
    	System.out.println("output: " + output);
    	System.out.println("angle: " + Robot.arm.getArmAngle());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double avgVelocity = 0;
    	
    	
    	if (Math.abs(error) < ERROR_TOLERANCE) {
    		System.out.println("within error tolerance");
    		if (avgVelocity < 1) {
    			return true;
    		}
    		
    	}
    	return false;
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
