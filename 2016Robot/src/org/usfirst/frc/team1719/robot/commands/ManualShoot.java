package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class ManualShoot extends Command{

	final double SHOOT_WAIT_TIME = 2.5;
	final double PREP_WAIT_TIME = 0.2;
	private boolean prepComplete;
	private Timer shootTimer;
	private Timer prepTimer;
	
	public ManualShoot(){
		requires(Robot.shooter);
		shootTimer = new Timer();
		prepTimer = new Timer();
	}
	
	@Override
	protected void end() {
		Robot.shooter.reset();
		
	}

	@Override
	protected void execute() {
		if(prepTimer.get()>PREP_WAIT_TIME){
				//Spin up the Fly Wheels
				Robot.shooter.spin(1, -1);
				Robot.shooter.runInnerMotors(Robot.shooter.INTAKE);
				prepTimer.stop();
				prepComplete = true;
		}
		if(Robot.oi.getFireButton()&&prepComplete){
			Robot.shooter.runInnerMotors(Robot.shooter.EJECT);
			shootTimer.start();
		}
		
	}

	@Override
	protected void initialize() {
		shootTimer.reset();
		prepTimer.reset();
		prepComplete = false;
		//Move ball into back of shooter
		
		prepTimer.start();
	}

	@Override
	protected void interrupted() {
		Robot.shooter.reset();
		
	}

	@Override
	protected boolean isFinished() {
		if(shootTimer.get()>SHOOT_WAIT_TIME){
			shootTimer.stop();
			shootTimer.reset();
			return true;
		}else{
			return false;
		}
	}

}
