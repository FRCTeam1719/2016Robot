package org.usfirst.frc.team1719.robot.subsystems;

import org.usfirst.frc.team1719.robot.subsystems.logical.IFlyWheel;
import org.usfirst.frc.team1719.robot.subsystems.logical.LogicalFlyWheel;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Flywheel subsystem Composed of: 1 Spark motor controller for spinning the
 * wheel An encoder for measuring wheel speed The subsystem is used as a
 * component of the larger shooter mechanism The Flywheel is required to be able
 * to spin and consistent speeds independent of battery voltage
 * 
 * @author aaroneline
 *
 */
public class FlyWheel extends Subsystem implements IFlyWheel{

	private LogicalFlyWheel logic;
	
	public FlyWheel(SpeedController motor){
		logic = new LogicalFlyWheel(motor);
	}
	
	
	@Override
	public void reset() {
		logic.reset();
		
	}

	@Override
	public void spin(double speed) {
		logic.spin(speed);
	}

	@Override
	public boolean isStabilized() {
		return logic.isStabilized();
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

	
	
}