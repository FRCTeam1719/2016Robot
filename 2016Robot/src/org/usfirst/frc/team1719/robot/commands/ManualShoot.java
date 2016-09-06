package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.interfaces.RobotInterface;
import org.usfirst.frc.team1719.robot.subsystems.DualShooter;
import org.usfirst.frc.team1719.robot.subsystems.logical.IDualShooter;
import edu.wpi.first.wpilibj.Timer.Interface;

public class ManualShoot extends TestableCommand{

	
	public static enum ShooterState {
		PREP,SECURING_BALL,REV_SHOOTER,EJECT_BALL,WAITING_FOR_END
	}
	
	final double SHOOT_WAIT_TIME = 2.5;
	final double PREP_WAIT_TIME = 0.2;
	private boolean done;
	private ShooterState currentState;
	//WPILib Timer Interface
	private Interface timer;
	private IDualShooter system;
	
	public ManualShoot(IDualShooter system, RobotInterface robot){
		super(system, robot);
		this.system = system;
		timer = robot.getSystemTimer("shootTimer");
	}
	
	
	@Override
	protected void end() {
		//Verify that everything has stopped
		system.reset();
		//Reset the stabilization notification
		dashboard._putBoolean("shooterStable", false);
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
			currentState = ShooterState.SECURING_BALL;
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
				currentState = ShooterState.REV_SHOOTER;
				//Start the outer motors
				system.spin(DualShooter.spinMode.EJECT);
			}
			break;
		case REV_SHOOTER:
			//Update the dashboard on the state of the shooter
			dashboard._putBoolean("shooterStable", system.isStabilized());
			if(robot.getOi().getFireButton()){
				//Move onto next stage
				currentState = ShooterState.EJECT_BALL;
			}
			break;
		case EJECT_BALL:
			//Shoot the ball & start a timer
			system.runInnerMotors(DualShooter.spinMode.EJECT);
			timer.start();
			currentState = ShooterState.WAITING_FOR_END;
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
		currentState = ShooterState.PREP;
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
	
	public ShooterState getCurrentState(){
		return currentState;
	}
	
	public boolean isDone(){
		return done;
	}

}
