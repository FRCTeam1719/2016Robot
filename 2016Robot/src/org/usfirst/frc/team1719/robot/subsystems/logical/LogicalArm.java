package org.usfirst.frc.team1719.robot.subsystems.logical;

import org.usfirst.frc.team1719.robot.sensors.IScaledPotentiometer;

import edu.wpi.first.wpilibj.SpeedController;

public class LogicalArm implements IArm {
	
	SpeedController motor;
	private IScaledPotentiometer pot;
	
	private double targetPos = 0;
	
	public LogicalArm(SpeedController motor, IScaledPotentiometer pot) {
		this.motor = motor;
		this.pot = pot;
		targetPos = this.pot.get();
	}
	
	@Override
	public void move(double speed) {
		motor.set(speed);
	}

	@Override
	public double getArmAngle() {
		return pot.get();
	}

	@Override
	public double getTargetPos() {
		return targetPos;
	}

	@Override
	public void setTargetPos(double targetPos) {
		this.targetPos = targetPos;
	}

}
