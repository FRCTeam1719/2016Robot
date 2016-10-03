package org.usfirst.frc.team1719.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class PIDDriveSide extends PIDSubsystem{

	SpeedController controller;
	Encoder encoder;
	
	public PIDDriveSide(SpeedController controller, Encoder encoder, String name){
		super(name, 1.0, 0.0, 0.0);
		setAbsoluteTolerance(0.2);
		getPIDController().setContinuous(false);
		this.controller = controller;
		this.encoder = encoder;
	}

	@Override
	protected double returnPIDInput() {
		return encoder.getRate();
	}

	@Override
	protected void usePIDOutput(double output) {
		controller.pidWrite(output);
	}

	@Override
	protected void initDefaultCommand() {
	}
	
	
	
}
