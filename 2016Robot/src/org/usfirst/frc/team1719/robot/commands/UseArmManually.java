package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
/**
 * Default command for the arm, drives it according to the Operator Joystick
 * @author aaroneline
 *
 */
public class UseArmManually extends Command{

	final double TOLERANCE = 0.1;
	final double CONTROL_SCALING = .75;
	final double LOW_RANGE_CONTROL_SCALING = .25;
	final double LOW_RANGE_THRESHOLD = -60;

	
	public UseArmManually(){
		requires(Robot.arm);
	}
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {

	    double joystickReading = Robot.oi.getArmReading();
	    double motorSpeed;

		 // joystick not used, hold arm steady with PID + sinusoidally varing force
		    
		// joystick touched,
		    motorSpeed = joystickReading * Math.abs(joystickReading);
		
		if (motorSpeed > 0.7) {
			motorSpeed = 0.7;
		}
		else if (motorSpeed < -0.7) {
			motorSpeed = -0.7;
		}

		
		
		Robot.arm.move(motorSpeed);
		//System.out.println("Arm Angle: "+Robot.arm.getArmAngle());
		//System.out.println("motor speed: " + motorSpeed);
		System.out.println("Arm angle: " + Robot.arm.getArmAngle());
		System.out.println("Raw pot value" + RobotMap.armPot.getRaw());
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
//
	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {
		
	}

}
