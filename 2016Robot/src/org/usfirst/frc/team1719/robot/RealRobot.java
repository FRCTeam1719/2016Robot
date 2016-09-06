package org.usfirst.frc.team1719.robot;

import org.usfirst.frc.team1719.robot.interfaces.RobotInterface;
import org.usfirst.frc.team1719.robot.interfaces.TestableDashboard;
import org.usfirst.frc.team1719.robot.sensors.UTimer;

import edu.wpi.first.wpilibj.Timer.Interface;

public class RealRobot implements RobotInterface{

	@Override
	public OI getOi() {
		return Robot.oi;
	}

	@Override
	public TestableDashboard getDashboard() {
		return Robot.globalDashboard;
	}

	@Override
	public Interface getSystemTimer(String name) {
		//Just throw the name away, it's only used for testing
		return new UTimer();
	}

}
