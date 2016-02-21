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
	private double targetPos = 0.0D;
	
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
	 * @param speed to move at
	 */
	public void move(double speed) {
		motor.set(speed);
	}
	
	/**
	 * Get the current angle of the arm 
	 * @return double angle
	 */
	public double getArmAngle() {
		return pot.get();
	}

    public double getTargetPos() {
        return targetPos;
    }

    public void setTargetPos(double _targetPos) {
        targetPos = _targetPos;
    }
	
}
