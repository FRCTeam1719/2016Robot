package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class UseFlyWheel extends Command{
	
	public UseFlyWheel() {
		requires(Robot.rightFlywheel);
	}

	double leftPower;
	double rightPower;
	
	public UseFlyWheel(double leftPower, double rightPower)
	{
		this.leftPower = leftPower;
		this.rightPower = rightPower; 
	}
	
	@Override
	protected void end() {
		// TODO Auto-generated method stub
		Robot.rightFlywheel.reset();
		Robot.leftFlywheel.reset();
		
	}

	@Override
	protected void execute() {

		Robot.rightFlywheel.spin(rightPower);
		Robot.leftFlywheel.spin(leftPower);
		
	}

	@Override
	protected void initialize() {
		Robot.rightFlywheel.reset();
		Robot.leftFlywheel.reset();
		// TODO Auto-generated method stub
		
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
