package org.usfirst.frc.team1719.robot.subsystems.logical;

import org.usfirst.frc.team1719.mockhardware.MockScaledPotentiometer;
import org.usfirst.frc.team1719.mockhardware.MockSpeedController;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


public class LogicalArmTest {
	MockSpeedController motor;
	MockScaledPotentiometer pot;
	
	LogicalArm logicalArm;
	
	public void setup() {
		motor = new MockSpeedController();
		pot = new MockScaledPotentiometer();
		
		logicalArm = new LogicalArm(motor, pot);
		
	}
	
	@Test
	public void testLogicalArm() {
		setup();
		assertTrue(motor.currentSpeed == 0);
		assertTrue(logicalArm.getArmAngle() == pot.get());
		assertTrue(logicalArm.getTargetPos() == pot.get());
	}
	
	@Test
	public void testMove() {
		setup();
		double goodSpeeds[] = {-1, -0.5, 0, 0.5, 1};
		for (double speed : goodSpeeds) {
			logicalArm.move(speed);
			pot.setReading(-1);
			assertTrue(motor.currentSpeed == speed);
			assertTrue(logicalArm.getArmAngle() == -1);
		}
		
		double badSpeeds[] = {-2, 2};
		for (double speed : badSpeeds) {
			logicalArm.move(speed);
			assertTrue( (motor.get() == -1) || (motor.get() == 1));
		}
	}
}
