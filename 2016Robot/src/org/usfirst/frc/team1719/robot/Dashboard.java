package org.usfirst.frc.team1719.robot;

import org.usfirst.frc.team1719.robot.interfaces.TestableDashboard;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Wrapper class for SmartDashboard
 * @author aaron
 *
 */
public class Dashboard extends SmartDashboard implements TestableDashboard{

	@Override
	public void _putBoolean(String key, boolean value) {
		super.putBoolean(key, value);
	}

	@Override
	public boolean _getBoolean(String key) {
		return super.getBoolean(key);
	}

	@Override
	public void _putNumer(String key, double value) {
		super.putNumber(key, value);
	}

	@Override
	public double _getNumber(String key) {
		return super.getNumber(key);
	}

	@Override
	public void _putString(String key, String value) {
		super.putString(key, value);
	}

	@Override
	public String _getString(String key) {
		return super.getString(key);
	}

}
