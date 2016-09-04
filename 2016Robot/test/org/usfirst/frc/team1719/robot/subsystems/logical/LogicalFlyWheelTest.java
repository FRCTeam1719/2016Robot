package org.usfirst.frc.team1719.robot.subsystems.logical;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.usfirst.frc.team1719.mockhardware.MockSpeedController;

public class LogicalFlyWheelTest {

	MockSpeedController spark;
	LogicalFlyWheel test;
	
	public void setup(){
		spark = new MockSpeedController();
		test = new LogicalFlyWheel(spark);
	}
	
	@Test
	public void testLogicalFlyWheel() {
		setup();
		assertTrue(spark.currentSpeed == 0);
	}

	@Test
	public void testReset() {
		setup();
		test.reset();
		assertTrue(spark.currentSpeed == 0);
		double speeds[] = {-1,-0.5,0,0.5,1};
		for(double speed: speeds){
			test.spin(speed);
			test.reset();
			assertTrue(spark.currentSpeed == 0);
		}
	}

	@Test
	public void testSpin() {
		setup();
		double speeds[] = {-1,-0.5,0,0.5,1};
		for(double speed: speeds){
			test.spin(speed);
			assertTrue(spark.currentSpeed == speed);
		}
	}

}
