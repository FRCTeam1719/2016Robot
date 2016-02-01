package org.usfirst.frc.team1719.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1719.robot.Robot;
public class DisplayVoltage extends Command {


	public DisplayVoltage(){
		requires(Robot.display);
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		System.out.println("Hit DisplayDrawTest Command Init");
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		System.out.println("Command Running");
		String voltage = Double.toString(DriverStation.getInstance().getBatteryVoltage());
		Robot.display.displayString(voltage);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
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
