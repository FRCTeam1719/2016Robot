package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class MoveForwardsUntilWall extends Command {

	final double TOLERANCE_IN_CM = 4;
	double desiredDist;
	double timesDetected = 0;
	
	Timer resetTimer;
	
	double RESET_TIME = 0.2
			
			
			
			
			
			
			;
	
    public MoveForwardsUntilWall(double distToWall) {
        requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(withinTolerance(RobotMap.ultrasonic.getDistanceCM())){
			timesDetected++;
		}
		if(resetTimer.get() >= RESET_TIME){
			resetTimer.reset();
			timesDetected = 0;
		}
		if(timesDetected >=2){
			SmartDashboard.putBoolean("withinDistance", true);
		}else{
			SmartDashboard.putBoolean("withinDistance", false);
		}
		Robot.drive.operateDrive(0.1, 0.1);
    }
    
    private boolean withinTolerance(double distance){
		return Math.abs(distance - desiredDist) < TOLERANCE_IN_CM;
	}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return timesDetected >= 2;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
