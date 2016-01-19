package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShootBoulder extends Command {
	
	double leftPower;
	double rightPower;
	boolean hasShot;
	boolean isStabilized;
	
	public ShootBoulder(double leftPower,double rightPower)
	{
		this.leftPower = leftPower;
		this.rightPower = rightPower;
	}
	
	@Override
	protected void end() {
		
		SmartDashboard.putBoolean("Angle Reached", false);
		// TODO Auto-generated method stub
	
	}

	@Override
	protected void execute() {
		
		Robot.shooter.spin(leftPower, rightPower);
		isStabilized = Robot.shooter.isStabilized();
		
		if((!Robot.isAuton && isStabilized && Robot.oi.getFireButton())||
				(Robot.isAuton && isStabilized && SmartDashboard.getBoolean("Angle Reached")))
		{
			Robot.shooter.fire();
			hasShot = true;
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
		return hasShot;
	}

}
