package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.sensors.TargetVision;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutoSenseTower extends Command {

    private static final double MIN_DISTANCE_FT = 2.0D;
    private static final double MAX_DISTANCE_FT = 10.0D;
    private static final double WHEEL_RADIUS_FT = 0.5D;
    private static final double M_PER_FT = 0.3048D;
    private static final double V_0_Y = 6.244389482D;
    private static final double T_TO_APEX = 0.6422809124D;
    private static final double MASS = 0.2954545455D;
    private static final double K_DRAG = 0.05834736622D;
    private static final double VELOCITY_MAX = 13.5D;
    private static final double HACKY_NUMBER_A = 11080.0D;
    private static final double HACKY_NUMBER_B = -3067D;
    private static final double HACKY_NUMBER_C = 339.7D;
    private static final double HACKY_NUMBER_D = -18.76D;
    private static final double HACKY_NUMBER_E = 0.5166D;
    private static final double HACKY_NUMBER_F = -0.005665D;
    
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
        TargetVision.TargetPos pos = TargetVision.detect();
        if(pos == null) {
            //try to find target
            return;
        }
        SmartDashboard.putNumber("TurnToAngleParam", -pos.azimuth);
        double dist1 = Math.max(pos.distance - MAX_DISTANCE_FT, 0.0D);
        double dist2 = Math.min(pos.distance - MIN_DISTANCE_FT, 0.0D);
        SmartDashboard.putNumber("MoveDistParam", (dist1 > 0.0D) ? dist1 : dist2);
        double distance_m = M_PER_FT * pos.distance;
        double firingAltitude, firingVelocity;
        firingAltitude = firingVelocity = 0; // add calculations for altitude/velocity
        double v_0_x = MASS * (Math.exp(K_DRAG * distance_m / MASS) - 1.0D) / (K_DRAG * T_TO_APEX);
        firingVelocity = Math.sqrt(v_0_x * v_0_x + V_0_Y * V_0_Y);
        firingAltitude = (180.0D / Math.PI) * Math.atan(V_0_Y / v_0_x);
        if(firingVelocity > VELOCITY_MAX) {
            // Hack -- deal with excessive distances by interpolation between estimated points using
            // a quintic curve fit because actually solving the equation is (nigh) impossible.
            firingAltitude = HACKY_NUMBER_A * Math.pow(pos.distance, 5.0D) + HACKY_NUMBER_B * Math.pow(pos.distance, 4.0D)
                    + HACKY_NUMBER_C * Math.pow(pos.distance, 3.0D) + HACKY_NUMBER_D * Math.pow(pos.distance, 2.0D)
                    + HACKY_NUMBER_E * pos.distance + HACKY_NUMBER_F;
            firingVelocity = VELOCITY_MAX;
        }
        SmartDashboard.putNumber("MoveArmParam", firingAltitude);
        SmartDashboard.putNumber("FlywheelParam", firingVelocity / (M_PER_FT * WHEEL_RADIUS_FT * 2.0D * Math.PI));
        done = true;
        return;
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
