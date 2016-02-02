package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class UseArm extends Command{

	final double TOLERANCE = 0.1;
	
	public UseArm(){
		requires(Robot.arm);
	}
	@Override
	protected void initialize() {
		
		
	}

	@Override
	protected void execute() {
		System.out.println("USING ARM");
		double speed = Robot.oi.getArmReading();
		if(Math.abs(speed)<TOLERANCE){
			
			speed = 0;
		}
		Robot.arm.move(speed);
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
