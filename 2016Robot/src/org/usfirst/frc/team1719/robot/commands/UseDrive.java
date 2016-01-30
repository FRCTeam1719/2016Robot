package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class UseDrive extends Command{

	final double TOLERANCE = 0.025D;
	final double NIL = 0.0;
	
	public UseDrive(){
		requires(Robot.drive);
	}
	double startValueL = 0;
	double startValueR = 0;
	double smooth = 0.5;
	double corectedValueL = 0;
	double corectedValueR = 0;
	double sychTolerance = 1.5;
	@Override
	protected void initialize() {
		// No initialization needed
		
	}

	@Override
	protected void execute() {
		double left = Robot.oi.getLeftReading();
		double right = Robot.oi.getRightReading();
		// Zero out the motors.
		if(Math.abs(left)<TOLERANCE){
			left = NIL;
		}
		if(Math.abs(right)<TOLERANCE){
			right = NIL;
		}
	    //Adjust sensitivity
		left = Math.abs(left) * left;
		right = Math.abs(right) * right;
		//Smooth Drive
		if(left != 0){
		corectedValueL = (left*smooth) + ( corectedValueL * ( 1.0 - smooth));
		left = corectedValueL;
		}
		if(right != 0){
		corectedValueR = (right*smooth) + ( corectedValueR * ( 1.0 - smooth));
		right = corectedValueR;
		}
		//Sync the two sides speed if within the tolerance
		if(Math.abs(left - right) < sychTolerance){
			double corectedSpeed = (left +right) /2;
			left = corectedSpeed;
			right = corectedSpeed;
		}
		Robot.drive.operateDrive(-right, -left);
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
