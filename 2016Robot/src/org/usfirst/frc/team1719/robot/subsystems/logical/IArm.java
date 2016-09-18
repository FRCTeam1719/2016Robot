package org.usfirst.frc.team1719.robot.subsystems.logical;

public interface IArm extends LogicalSubsystem {

	public void move(double speed);
	public double getArmAngle();
	
	public double getTargetPos();
	public void setTargetPos(double targetPos);
	
}
