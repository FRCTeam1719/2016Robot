package org.usfirst.frc.team1719.robot.subsystems;

import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.commands.UseArmPID;
import org.usfirst.frc.team1719.robot.sensors.ScaledPotentiometer;
import org.usfirst.frc.team1719.robot.subsystems.logical.IArm;
import org.usfirst.frc.team1719.robot.subsystems.logical.LogicalArm;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
/**
 * Arm subsystem. 
 * Controlled by a Spark motor controller w/ limit siwtches
 * Moves freely in one axis over the robot
 * Data on current position is obtained via a potentiometer
 * @author aaroneline
 *
 */
public class Arm extends Subsystem implements IArm {
	

	LogicalArm logicArm;
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new UseArmPID(this, Robot.robotData));
	}
	
	public Arm(Spark motor, ScaledPotentiometer pot) {
		logicArm = new LogicalArm(motor, pot);
	}
	
	/**
	 * @param speed to move at
	 */
	public void move(double speed) {
		logicArm.move(speed);
	}
	
	/**
	 * Get the current angle of the arm 
	 * @return double angle
	 */
	public double getArmAngle() {
		return logicArm.getArmAngle();
	}


    public double getTargetPos() {
        return logicArm.getTargetPos();
    }

    public void setTargetPos(double _targetPos) {
        logicArm.setTargetPos(_targetPos);
    }
	
}
