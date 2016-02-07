package org.usfirst.frc.team1719.robot.subsystems;


import java.util.stream.DoubleStream;

import org.usfirst.frc.team1719.robot.settings.PIDData;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class FlyWheel extends Subsystem {
	
	Spark motor;
	Encoder encoder;
	
	double errors[] = new double[30];
	
	PIDData pidData;
	double previousError = 0;
	double output = 0;
	
	double integral = 0;
	double derivative = 0;
	
	final double ONEROTATION = 1.57075;
	final double TOLERANCE_FEET_PER_SECOND = 1;
	
	public FlyWheel(Spark controller, Encoder enc, PIDData pidData) {
		
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
	//spin at desired feet per second
	public void spin(double feetPerSecond) {
		double desiredSpeed = feetPerSecond;
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
		
		if(Math.abs(error) < TOLERANCE_FEET_PER_SECOND)
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
		//TODO PID is disabled until custom encoders are setup
		//motor.set(output);
		motor.set(1);
		for (int i = (errors.length - 1); i >= 0; i--) {                
		    errors[i+1] = errors[i];
		}
		errors[0] = error;
		
		previousError = error;
		System.out.println("Output: " + output);
		System.out.println("Error: " + error);
		System.out.println("kP: "+ pidData.kP);
	}
	
	public boolean isStabilized(double tolerance)
	{
		return (DoubleStream.of(errors).sum()/30)>tolerance;
	}
	
	public double getRate() {
		return encoder.getRate();
	}
	
}
