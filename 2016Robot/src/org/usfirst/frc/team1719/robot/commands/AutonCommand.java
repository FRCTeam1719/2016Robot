package org.usfirst.frc.team1719.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Skeleton Autonomous command that simply outputs which auton we are using
 * 
 * @author aaroneline
 *
 */
public class AutonCommand extends Command{

	int commandNum;
	
	public AutonCommand(int commandNum){
		this.commandNum = commandNum;
	}
	
	
	@Override
	protected void initialize() {
		
	}

	@Override
	protected void execute() {
		System.out.println("Autonomous Command Number: "+commandNum);
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
