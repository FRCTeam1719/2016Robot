package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.settings.PIDData;

import edu.wpi.first.wpilibj.command.Command;
public class UseArm extends Command{
	PIDData pidData;
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
		double joystickReading = Robot.oi.getArmReading();
		//Apply control scaling
		double motorSpeed = joystickReading * CONTROL_SCALING;
		if(Math.abs(joystickReading)<TOLERANCE){
			motorSpeed = 0;
			
		}
		Robot.arm.move(motorSpeed);
		System.out.println("Arm Angle: "+Robot.arm.getArmAngle());
		
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
