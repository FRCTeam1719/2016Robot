package org.usfirst.frc.team1719.robot.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.usfirst.frc.team1719.mockhardware.MockSpeedController;
import org.usfirst.frc.team1719.mockhardware.MockTimer;
import org.usfirst.frc.team1719.robot.MockRobot;
import org.usfirst.frc.team1719.robot.subsystems.logical.IDualShooter;
import org.usfirst.frc.team1719.robot.subsystems.logical.LogicalDualShooter;
import org.usfirst.frc.team1719.robot.subsystems.logical.LogicalFlyWheel;

public class ManualShootTest {

	private MockRobot robot = new MockRobot();
	private MockSpeedController leftFlyWheelMotor = new MockSpeedController();
	private MockSpeedController rightFlyWheelMotor = new MockSpeedController();
	private MockSpeedController leftInnerMotor = new MockSpeedController();
	private MockSpeedController rightInnerMotor = new MockSpeedController();
	private MockSpeedController[] motors = 
		{leftFlyWheelMotor, rightFlyWheelMotor, leftInnerMotor, rightInnerMotor};
	private LogicalFlyWheel leftFlyWheel = new LogicalFlyWheel(leftFlyWheelMotor);
	private LogicalFlyWheel rightFlyWheel = new LogicalFlyWheel(rightFlyWheelMotor);
	private LogicalDualShooter shooter = new 
			LogicalDualShooter(leftFlyWheel, rightFlyWheel, leftInnerMotor, rightInnerMotor);
	private ManualShoot command = new ManualShoot(shooter, robot);
	private MockTimer timer = robot.getTimer("shootTimer");
	
	@Test
	public void testInitialize() {
		command.initialize();
		//Check all of our default states are good
		assertTrue(command.getCurrentState() == ManualShoot.ShooterState.PREP);
		assertFalse(timer.running);
		assertTrue(timer.time == 0);
		for(MockSpeedController motor: motors){
			assertTrue(motor.currentSpeed == 0);
		}
	}

	@Test
	public void testExecute() {
		command.initialize();
		assertFalse(command.isDone());
		command.execute();
		assertFalse(command.isDone());
		//Shooter should be in intake mode
		assertTrue(shooter.innerWheelMode == IDualShooter.spinMode.INTAKE);
		//Timer should be enabled
		assertTrue(timer.running);
		assertTrue(command.getCurrentState() == ManualShoot.ShooterState.SECURING_BALL);
		//Command shouldn't advance
		command.execute();
		assertFalse(command.isDone());
		assertTrue(command.getCurrentState() == ManualShoot.ShooterState.SECURING_BALL);
		timer.time = command.PREP_WAIT_TIME-0.1;
		command.execute();
		assertTrue(command.getCurrentState() == ManualShoot.ShooterState.SECURING_BALL);
		//Now advance the command
		timer.time  = command.PREP_WAIT_TIME+0.1;
		command.execute();
		assertFalse(command.isDone());
		//Inner motors should stop
		assertTrue(shooter.innerWheelMode == IDualShooter.spinMode.STOP);
		//Timer should be stopped & reset
		assertFalse(timer.running);
		assertTrue(timer.time == 0);
		//Outer motors should be revving 
		assertTrue(shooter.flyWheelMode == IDualShooter.spinMode.EJECT);
		//Mode should change
		assertTrue(command.getCurrentState() == ManualShoot.ShooterState.REV_SHOOTER);
		robot.oi.fireButton = false;
		command.execute();
		assertFalse(command.isDone());
		assertTrue(command.getCurrentState() == ManualShoot.ShooterState.REV_SHOOTER);
		//Wheels should be running
		assertTrue(shooter.flyWheelMode == IDualShooter.spinMode.EJECT);
		//Mode should change
		robot.oi.fireButton = true;
		command.execute();
		assertFalse(command.isDone());
		robot.oi.fireButton = false;
		assertTrue(command.getCurrentState() == ManualShoot.ShooterState.EJECT_BALL);
		command.execute();
		assertFalse(command.isDone());
		//Time should have started
		assertTrue(timer.running);
		//All wheels should be running
		assertTrue((shooter.flyWheelMode == IDualShooter.spinMode.EJECT) && 
				(shooter.innerWheelMode == IDualShooter.spinMode.EJECT));
		//State should change
		assertTrue(command.getCurrentState() == ManualShoot.ShooterState.WAITING_FOR_END);
		command.execute();
		assertFalse(command.isDone());
		//Things should still be spinning 
		assertTrue((shooter.flyWheelMode == IDualShooter.spinMode.EJECT) && 
				(shooter.innerWheelMode == IDualShooter.spinMode.EJECT));
		timer.time = command.SHOOT_WAIT_TIME-0.1;
		command.execute();
		assertFalse(command.isDone());
		assertTrue((shooter.flyWheelMode == IDualShooter.spinMode.EJECT) && 
				(shooter.innerWheelMode == IDualShooter.spinMode.EJECT));
		timer.time = command.SHOOT_WAIT_TIME+0.1;
		command.execute();
		assertTrue(command.isDone());
		//Things should stop
		assertFalse(timer.running);
		assertTrue((shooter.flyWheelMode == IDualShooter.spinMode.STOP) && 
				(shooter.innerWheelMode == IDualShooter.spinMode.STOP));
	}

	@Test
	public void testEnd() {
		IDualShooter.spinMode[] modes = 
			{IDualShooter.spinMode.EJECT, IDualShooter.spinMode.INTAKE,IDualShooter.spinMode.STOP};
		for(int i=0;i<modes.length;i++){
			for(int j=0;j<modes.length;j++){
				shooter.spin(modes[i]);
				shooter.runInnerMotors(modes[j]);
				command.end();
				assertTrue((shooter.flyWheelMode == IDualShooter.spinMode.STOP) && 
						(shooter.innerWheelMode == IDualShooter.spinMode.STOP));
			}
		}
	}

	@Test
	public void testInterrupted() {
		IDualShooter.spinMode[] modes = 
			{IDualShooter.spinMode.EJECT, IDualShooter.spinMode.INTAKE,IDualShooter.spinMode.STOP};
		for(int i=0;i<modes.length;i++){
			for(int j=0;j<modes.length;j++){
				shooter.spin(modes[i]);
				shooter.runInnerMotors(modes[j]);
				command.end();
				assertTrue((shooter.flyWheelMode == IDualShooter.spinMode.STOP) && 
						(shooter.innerWheelMode == IDualShooter.spinMode.STOP));
			}
		}
	}

}
