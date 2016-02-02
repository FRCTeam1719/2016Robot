package org.usfirst.frc.team1719.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class AutonCommand extends Command{

	int commandNum;
	
	public AutonCommand(int commandNum){
		this.commandNum = commandNum;
	}
	
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		System.out.println("Autonomous Command Number: "+commandNum);
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
