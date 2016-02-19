package org.usfirst.frc.team1719.robot.commands;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.ToDoubleFunction;

import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Turns the drive to the specified angle
 */
public class TurnToAngle extends Command {
    private static final int QUEUE_LENGTH = 10;
	//in degrees
	private static final double TOLERANCE = 0.5;
	private static final double MAX_PWR = 1.0D;
	private static final ToDoubleFunction<Double> ident = new ToDoubleFunction<Double>() {
        
        @Override
        public double applyAsDouble(Double value) {
            return value;
        }
    };
	
	double kP;
	double kD;
	
	private double tunedAngle;
	private double currentAngle = 0D;
	
	double previousError;
	
	Deque<Double> errors = new ArrayDeque<Double>();
	private boolean shouldResetGyro;
	
	AnalogGyro gyro = RobotMap.gyro;

	/**
	 * 
	 * Negative Angles move to the left, Positive to the Right
	 * @param desiredAngle to turn to
	 */

    public TurnToAngle(double desiredAngle, boolean shouldResetGyro) {
        // Use requires() here to declare subsystem dependencies
        
    	requires(Robot.drive);

    	//TODO make this better
    	
    	tunedAngle = desiredAngle;
    	this.shouldResetGyro = shouldResetGyro;
    	for(int i = 0; i < QUEUE_LENGTH; i++) errors.push(0.0D);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	kP = SmartDashboard.getNumber("Turn kP");
    	kD = SmartDashboard.getNumber("Turn kD");
    	if(tunedAngle == 0.0D) tunedAngle = SmartDashboard.getNumber("TurnToAngleParam");
    	if(shouldResetGyro){
    		gyro.reset();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()  {
    	
    	
    	
    	currentAngle = gyro.getAngle();
    	double error = tunedAngle - currentAngle;
    	
    	errors.add(error);
    	errors.remove();
    	
    	double derivative = error - previousError;
    	previousError = error;
    	
    	double output = Math.max(Math.min((error * kP) + (derivative * kD), MAX_PWR), -MAX_PWR);
    	
    	Robot.drive.operateDrive(output, -output);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double avgError = errors.stream().mapToDouble(ident).sum() / errors.size();
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
