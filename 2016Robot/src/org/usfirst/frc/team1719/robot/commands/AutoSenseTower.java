package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.sensors.TargetVision;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Command to determine angle needed to make a shot
 */
public class AutoSenseTower extends Command {

    private static final double MIN_DISTANCE_FT = 2.0D;
    private static final double MAX_DISTANCE_FT = 10.0D;

    
    boolean done;
    
    public AutoSenseTower() {
        // Use requires() here to declare subsystem dependencies
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        done = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Gather vision data 
        TargetVision.TargetPos pos = TargetVision.detect();
        if(pos == null) {
            //We didn't find the target, so try again
            return;
        }
        //Angle for turning to the goal
        SmartDashboard.putNumber("TurnToAngleParam", -pos.azimuth);
        
        double dist1 = Math.max(pos.distance - MAX_DISTANCE_FT, 0.0D);
        double dist2 = Math.min(pos.distance - MIN_DISTANCE_FT, 0.0D);
        SmartDashboard.putNumber("MoveDistParam", (dist1 > 0.0D) ? dist1 : dist2);
        double necessaryAngle = calcAngleFromDistance(pos.distance);
        SmartDashboard.putNumber("MoveArmParam", necessaryAngle);
        done = true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
        done = false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        done = false;
    }
    /**
     *  Calculate the shooting angle from our distance based on our model
     */
    private double calcAngleFromDistance(double distance){
    	//TODO Actually do stuff here
    	return distance;
    }
    
}
