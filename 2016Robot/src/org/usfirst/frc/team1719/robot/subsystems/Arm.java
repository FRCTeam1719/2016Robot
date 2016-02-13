package org.usfirst.frc.team1719.robot.subsystems;

import org.usfirst.frc.team1719.robot.commands.UseArm;
import org.usfirst.frc.team1719.robot.settings.PIDData;

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
	double integral = 0;
	double derivative = 0;
	double previousError = 0;
	double output = 0;
	AnalogPotentiometer pot;
	PIDData pidData;
	double errors[] = new double[30];
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new UseArm());
		
	}
	
	public Arm(Spark motor, AnalogPotentiometer pot, PIDData pidData){
		this.pidData = pidData;
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
	public void stayOnTarget(double target){

		double currentAngle = getArmAngle();
		double error = target - currentAngle;
		integral += error;
		derivative = error - previousError;
		if (Math.abs(error) < 0) {
			integral = 0;
		}
		if(integral>.5/pidData.kI)
			integral = .5/pidData.kI;
			

		output += (error * pidData.kP) + (integral * pidData.kI) + (derivative * pidData.kD);

		if (output > 1) {
			output = 1;
		} else if (output < -1) {
			output = -1;
		}
		motor.set(1);
		if(error == 0){
			motor.set(0);
		}
			else{
				motor.set(output);
			}
		
		for (int i = (errors.length - 1); i > 0; i--) {                
		    errors[i] = errors[i-1];
		} 
		errors[0] = error;
		previousError = error;
		
	}
	
}
