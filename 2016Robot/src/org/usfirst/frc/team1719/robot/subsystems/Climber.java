package org.usfirst.frc.team1719.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
/**
 * Climber Subsystem
 * Controlled by a Spark Motor
 * @author kthami
 *
 */

public class Climber extends Subsystem {
	
	
	Spark leftTapeMotor;
	Spark rightTapeMotor;
	Spark winchMotor;
	
	public Climber(Spark winchMotor) {
		
		this.winchMotor = winchMotor;
	}
	@Override
	protected void initDefaultCommand() {
		
	}

	
	public void winchUp() {
		winchMotor.set(0.7);
	}
	
	public void winchDown() {
		winchMotor.set(-0.7);
	}
	
	public void stop() {
		winchMotor.set(0);
	}
	
}
		// TODO Auto-generated method stub
		


