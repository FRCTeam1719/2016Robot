package org.usfirst.frc.team1719.robot.subsystems;

import org.usfirst.frc.team1719.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Turn on and off the Photon Cannon (its a flashlight)
 * 
 * @author a-bad-programmer
 *
 */
public class PhotonCannon extends Subsystem {
	public boolean isOn = false;

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

	
	public void turnOn() {
		// Flicker the photon cannon until we get to the right state
			RobotMap.photonCannon.set(true);// dim
			RobotMap.photonCannon.set(false);// off
			RobotMap.photonCannon.set(true);// blinking
			RobotMap.photonCannon.set(false);// off
			RobotMap.photonCannon.set(true);// on full power
			isOn = true;
	}



	public void turnOff() {
			RobotMap.photonCannon.set(false); // set off
			isOn = false;
	}

}
