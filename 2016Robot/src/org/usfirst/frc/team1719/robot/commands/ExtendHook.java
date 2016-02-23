package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ExtendHook extends Command {
	
	int loopNumber = 0;
	int desiredLoopNumber;
	
	public ExtendHook () {
		requires(Robot.tapeSubsystem);
	}
	@Override
	protected void end() {
		Robot.tapeSubsystem.stop();
		
	}

	@Override
	protected void execute() {
		Robot.tapeSubsystem.retract();		
	}

	@Override
	protected void initialize() {		
	}
	

	@Override
	protected void interrupted() {
		Robot.tapeSubsystem.stop();
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
