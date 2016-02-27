package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.subsystems.DualShooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ManualShoot extends Command{

	
	private enum state {
		PREP,SECURING_BALL,REV_SHOOTER,EJECT_BALL,WAITING_FOR_END
	}
	
	final double SHOOT_WAIT_TIME = 2.5;
	final double PREP_WAIT_TIME = 0.2;
	private boolean done;
	private state currentState;
	private Timer shootTimer;
	private Timer prepTimer;
	
	public ManualShoot(){
		requires(Robot.shooter);
		shootTimer = new Timer();
		prepTimer = new Timer();
	}
	
	@Override
	protected void end() {
		//Verify that everything has stopped
		Robot.shooter.reset();
		//Reset the stabilization notification
		SmartDashboard.putBoolean("shooterStable", false);
	}

	@Override
	protected void execute() {
		//Check if we can move onto the next mode
		switch (currentState) {
		case PREP:
			//Run the intake motors so that the ball is secure
			Robot.shooter.runInnerMotors(DualShooter.spinMode.INTAKE);
			//Start the prep timer
			prepTimer.start();
			//Advance to the next state
			currentState = state.SECURING_BALL;
			break;
		case SECURING_BALL:		
			if(prepTimer.get() > PREP_WAIT_TIME){
				//End this state
				//Stop the intake wheels
				Robot.shooter.runInnerMotors(DualShooter.spinMode.STOP);
				//Stop the timer
				prepTimer.stop();
				//Start the next phase
				currentState = state.REV_SHOOTER;
				//Start the outer motors
				Robot.shooter.spin(DualShooter.spinMode.EJECT);
			}
			break;
		case REV_SHOOTER:
			//Update the dashboard on the state of the shooter
			SmartDashboard.putBoolean("shooterStable", Robot.shooter.isStabilized());
			if(Robot.oi.getFireButton()){
				//Move onto next stage
				currentState = state.EJECT_BALL;
			}
			break;
		case EJECT_BALL:
			//Shoot the ball & start a timer
			Robot.shooter.runInnerMotors(DualShooter.spinMode.EJECT);
			shootTimer.start();
			currentState = state.WAITING_FOR_END;
			break;
		case WAITING_FOR_END:
			if(shootTimer.get() > SHOOT_WAIT_TIME){
				//Stop the wheels and the timer
				Robot.shooter.reset();
				shootTimer.stop();
				//Mark that we are done
				done = true;
			}
			break;
		}
		
		
	}

	@Override
	protected void initialize() {
		//Reset all timers
		shootTimer.reset();
		prepTimer.reset();
		//Reset all states
		done = false;
		currentState = state.PREP;
	}

	@Override
	protected void interrupted() {
		//Stop everything
		Robot.shooter.reset();
		
	}

	@Override
	protected boolean isFinished() {
		return done;
	}

}
