package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
/**
 * Bring the shooter up to speed, indicated on the Smartdashboard when you are done
 * @author aaroneline
 *
 */
public class RevUpShooter extends Command{

	public RevUpShooter(){
		requires(Robot.shooter);
	}
	
	@Override
	protected void initialize() {
		//No initialization needed
	}

	@Override
	protected void execute() {
		Robot.shooter.spin(1, 1);
		
	}

	@Override
	protected boolean isFinished() {
		// TODO only return true when the shooter is up to speed
		return true;
	}

	@Override
	protected void end() {
		
		
	}

	@Override
	protected void interrupted() {
		
		
	}

}
