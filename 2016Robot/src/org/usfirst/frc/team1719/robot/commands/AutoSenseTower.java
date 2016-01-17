package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.sensors.TargetVision;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoSenseTower extends Command {

    private static final double MIN_DISTANCE_FT = 2.0D;
    private static final double MAX_DISTANCE_FT = 10.0D;
    private static final double TARGET_HEIGHT_FT = 7.583D;
    private static final double AZIMUTH_ACC_DEG = 10.0D;
    private static final double ANGLE_ACC_DEG = 10.0D;
    
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
        System.out.println("Begin AutoSenseTower loop");
        TargetVision.TargetPos pos = TargetVision.detect();
        if(pos == null) {
            //try to find target
            return;
        }
        if((pos.distance > MIN_DISTANCE_FT) && (pos.distance < MAX_DISTANCE_FT) && (pos.azimuth < AZIMUTH_ACC_DEG) && (pos.targetAngle < ANGLE_ACC_DEG)) {
            Robot.weapon.aimAndFire(pos.distance, TARGET_HEIGHT_FT + 0.9D);
            done = true;
            return;
        }
        
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
}
