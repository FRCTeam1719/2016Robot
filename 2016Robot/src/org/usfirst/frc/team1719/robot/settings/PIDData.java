package org.usfirst.frc.team1719.robot.settings;

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
