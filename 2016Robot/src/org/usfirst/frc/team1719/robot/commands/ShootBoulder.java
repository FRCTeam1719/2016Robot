package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShootBoulder extends Command {
	
	double leftPower;
	double rightPower;
	boolean hasShot;
	boolean isStabilized;
	final double WAIT_TIME = 0.5;
	Timer timer;
	
	//Shoot boulder (desired leftPower, desired rightPower)
	public ShootBoulder(double leftPower,double rightPower)
	{
		requires(Robot.shooter);
		this.leftPower = leftPower;
		this.rightPower = rightPower;
		timer = new Timer();
	}
	
	@Override
	protected void end() {
		
		SmartDashboard.putBoolean("Angle Reached", false);
		Robot.shooter.reset();
	}

	@Override
	protected void execute() {
		//TODO Uncomment once custom encoders work
//		Robot.shooter.spin(leftPower, rightPower);
//		isStabilized = Robot.shooter.isStabilized();
		isStabilized = true;
		//if we shoot the boulder check if the angle has been reached before firing
		if((!Robot.isAuton && isStabilized && Robot.oi.getFireButton())||
				(Robot.isAuton && isStabilized && SmartDashboard.getBoolean("Angle Reached")))
		{
			Robot.shooter.fire();
			//TODO Maybe change this
			//hasShot = true;
			timer.start();
		}
	}

	@Override
	protected void initialize() {
		Robot.shooter.reset();
	}

	@Override
	protected void interrupted() {

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
