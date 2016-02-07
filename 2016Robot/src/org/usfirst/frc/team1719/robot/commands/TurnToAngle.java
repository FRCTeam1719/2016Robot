package org.usfirst.frc.team1719.robot.commands;

import java.util.ArrayList;

import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurnToAngle extends Command {

	final double SPEED = 0.75D;

	//in degrees
	final double TOLERANCE = 0.5;
	
	double kP;
	double kI;
	double kD;
	
	private boolean resetGyro;
	private double tunedAngle;
	private double currentAngle = 0D;
	
	double integral;
	double derivative;
	double previousError;
	
	ArrayList<Double> errors = new ArrayList<Double>();
	
	AnalogGyro gyro = RobotMap.gyro;
	
    public TurnToAngle(double desiredAngle, boolean resetGyro) {
        // Use requires() here to declare subsystem dependencies
        
    	requires(Robot.drive);

    	//TODO make this better
    	
    	tunedAngle = desiredAngle;
    	this.resetGyro = resetGyro;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (resetGyro) {
    		gyro.reset();
    	}
    	kP = SmartDashboard.getNumber("Turn kP");
    	kI = SmartDashboard.getNumber("Turn kI");
    	kD = SmartDashboard.getNumber("Turn kD");
    	if(tunedAngle == -1337) tunedAngle = SmartDashboard.getNumber("TurnToAngleParam");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()  {
    	
    	
    	
    	currentAngle = gyro.getAngle();
    	double error = tunedAngle - currentAngle;
    	
    	if (errors.size() >= 10) {
    		errors.remove(0);
    		errors.add(error);
    	}
    	else {
    		errors.add(error);
    	}
    	
    	integral += error;
    	
    	if (Math.abs(error) < TOLERANCE) {
    		integral = 0;
    	}
    	
    	
    	derivative = error - previousError;
    	double output = (error * kP) + (integral * kI) + (derivative * kD);
    	double rightOutput = (output / 2);
    	double leftOutput = -(output / 2);
    	System.out.println("desired Angle: " + tunedAngle + " | Current angle: " + currentAngle);
    	System.out.println("Error: " + error + " | Output: " + output);
    	
    	Robot.drive.operateDrive(leftOutput, rightOutput);
    	
    	
    	//turning clockwise
    	previousError = error;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        
    	double avgError = 0;
    	
    	for (int i = 0; i < errors.size(); i++) {
    		avgError += errors.get(i);
    	}
    	avgError /= errors.size();
    	
    	return (Math.abs(avgError) < TOLERANCE);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
