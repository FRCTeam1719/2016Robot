package org.usfirst.frc.team1719.robot.subsystems;

import org.usfirst.frc.team1719.robot.settings.PIDData;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FlyWheel extends Subsystem {
	
	Talon motor;
	Encoder encoder;
	
	PIDData pidData;
	double previousError = 0;
	double output = 0;
	
	double integral = 0;
	double derivative = 0;
	
	final double ONEROTATION = 256;
	
	public FlyWheel(Talon controller, Encoder enc, PIDData pidData) {
		
		this.pidData = pidData;
		motor = controller;
		encoder = enc;
		encoder.reset();
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	public void reset(){
		encoder.reset();
		output = 0;
	}

	public void spin(double rotPerSecond) {
		double desiredSpeed = ONEROTATION * rotPerSecond;
		pidData.kP = SmartDashboard.getNumber("Right flywheel kP: ");
		pidData.kI = SmartDashboard.getNumber("Right flywheel kI: ");
		pidData.kD = SmartDashboard.getNumber("Right flywheel kD: ");

		if (desiredSpeed == 0) {
			motor.set(0);
			return;
		}
		double currentSpeed = encoder.getRate();
		double error = desiredSpeed - currentSpeed;
		integral += error;
		derivative = error - previousError;
		
		if(Math.abs(error) < 100)
		{
			integral = 0;
		}
		
		output += (error * pidData.kP) + (integral * pidData.kI) + (derivative * pidData.kD);
		
		if (output > 1) {
			output = 1;
		}
		else if (output < -1) {
			output = -1;
		}
		
		motor.set(output);
		
		previousError = error;
		System.out.println("Output: " + output);
		System.out.println("Error: " + error);
		System.out.println("kP: "+ pidData.kP);
	}
	
	public double getRate() {
		return encoder.getRate();
	}
	
}
