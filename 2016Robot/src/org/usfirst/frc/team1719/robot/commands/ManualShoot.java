package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.subsystems.DualShooter;
import org.usfirst.frc.team1719.robot.subsystems.logical.IDualShooter;

import edu.wpi.first.wpilibj.Timer.Interface;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ManualShoot extends TestableCommand{

	
	private enum state {
		PREP,SECURING_BALL,REV_SHOOTER,EJECT_BALL,WAITING_FOR_END
	}
	
	final double SHOOT_WAIT_TIME = 2.5;
	final double PREP_WAIT_TIME = 0.2;
	private boolean done;
	private state currentState;
	//WPILib Timer Interface
	private Interface timer;
	private IDualShooter system;
	
	public ManualShoot(IDualShooter system, Interface timer){
		super(system);
		this.system = system;
		this.timer = timer;
	}
	
	
	@Override
	protected void end() {
		//Verify that everything has stopped
		system.reset();
		//Reset the stabilization notification
		SmartDashboard.putBoolean("shooterStable", false);
	}

	@Override
	protected void execute() {
		//Check if we can move onto the next mode
		switch (currentState) {
		case PREP:
			//Run the intake motors so that the ball is secure
			system.runInnerMotors(DualShooter.spinMode.INTAKE);
			//Start the prep timer
			timer.start();
			//Advance to the next state
			currentState = state.SECURING_BALL;
			break;
		case SECURING_BALL:		
			if(timer.get() > PREP_WAIT_TIME){
				//End this state
				//Stop the intake wheels
				system.runInnerMotors(DualShooter.spinMode.STOP);
				//Stop the timer
				timer.stop();
				timer.reset();
				//Start the next phase
				currentState = state.REV_SHOOTER;
				//Start the outer motors
				system.spin(DualShooter.spinMode.EJECT);
			}
			break;
		case REV_SHOOTER:
			//Update the dashboard on the state of the shooter
			SmartDashboard.putBoolean("shooterStable", system.isStabilized());
			if(Robot.oi.getFireButton()){
				//Move onto next stage
				currentState = state.EJECT_BALL;
			}
			break;
		case EJECT_BALL:
			//Shoot the ball & start a timer
			system.runInnerMotors(DualShooter.spinMode.EJECT);
			timer.start();
			currentState = state.WAITING_FOR_END;
			break;
		case WAITING_FOR_END:
			if(timer.get() > SHOOT_WAIT_TIME){
				//Stop the wheels and the timer
				system.reset();
				timer.stop();
				timer.reset();
				//Mark that we are done
				done = true;
			}
			break;
		}
		
		
	}

	@Override
	protected void initialize() {
		//Reset all timers
		timer.reset();
		//Reset all states
		done = false;
		currentState = state.PREP;
	}

	@Override
	protected void interrupted() {
		//Stop everything
		system.reset();
		
	}

	@Override
	protected boolean isFinished() {
		return done;
	}

}
