package org.usfirst.frc.team1719.robot.sensors;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.ControllerPower;

/**
 * Extension of the AnalogPotentiometer that allows for easier debugging and increased expandability
 * @author aaron
 *
 */
public class ScaledPotentiometer extends AnalogPotentiometer{

	AnalogInput channel;
	double scale;
	double offset;
	
	public ScaledPotentiometer(AnalogInput channel, double scale, double offset) {
		super(channel, 1, 0);
		this.channel = channel;
		this.scale = scale;
		this.offset = offset;
	}
	
	public ScaledPotentiometer(int channel, double scale, double offset){
		this(new AnalogInput(channel), scale, offset);
	}
	
	public ScaledPotentiometer(AnalogInput channel, double scale){
		super(channel, scale);
		this.channel = channel;
	}
	
	public ScaledPotentiometer(AnalogInput channel){
		super(channel);
		this.channel = channel;
	}
	
	@Override
	public double get() {
	    return (channel.getVoltage() / ControllerPower.getVoltage5V()) * scale + offset;
	 }
	
	public double getRaw(){
		return channel.getVoltage() / ControllerPower.getVoltage5V();
	}
	
	protected void setOffset(double offset){
		this.offset = offset;
	}
	
	protected void setScale(double scale){
		this.scale = scale;
	}
	
	protected double getScale(){
		return scale;
	}
	
	

}
