package org.usfirst.frc.team1719.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class TapeSubsystem extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	SpeedController leftMotor;
	SpeedController rightMotor;

	public TapeSubsystem(SpeedController leftMotor, SpeedController rightMotor) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
	}
    public void initDefaultCommand() {
        
    }
    
    public void extend() {
    	leftMotor.set(1);
    	rightMotor.set(-1);
    }
    
    public void retract() {
    	leftMotor.set(-1);
    	rightMotor.set(1);
    }
    
    public void stop() {
    	leftMotor.set(0);
    	rightMotor.set(0);
    }
}

