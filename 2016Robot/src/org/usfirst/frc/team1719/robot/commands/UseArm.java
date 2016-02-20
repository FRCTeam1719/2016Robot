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
	
	public UseArm(){
		requires(Robot.arm);
	}
	@Override
	protected void initialize() {
		
		
	}

	@Override
	protected void execute() {
		double kP = SmartDashboard.getNumber("Arm steady kP");
		double angle = Robot.arm.getArmAngle();
		double joystickReading = Robot.oi.getArmReading();
		//Apply control scaling
		double motorSpeed = joystickReading * CONTROL_SCALING;
		
		if (Robot.arm.getArmAngle() < -95) {
			
			//if the lower button isn't pressed
//			if (! (Robot.oi.getLowerButton())) {
//				System.out.println("ARM BELOW POS, MOVING");
//				new MoveArmToPos(-85).start();
//				return;
//			}
		}
		if(Math.abs(joystickReading)<TOLERANCE){
			
			if (angle > 10) {
				motorSpeed = -0.2;
			}
			else if (angle < -20) {
				motorSpeed = 0.2;
			}
			
		}
		Robot.arm.move(motorSpeed);
		System.out.println("Arm Angle: "+Robot.arm.getArmAngle());
		System.out.println("motor speed: " + motorSpeed);
		
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
