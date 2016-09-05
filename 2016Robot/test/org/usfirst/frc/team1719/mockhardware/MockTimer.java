package org.usfirst.frc.team1719.mockhardware;

import edu.wpi.first.wpilibj.Timer.Interface;

public class MockTimer implements Interface{

	public double time = 0;
	public boolean running = false;
	
	@Override
	public double get() {
		// TODO Auto-generated method stub
		return time;
	}

	@Override
	public void reset() {
		time = 0;
	}

	@Override
	public void start() {
		running = true;
	}

	@Override
	public void stop() {
		running = false;
	}

	@Override
	public boolean hasPeriodPassed(double period) {
		return false;
	}

}
