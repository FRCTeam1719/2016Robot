package org.usfirst.frc.team1719.robot.subsystems.logical;

public interface IDualShooter extends LogicalSubsystem{

	public enum spinMode{
		EJECT,INTAKE,STOP
	}
	public final double FLYWHEEL_INTAKE_SPEED = 0.7;
	public final double FLYWHEEL_EJECT_SPEED = 1;
	public final double INNER_INTAKE_SPEED = 1;
	public final double INNER_EJECT_SPEED = 1;
	
	void spin(spinMode speed);
	void runInnerMotors(spinMode speed);
	void reset();
	boolean isStabilized();
	
}
