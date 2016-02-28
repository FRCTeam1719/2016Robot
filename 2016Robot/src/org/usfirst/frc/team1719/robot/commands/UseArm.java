package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * Default command for the arm, drives it according to the Operator Joystick
 * @author aaroneline
 *
 */
public class UseArm extends Command{

	final double TOLERANCE = 0.1;
	final double CONTROL_SCALING = .75;
	final double LOW_RANGE_CONTROL_SCALING = .25;
	final double LOW_RANGE_THRESHOLD = -60;
	private double lastErr = 0.0D;
	private double integral = 0.0D;
	
	public UseArm(){
		requires(Robot.arm);
	}
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {

	    double joystickReading = Robot.oi.getArmReading();
	    double motorSpeed;

		if(Math.abs(joystickReading) < TOLERANCE){ // joystick not used, hold arm steady with PID + sinusoidally varing force
		    double kP = SmartDashboard.getNumber("Arm steady kP");
	        double kI = SmartDashboard.getNumber("Arm steady kI");
	        double kD = SmartDashboard.getNumber("Arm steady kD");
	        double rng = SmartDashboard.getNumber("Arm steady integral range");
	        double angle = Robot.arm.getArmAngle();
	        double error = (Robot.arm.getTargetPos() - angle);
	        if(Math.abs(error) < rng) integral += error;
	        double derivative = error - lastErr;
	        motorSpeed = kP * error + kI * integral + kD * derivative;
		} else { // joystick touched, reset integral and desired pos
		    integral = 0;
		    Robot.arm.setTargetPos(Robot.arm.getArmAngle());
			//Apply control scaling
		    if(Robot.arm.getArmAngle()<LOW_RANGE_THRESHOLD)
		    	if (joystickReading < 0) {
		    		motorSpeed = joystickReading *LOW_RANGE_CONTROL_SCALING;
		    	}
		    	else {
		    		motorSpeed = joystickReading * CONTROL_SCALING;
		    	}
		    else
		    	motorSpeed = joystickReading * CONTROL_SCALING;
		}
		if (motorSpeed > 0.7) {
			motorSpeed = 0.7;
		}
		else if (motorSpeed < -0.7) {
			motorSpeed = -0.7;
		}

		
		
		Robot.arm.move(motorSpeed);
		//System.out.println("Arm Angle: "+Robot.arm.getArmAngle());
		//System.out.println("motor speed: " + motorSpeed);
		System.out.println("Target angle: " + Robot.arm.getTargetPos());
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {
		
	}

}
