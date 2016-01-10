package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class toggleShifters extends Command{

	@Override
	protected void initialize() {
		// No init
		
	}

	@Override
	protected void execute() {
		Robot.shifters.toggleState();
		System.out.println("SHIFTED!");
		
	}

	@Override
	protected boolean isFinished() {
		// Only run once
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
