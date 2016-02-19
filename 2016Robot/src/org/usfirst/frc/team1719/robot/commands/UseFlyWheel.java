package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class UseFlyWheel extends Command{
	
	public UseFlyWheel() {
		requires(Robot.rightFlywheel);
		requires(Robot.leftFlywheel);
	}

	double leftPower;
	double rightPower;
	
	public UseFlyWheel(double leftPower, double rightPower)
	{
		requires(Robot.rightFlywheel);
		requires(Robot.leftFlywheel);
		this.leftPower = leftPower;
		this.rightPower = rightPower; 
	}
	
	@Override
	protected void end() {
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
	}

	@Override
	protected void interrupted() {
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
