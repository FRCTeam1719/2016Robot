package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
/**
 * 
 * @author aaroneline
 * Takes a String and displays it on the DigitBoard
 */
public class DisplayText extends Command{

	String text;
	/**
	 * String to display on DigitBoard
	 * @param text
	 */
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
		return true;
	}

	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {
		
	}

}
