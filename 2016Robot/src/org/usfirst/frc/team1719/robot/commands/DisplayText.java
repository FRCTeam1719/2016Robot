package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DisplayText extends Command{

	String text;
	
	public DisplayText(String text){
		requires(Robot.display);
		this.text = text;
	}
	
	
	@Override
	protected void initialize() {
		//No initialization needed
	}

	@Override
	protected void execute() {
		Robot.display.displayString("1719");
		
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
