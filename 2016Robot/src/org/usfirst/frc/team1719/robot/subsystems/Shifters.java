package org.usfirst.frc.team1719.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shifters extends Subsystem{
	boolean state = false;
	Solenoid shifter;
	public Shifters(Solenoid shifter){
		this.shifter = shifter;
		state = false;
	}
	
	public void toggleState(){
		shifter.set(!state);
		state = !state;
	}
	
	
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
