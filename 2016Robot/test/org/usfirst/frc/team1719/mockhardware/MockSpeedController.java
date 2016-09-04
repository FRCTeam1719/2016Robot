package org.usfirst.frc.team1719.mockhardware;

import edu.wpi.first.wpilibj.SpeedController;

public class MockSpeedController implements SpeedController{

	public double currentSpeed;
	
	public MockSpeedController(){
		currentSpeed = 0;
	}
	
	public void set(double newSpeed){
		currentSpeed = newSpeed;
	}

	@Override
	public void pidWrite(double output) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double get() {
		// TODO Auto-generated method stub
		return currentSpeed;
	}

	@Override
	public void set(double speed, byte syncGroup) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setInverted(boolean isInverted) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getInverted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopMotor() {
		// TODO Auto-generated method stub
		
	}

}
