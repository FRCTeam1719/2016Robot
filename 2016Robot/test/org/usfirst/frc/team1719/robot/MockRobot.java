package org.usfirst.frc.team1719.robot;

import java.util.Hashtable;

import org.usfirst.frc.team1719.mockhardware.MockDashboard;
import org.usfirst.frc.team1719.mockhardware.MockTimer;
import org.usfirst.frc.team1719.robot.interfaces.RobotInterface;
import org.usfirst.frc.team1719.robot.interfaces.TestableDashboard;

import edu.wpi.first.wpilibj.Timer.Interface;

public class MockRobot implements RobotInterface {

	public MockOI oi = new MockOI();
	public RealRobot robotData;
	public MockDashboard dashboard = new MockDashboard();
	private Hashtable<String, MockTimer> timers = new Hashtable<String, MockTimer>();
	
	@Override
	public OI getOi() {
		return oi;
	}

	@Override
	public TestableDashboard getDashboard() {
		return dashboard;
	}

	@Override
	public Interface getSystemTimer(String name) {
		MockTimer timer = new MockTimer();
		timers.put(name, timer);
		return timer;
	}
	
	public MockTimer getTimer(String name){
		return timers.get(name);
	}
	
	public void clearTimers(){
		timers.clear();
	}

}
