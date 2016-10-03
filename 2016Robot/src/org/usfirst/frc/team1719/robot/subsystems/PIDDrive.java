package org.usfirst.frc.team1719.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class PIDDrive extends Subsystem {

	PIDDriveSide left;
	PIDDriveSide right;
	
	public PIDDrive(SpeedController leftC, SpeedController rightC,
			Encoder leftE, Encoder rightE){
		left = new PIDDriveSide(leftC, leftE, "Left Drive Side");
		right = new PIDDriveSide(rightC, rightE, "Right Drive Side");
	}
	
	public void operateDrive(double leftSpeed, double rightSpeed){
		left.setSetpoint(leftSpeed);
		right.setSetpoint(rightSpeed);
	}
	
	public void stopHard(){
		left.stopHard();
		right.stopHard();
	}
	
	public void setPIDControllers(boolean state){
		left.setPIDController(state);
		right.setPIDController(state);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}
