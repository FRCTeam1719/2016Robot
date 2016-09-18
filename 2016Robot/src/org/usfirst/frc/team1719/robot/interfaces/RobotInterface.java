package org.usfirst.frc.team1719.robot.interfaces;

import org.usfirst.frc.team1719.robot.OI;

import edu.wpi.first.wpilibj.Timer.Interface;

public interface RobotInterface {

	public OI getOi();
	public TestableDashboard getDashboard();
	public Interface getSystemTimer(String name);

}
