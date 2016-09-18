package org.usfirst.frc.team1719.robot.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.usfirst.frc.team1719.mockhardware.MockScaledPotentiometer;
import org.usfirst.frc.team1719.mockhardware.MockSpeedController;
import org.usfirst.frc.team1719.robot.MockRobot;
import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.subsystems.logical.LogicalArm;

/**
 *
 */
public class UseArmPIDTest {
	
	private MockRobot robot = new MockRobot();
	private MockSpeedController armMotor = new MockSpeedController();
	private MockScaledPotentiometer pot = new MockScaledPotentiometer();
	
	private LogicalArm arm = new LogicalArm(armMotor, pot);
	

	
	private UseArmPID armCommand = new UseArmPID(arm, robot);
	
	@Test
	public void testInitialize() {
		armCommand.initialize();
	}
	
	@Test
	public void testExecute() {
		armCommand.initialize();
		assertTrue(arm.getArmAngle() == arm.getTargetPos());
		assertFalse(armCommand.isFinished());
		robot.oi.setArmReading(0.5);
		armCommand.execute();
		assertTrue(armMotor.currentSpeed != 0);
		assertTrue(arm.getArmAngle() == arm.getTargetPos());
		pot.setReading(pot.get() + 1);
		robot.oi.setArmReading(0);
		assertTrue(arm.getArmAngle() != arm.getTargetPos());
	}
}
