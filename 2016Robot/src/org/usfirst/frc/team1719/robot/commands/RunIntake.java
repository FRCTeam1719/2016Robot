package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.subsystems.DualShooter;

import edu.wpi.first.wpilibj.command.Command;

public class RunIntake extends Command{

	public RunIntake(){
		requires(Robot.shooter);
	}
	
	@Override
	protected void initialize() {
		
		
	}

	@Override
	protected void execute() {
		Robot.shooter.runInnerMotors(DualShooter.spinMode.INTAKE);
		Robot.shooter.spin(DualShooter.spinMode.INTAKE);
	}
	

	@Override
	protected boolean isFinished() {
		return !Robot.oi.getIntakeButton();
	}

	@Override
	protected void end() {
		Robot.shooter.reset();
		
	}

	@Override
	protected void interrupted() {
		Robot.shooter.reset();
		
	}

}
