package org.usfirst.frc.team1719.robot.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

public class AutoScalingPotentiometer extends ScaledPotentiometer{

	private double offset;
	
	
	public AutoScalingPotentiometer(AnalogInput channel, double scale, double defaultPosition) {
		super(channel, 360, 0);
		double initState = super.get();
		//Set the offset
		offset = -1 * (initState - defaultPosition);
		System.out.println("initState: "+initState); 
		System.out.println("initRawState: "+super.getRaw());
		System.out.println("defaultPosition: "+defaultPosition);
		System.out.println("offset: "+offset);
	}
	
	@Override
	public double get(){
		return super.get() + offset;
	}
	
	
	protected void setOffset(double offset){
		this.offset = offset;
	}
	
	public void scale(double currerntPoint){
		
	}
	
	

	
	
	
	
	
}
