package org.usfirst.frc.team1719.robot.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

public class AutoScalingPotentiometer extends ScaledPotentiometer{

	private double offset;
	
	
	public AutoScalingPotentiometer(AnalogInput channel, double scale, double defaultPosition) {
		super(channel, scale, 0);
		double initState = super.get();
		//Set the offset
	    offset = defaultPosition;
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
	
	public void scale(double currentPoint){
		double currentRawPoint = super.get();
		double newOffset = currentRawPoint - currentPoint;
		setOffset(newOffset);
		System.out.println(currentRawPoint);
		System.out.println(newOffset);
	}
	
	

	
	
	
	
	
}
