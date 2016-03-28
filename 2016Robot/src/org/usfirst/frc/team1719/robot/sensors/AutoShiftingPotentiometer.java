package org.usfirst.frc.team1719.robot.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * AutoShiftingPotentiometer implements a ScaledPotentiometer where the offset is dynamically shifted
 * This is to counteract a slipping potentiometer
 * 
 * @author aaron
 *
 */
public class AutoShiftingPotentiometer extends ScaledPotentiometer{

	
	/**
	 * The Pot will start at the same position every time the robot is turned on. 
	 * The output of the pot will be shifted to match that
	 * @param channel AnalogInput the pot is on
	 * @param scale multiplier for pot output
	 * @param defaultPosition default angle the pot is at
	 */
	public AutoShiftingPotentiometer(AnalogInput channel, double scale, double defaultPosition) {
		super(channel, 360, 0);
		double initState = super.get();
		//Set the offset
		double offset = -1 * (initState - defaultPosition);
		super.setOffset(offset);
		System.out.println("initState: "+initState); 
		System.out.println("initRawState: "+super.getRaw());
		System.out.println("defaultPosition: "+defaultPosition);
		System.out.println("offset: "+offset);
	}
	
	public AutoShiftingPotentiometer(int channel, double scale, double defaultPosition) {
		this(new AnalogInput(channel), scale, defaultPosition);
	}
	
	/**
	 * Adjusts the offset of the pot so that it currently reads what is passed 
	 * @param desiredReading 
	 */
	public void adjustShift(double desiredReading){
		double currentState = super.getRaw() * super.getScale();
		double newOffset = -1 * (currentState - desiredReading);
		super.setOffset(newOffset);
	}
	
	

	
	
	
	
	
}
