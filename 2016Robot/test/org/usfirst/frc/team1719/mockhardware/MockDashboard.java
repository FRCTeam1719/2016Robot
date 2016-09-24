package org.usfirst.frc.team1719.mockhardware;

import java.util.Hashtable;

import org.usfirst.frc.team1719.robot.interfaces.TestableDashboard;

public class MockDashboard implements TestableDashboard {

	private Hashtable<String,Boolean> booleans = new Hashtable<String,Boolean>();
	private Hashtable<String,Double> numbers = new Hashtable<String,Double>();
	private Hashtable<String,String> strings = new Hashtable<String,String>();
	
	@Override
	public void _putBoolean(String key, boolean value) {
		booleans.put(key, value);
	}

	@Override
	public boolean _getBoolean(String key) {
		return booleans.get(key);
	}

	@Override
	public void _putNumber(String key, double value) {
		numbers.put(key, value);
	}

	@Override
	public double _getNumber(String key) {
		return numbers.get(key);
	}

	@Override
	public void _putString(String key, String value) {
		strings.put(key, value);
	}

	@Override
	public String _getString(String key) {
		return strings.get(key);
	}

}
