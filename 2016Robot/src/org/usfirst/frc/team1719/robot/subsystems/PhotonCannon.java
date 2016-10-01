package org.usfirst.frc.team1719.robot.subsystems;

import org.usfirst.frc.team1719.robot.RobotMap;

import edu.wpi.first.wpilibj.Relay;
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
	public void update(){
		if(isOn){
			turnOn();
		}else{
			turnOff();
		}
	}
	
	public void turnOn() {
		// Flicker the photon cannon until we get to the right state
			System.out.println("TURNING ON");
			RobotMap.photonCannon.setDirection(Relay.Direction.kForward);// dim
//			RobotMap.photonCannon.set(false);// off
//			RobotMap.photonCannon.set(true);// blinking
//			RobotMap.photonCannon.set(false);// off
//			RobotMap.photonCannon.set(true);// on full power
	}



	public void turnOff() {
			System.out.println("TURNING OFF");
			RobotMap.photonCannon.setDirection(Relay.Direction.kReverse); // set off
	}

}
