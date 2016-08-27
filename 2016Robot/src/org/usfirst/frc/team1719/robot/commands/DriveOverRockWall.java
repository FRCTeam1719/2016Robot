package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveOverRockWall extends Command {

	static final int DRIVING_TO_WALL = 0;
	static final int ON_RAMP_GOING_UP = 1;
	static final int ON_WALL_GOING_UP = 2;
	static final int ON_WALL_GOING_DOWN = 3;
	static final int ON_RAMP_GOING_DOWN = 4;
	static final int OFF_WALL_GOING_FORWARDS = 5;
	static final int FINISHED = 6;
	int stage;
	
	Timer timeoutTimer;
    public DriveOverRockWall() {
        requires(Robot.drive);
        timeoutTimer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	stage = DRIVING_TO_WALL;
    	RobotMap.navX.reset();
    	timeoutTimer.start();
    	timeoutTimer.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (stage == DRIVING_TO_WALL) {
    		Robot.drive.operateDrive(-0.7, -0.7);
    		if (RobotMap.navX.getPitch() >= 2) {
    			stage = ON_RAMP_GOING_UP;
    			timeoutTimer.reset();
    			System.out.println("ON RAMP");
    		}
    	}
    	else if (stage == ON_RAMP_GOING_UP) {
    		Robot.drive.operateDrive(-0.9, -0.9);
    		if (RobotMap.navX.getPitch() >= 15) {
    			stage = ON_WALL_GOING_UP;
    			timeoutTimer.reset();
    		}
    	}
    	else if (stage == ON_WALL_GOING_UP) {
    		Robot.drive.operateDrive(-0.9, -0.9); 
    		if (RobotMap.navX.getPitch() <= -10) {
    			stage = ON_WALL_GOING_DOWN;
    			timeoutTimer.reset();
    		}
    	}
    	else if (stage == ON_WALL_GOING_DOWN) {
    		Robot.drive.operateDrive(-0.4, -0.4);
    		if (RobotMap.navX.getPitch() >= -5) {
    			stage = ON_RAMP_GOING_DOWN;
    			timeoutTimer.reset();
    		}
    	}
    	else if (stage == ON_RAMP_GOING_DOWN) {
    		Robot.drive.operateDrive(-0.3, -0.3);
    		if (Math.abs(RobotMap.navX.getPitch()) < 2) {
    			stage = OFF_WALL_GOING_FORWARDS;
    			timeoutTimer.reset();
    		}
    	}
    	else if (stage == OFF_WALL_GOING_FORWARDS) {
    		Robot.drive.operateDrive(-0.4, -0.4);
    		if (timeoutTimer.get() >= 0.7) {
    			stage = FINISHED;
    		}
    	}
    	System.out.println("navx: " + RobotMap.navX.getPitch());
    	System.out.println("stage: " + stage);
    	System.out.println("Finish: " + (stage == FINISHED));
    	System.out.println("Timer: " + timeoutTimer.get());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (stage == FINISHED || timeoutTimer.get() >= 3);
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("End called");
    	Robot.drive.operateDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
