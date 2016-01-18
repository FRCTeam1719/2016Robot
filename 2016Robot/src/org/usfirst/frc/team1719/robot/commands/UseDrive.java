package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class UseDrive extends Command{

	final double TOLERANCE = 0.1;
	final double NIL = 0.0;
	
	public UseDrive(){
		requires(Robot.drive);
	}
	
	
	@Override
	protected void initialize() {
		// No initialization needed
		
	}

	@Override
	protected void execute() {
		double left = Robot.oi.getLeftReading();
		double right = Robot.oi.getRightReading();
		if(Math.abs(left)<TOLERANCE){
			left = NIL;
		}
		if(Math.abs(right)<TOLERANCE){
			right = NIL;
		}
	    //Adjust sensitivity
		left = Math.abs(left) * left;
		right = Math.abs(right) * right;
		//Sync the two sides if within the tolerance
		if(Math.max(Math.abs(left), Math.abs(right)) - Math.min(Math.abs(left), Math.abs(right)) < TOLERANCE){
			double corectedSpeed = (left +right) /2;
			left = corectedSpeed;
			right = corectedSpeed;
		}
		Robot.drive.operateDrive(-right, -left);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
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
