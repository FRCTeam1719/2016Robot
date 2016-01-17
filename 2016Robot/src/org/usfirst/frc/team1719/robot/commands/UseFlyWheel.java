package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class UseFlyWheel extends Command{

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		Robot.rightFlywheel.reset();
		
		
	}

	@Override
	protected void execute() {
		Robot.rightFlywheel.spin(SmartDashboard.getNumber("FlywheelParam"));
	}

	@Override
	protected void initialize() {
		Robot.rightFlywheel.reset();
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
