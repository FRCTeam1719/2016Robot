package org.usfirst.frc.team1719.robot.settings;
/**
 * Class for storing PID data for any system that needs to use a PID loop
 * @author aaroneline
 *
 */
public class PIDData {
	public double kP;
	public double kI;
	public double kD;
	
	public PIDData (double defaultKP, double defaultKI, double defaultKD){
		kP = defaultKP;
		kI = defaultKI;
		kD = defaultKD;
	}
	
	
}
