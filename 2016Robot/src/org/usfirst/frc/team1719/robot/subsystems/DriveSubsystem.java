package org.usfirst.frc.team1719.robot.subsystems;

import org.usfirst.frc.team1719.robot.commands.UseDrive;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveSubsystem extends Subsystem{

	SpeedController leftController;
	SpeedController rightController;
	RobotDrive mainDrive;
	
	
	
	public DriveSubsystem(SpeedController leftController,SpeedController rightController){
		mainDrive = new RobotDrive(leftController, rightController);
		this.leftController = leftController;
		this.rightController = rightController;
	}
	
	
	public void operateDrive(double left, double right){
		mainDrive.tankDrive(left, right);
	}
	

	public void initDefaultCommand(){
		setDefaultCommand(new UseDrive());
	}
	
}
