package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class ManualShoot extends Command{

	final double WAIT_TIME = 5.0;
	boolean hasShoot;
	Timer timer;
	
	public ManualShoot(){
		requires(Robot.shooter);
	}
	
	@Override
	protected void end() {
		Robot.shooter.reset();
		
	}

	@Override
	protected void execute() {
		if(Robot.oi.getFireButton()){
			Robot.shooter.runInnerMotors(Robot.shooter.EJECT);
			timer.start();
		}
		
	}

	@Override
	protected void initialize() {
		timer = new Timer();
		hasShoot = false;
		//Spin up the Fly Wheels
		Robot.shooter.spin(1, -1);
	}

	@Override
	protected void interrupted() {
		Robot.shooter.reset();
		
	}

	@Override
	protected boolean isFinished() {
		if(timer.get()>WAIT_TIME){
			timer.stop();
			timer.reset();
			return true;
		}else{
			return false;
		}
	}

}
