package org.usfirst.frc.team1719.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class Wait extends Command {

	double waitTime;
	Timer timer;
	
	//
	public Wait(double timeSeconds) {
		waitTime = timeSeconds;
		timer = new Timer();
	}
	@Override
	protected void initialize() {
		timer.reset();
		timer.start();
		
	}

	@Override
	protected void execute() {
		
		
	}

	@Override
	protected boolean isFinished() {
		return (timer.get() >= waitTime);
	}

	@Override
	protected void end() {
		timer.stop();
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
