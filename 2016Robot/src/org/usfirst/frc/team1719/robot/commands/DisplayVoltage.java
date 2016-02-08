package org.usfirst.frc.team1719.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1719.robot.Robot;

/**
 * Displays the current Battery Voltage on the DigitBoard
 * @author aaroneline
 *
 */
public class DisplayVoltage extends Command {


	public DisplayVoltage(){
		requires(Robot.display);
	}

	@Override
	protected void initialize() {
		// No initialization needed
	}

	@Override
	protected void execute() {
		String voltage = Double.toString(DriverStation.getInstance().getBatteryVoltage());
		Robot.display.displayString(voltage);
	}

	@Override
	protected boolean isFinished() {
		// Finish right away
		return true;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

}
