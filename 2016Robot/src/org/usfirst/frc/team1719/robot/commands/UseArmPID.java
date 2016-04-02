package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * Default command for the arm, drives it according to the Operator Joystick
 * @author aaroneline
 *
 */
public class UseArmPID extends Command{

	final double TOLERANCE = 0.1;
	final double CONTROL_SCALING = .75;
	final double LOW_RANGE_CONTROL_SCALING = .25;
	final double LOW_RANGE_THRESHOLD = -60;
	private double lastErr = 0.0D;
	private double integral = 0.0D;
	private final double STANDARD_SPEED = 0.7;
	private final double SLOW_SPEED = 0.3;
	private final double DANGER_ANGLE = 8.5;
	private final double MIN_ANGLE = 0;
	private final double MAX_ANGLE = -35;
	public UseArmPID(){
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
	        double error = -(Robot.arm.getTargetPos() - angle);
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
		//Bound our speed. if we are near our target, slow us down.
		double boundedSpeed;
		if(nearEnds()){
			boundedSpeed = SLOW_SPEED;
		}else{
			boundedSpeed = STANDARD_SPEED;
		}
		
		
		if (motorSpeed > boundedSpeed) {
			motorSpeed = boundedSpeed;
		}
		else if (motorSpeed < -boundedSpeed) {
			motorSpeed = -boundedSpeed;
		}

		
		
		Robot.arm.move(motorSpeed);
		System.out.println("Angle: "+Robot.arm.getArmAngle());
		System.out.println("Raw Reading: "+Robot.arm.getRawReading());
	}

	/**
	 * 
	 * @return whether or not the arm is near the extremes
	 */
	private boolean nearEnds(){
		boolean nearTop = false;
		boolean nearBottom = false;
		if(DANGER_ANGLE  > Math.abs(Robot.arm.getArmAngle() - MAX_ANGLE)){
			nearTop = true;
		}
		if(DANGER_ANGLE > Math.abs(Robot.arm.getArmAngle() - MIN_ANGLE)){
			nearBottom = true;
		}
		return nearTop || nearBottom;
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {
		
	}

}