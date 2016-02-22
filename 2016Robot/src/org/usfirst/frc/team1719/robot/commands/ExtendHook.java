package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ExtendHook extends Command {
	
	int loopNumber = 0;
	int desiredLoopNumber ;
	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		Robot.climber.extend (1,-1);
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initialize() {
		
		loopNumber = 0;

		// TODO Auto-generated method stub
		
	}
	
	public ExtendHook () {
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
