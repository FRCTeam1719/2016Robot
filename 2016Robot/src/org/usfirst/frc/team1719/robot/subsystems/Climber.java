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
	
	
	Spark leftClimber;
	Spark rightClimber;
	
		public Climber(Spark leftClimber , Spark rightClimber){
		this.leftClimber = leftClimber;
		this.rightClimber = rightClimber;
	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	public void extend(double leftSpeed, double rightSpeed) {
		leftClimber.set(leftSpeed);
		rightClimber.set(rightSpeed);
	
	}	
	
	
	}
		// TODO Auto-generated method stub
		


