package org.usfirst.frc.team1719.robot;

public class MockOI implements OI {

	public boolean fireButton = false;
	
	
	@Override
	public double getLeftDriveReading() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getRightDriveReading() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getArmReading() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getFireButton() {
		// TODO Auto-generated method stub
		return fireButton;
	}

}
