package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
/**
 * Default command for the Drive
 * Controls it via tank drive from the Driver XBOX
 * @author aaroneline
 *
 */
public class UseDrive extends Command{

	final double DEADZONE = 0.0125D;
	final double NIL = 0.0;
	
	public UseDrive(){
		requires(Robot.drive);
	}
	double startValueL = 0;
	double startValueR = 0;
	final double SMOOTH = 0.5;
	double corectedValueL = 0;
	double corectedValueR = 0;
	final double SYNCHTOLERANCE = 0.15;
	@Override
	protected void initialize() {
		// No initialization needed
		
	}

	@Override
	protected void execute() {
		double left = Robot.oi.getLeftDriveReading();
		double right = Robot.oi.getRightDriveReading();
		
		//Drive algorithms 
		
		//Deadzone calculations
		if(Math.abs(left)<DEADZONE){
			left = NIL;
		}
		if(Math.abs(right)<DEADZONE){
			right = NIL;
		}
	    //Adjust sensitivity
		left = Math.abs(left) * left;
		right = Math.abs(right) * right;
		//Smooth Drive
		if(left != 0){
			corectedValueL = (left*SMOOTH) + ( corectedValueL * ( 1.0 - SMOOTH));
			left = corectedValueL;
		}
		if(right != 0){
			corectedValueR = (right*SMOOTH) + ( corectedValueR * ( 1.0 - SMOOTH));
			right = corectedValueR;
		}
		//Sync the two sides speed if within the tolerance
		if(Math.abs(left - right) < SYNCHTOLERANCE){
			double corectedSpeed = (left +right) /2;
			left = corectedSpeed;
			right = corectedSpeed;
		}
		Robot.drive.operateDrive(-right, -left);
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
