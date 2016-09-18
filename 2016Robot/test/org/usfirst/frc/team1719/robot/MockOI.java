package org.usfirst.frc.team1719.robot;

public class MockOI implements OI {

	public boolean fireButton = false;
	private double armReading;
	
	
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
		return armReading;
	}

	@Override
	public boolean getFireButton() {
		// TODO Auto-generated method stub
		return fireButton;
	}
	
	public void setArmReading(double newReading) {
		if (newReading < -1) {
			newReading = -1;
		}
		else if (newReading > 1) {
			newReading = 1;
		}
		
		armReading = newReading;
	}

}
