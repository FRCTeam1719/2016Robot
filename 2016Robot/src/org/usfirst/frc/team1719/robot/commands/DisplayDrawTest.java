package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.subsystems.Display;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;

public class DisplayDrawTest extends Command {

	public static Display display = new Display();
	PowerDistributionPanel pdp = new PowerDistributionPanel();


	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		double voltage = DriverStation.getInstance().getBatteryVoltage();
		voltage = voltage*100;
		String volt = Double.toString(voltage);
		display.displayString(volt);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
