package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class UsePIDDrive extends Command{
	
	public UsePIDDrive(){
		requires(Robot.drive);
	}

	@Override
	public void initialize(){
		Robot.drive.setPIDControllers(true);
	}
	
	@Override
	public void execute(){
		double leftJoy = Robot.oi.getLeftDriveReading();
		double rightJoy = Robot.oi.getRightDriveReading();
		
		
	}

}
