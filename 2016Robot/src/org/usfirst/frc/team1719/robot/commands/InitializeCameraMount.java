package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class InitializeCameraMount extends Command{

	private final double MOVEMENT_TIME = 0.5;
	private Timer waitTimer;
	
	public InitializeCameraMount(){
		requires(Robot.cameraMount);
		waitTimer = new Timer();
	}
	
	@Override
	public void initialize(){
		Robot.cameraMount.setAzimuth(0.5);
		Robot.cameraMount.setAzimuth(0.5);
		waitTimer.start();
	}
	
	@Override
	public void execute(){
		//No action needed
	}
	
	@Override
	public boolean isFinished(){
		return waitTimer.get() >= MOVEMENT_TIME;
	}
	
	@Override
	public void end(){
		waitTimer.stop();
	}
	
	@Override
	public void interrupted(){
		
	}

}
