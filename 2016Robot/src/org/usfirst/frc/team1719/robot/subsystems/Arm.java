package org.usfirst.frc.team1719.robot.subsystems;

import org.usfirst.frc.team1719.robot.commands.UseArm;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

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
	
	public void move(double speed) {
		motor.set(speed);
	}
	
	public double getPos() {
		return pot.get();
	}
	
	
}
