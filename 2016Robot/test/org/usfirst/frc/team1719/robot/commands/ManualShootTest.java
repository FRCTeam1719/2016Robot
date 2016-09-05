package org.usfirst.frc.team1719.robot.commands;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.usfirst.frc.team1719.mockhardware.MockSpeedController;
import org.usfirst.frc.team1719.mockhardware.MockTimer;
import org.usfirst.frc.team1719.robot.subsystems.logical.LogicalDualShooter;
import org.usfirst.frc.team1719.robot.subsystems.logical.LogicalFlyWheel;

import edu.wpi.first.wpilibj.SpeedController;

public class ManualShootTest {

	private SpeedController leftFlyWheelMotor = new MockSpeedController();
	private SpeedController rightFlyWheelMotor = new MockSpeedController();
	private SpeedController leftInnerMotor = new MockSpeedController();
	private SpeedController rightInnerMotor = new MockSpeedController();
	private SpeedController[] motors = 
		{leftFlyWheelMotor, rightFlyWheelMotor, leftInnerMotor, rightInnerMotor};
	private LogicalFlyWheel leftFlyWheel = new LogicalFlyWheel(leftFlyWheelMotor);
	private LogicalFlyWheel rightFlyWheel = new LogicalFlyWheel(rightFlyWheelMotor);
	private LogicalDualShooter shooter = new 
			LogicalDualShooter(leftFlyWheel, rightFlyWheel, leftInnerMotor, rightInnerMotor);
	private MockTimer timer = new MockTimer();
	private ManualShoot command = new ManualShoot(shooter, timer);
	
	@Test
	public void testInitialize() {
		command.initialize();
	}
	@Test
	public void testExecute() {
		fail("Not yet implemented");
	}
	@Test
	public void testIsFinished() {
		fail("Not yet implemented");
	}
	@Test
	public void testEnd() {
		fail("Not yet implemented");
	}
	@Test
	public void testInterrupted() {
		fail("Not yet implemented");
	}

}
