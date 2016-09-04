package org.usfirst.frc.team1719.robot.subsystems.logical;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.usfirst.frc.team1719.mockhardware.MockSpeedController;

public class LogicalDualShooterTest {

	private MockSpeedController leftFlyWheelMotor;
	private MockSpeedController rightFlyWheelMotor;
	private MockSpeedController leftInnerMotor;
	private MockSpeedController rightInnerMotor;
	private MockSpeedController[] motors = new MockSpeedController[4];
	private LogicalFlyWheel leftFlyWheel;
	private LogicalFlyWheel rightFlyWheel;
	private LogicalDualShooter shooter;
	private IDualShooter.spinMode[] modes = 
		{IDualShooter.spinMode.EJECT, IDualShooter.spinMode.INTAKE, IDualShooter.spinMode.STOP};
	private double[] flyWheelsSpeeds = 
		{IDualShooter.FLYWHEEL_EJECT_SPEED, IDualShooter.FLYWHEEL_INTAKE_SPEED, 0};
	private double[] innerMotorSpeeds = 
		{IDualShooter.INNER_EJECT_SPEED, IDualShooter.INNER_INTAKE_SPEED, 0};

	private void setup(){
		leftFlyWheelMotor = new MockSpeedController();
		rightFlyWheelMotor = new MockSpeedController();
		leftInnerMotor = new MockSpeedController();
		rightInnerMotor = new MockSpeedController();
		motors[0] = leftFlyWheelMotor;
		motors[1] = rightFlyWheelMotor;
		motors[2] = leftInnerMotor;
		motors[3] = rightInnerMotor;
		leftFlyWheel = new LogicalFlyWheel(leftFlyWheelMotor);
		rightFlyWheel = new LogicalFlyWheel(rightFlyWheelMotor);
		shooter = new 
				LogicalDualShooter(leftFlyWheel,rightFlyWheel,leftInnerMotor,rightInnerMotor);
	}
	
	@Test
	public void testSpin() {
		setup();
		for(int i=0;i<modes.length;i++){
			shooter.spin(modes[i]);
			assertTrue(Math.abs(leftFlyWheelMotor.currentSpeed) == flyWheelsSpeeds[i]);
			assertTrue(Math.abs(rightFlyWheelMotor.currentSpeed) == flyWheelsSpeeds[i]);
			if(modes[i]==IDualShooter.spinMode.INTAKE){
				assertTrue(leftFlyWheelMotor.currentSpeed < 0);
				assertTrue(rightFlyWheelMotor.currentSpeed > 0);
			}else if (modes[i]==IDualShooter.spinMode.EJECT){
				assertTrue(leftFlyWheelMotor.currentSpeed > 0);
				assertTrue(rightFlyWheelMotor.currentSpeed < 0);
			}
		}
	}

	@Test
	public void testRunInnerMotors() {
		setup();
		for(int i=0;i<modes.length;i++){
			shooter.runInnerMotors(modes[i]);
			assertTrue(Math.abs(leftInnerMotor.currentSpeed) == innerMotorSpeeds[i]);
			assertTrue(Math.abs(rightInnerMotor.currentSpeed) == innerMotorSpeeds[i]);
			if(modes[i]==IDualShooter.spinMode.INTAKE){
				assertTrue(leftInnerMotor.currentSpeed < 0);
				assertTrue(rightInnerMotor.currentSpeed > 0);
			}else if (modes[i]==IDualShooter.spinMode.EJECT){
				assertTrue(leftInnerMotor.currentSpeed > 0);
				assertTrue(rightInnerMotor.currentSpeed < 0);
			}
		}
	}

	@Test
	public void testReset() {
		setup();
		for(IDualShooter.spinMode flyMode:modes){
			for(IDualShooter.spinMode innerMode:modes){
				shooter.spin(flyMode);
				shooter.runInnerMotors(innerMode);
				shooter.reset();
				for(MockSpeedController motor:motors){
					assertTrue(motor.currentSpeed == 0);
				}
			}
		}
	}

}
