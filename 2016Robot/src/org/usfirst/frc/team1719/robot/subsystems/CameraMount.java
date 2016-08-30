package org.usfirst.frc.team1719.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Controls and tracks position of a pan and tilt servo assembly
 * @author aaron
 *
 */
public class CameraMount extends Subsystem{

	private Servo azimuthServo;
	private Servo heightAngleServo;
	private final double SCALE_FACTOR = 100;
	
	public CameraMount(Servo azimuthServo, Servo heightAngleServo){
		this.azimuthServo = azimuthServo;
		this.heightAngleServo = heightAngleServo;
	}
	
	public void setAzimuth(double pos){
		azimuthServo.set(pos);
	}
	
	public void setHeightAngle(double pos){
		heightAngleServo.set(pos);
	}
	
	public double getAzimuth(){
		return azimuthServo.get() * SCALE_FACTOR;
	}

	public double getHeightAngle(){
		return azimuthServo.get() * SCALE_FACTOR;
	}

	@Override
	protected void initDefaultCommand() {
		//No default command
	}

}
