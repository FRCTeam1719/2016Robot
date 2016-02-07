package org.usfirst.frc.team1719.robot.subsystems;

import org.usfirst.frc.team1719.robot.commands.UseArm;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
/**
 * Arm subsystem. 
 * Controlled by a Spark motor controller w/ limit siwtches
 * Moves freely in one axis over the robot
 * Data on current position is obtained via a potentiometer
 * @author aaroneline
 *
 */
public class Arm extends Subsystem {
	

	Spark motor;
	
	AnalogPotentiometer pot;
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new UseArm());
		
	}
	
	public Arm(Spark motor, AnalogPotentiometer pot) {
		this.motor = motor;
		this.pot = pot;
		motor.set(0);
	}
	
	/**
	 * Move the arm at desired speed
	 * @param speed
	 */
	public void move(double speed) {
		motor.set(speed);
	}
	
	/**
	 * Get the current angle fo the arm 
	 * @return
	 */
	public double getArmAngle() {
		return pot.get();
	}
	
	
}
