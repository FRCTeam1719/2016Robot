package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardAutoUpdate extends Command {
    
    @Override
    protected void initialize() {}
    
    @Override
    protected void execute() {
        SmartDashboard.putNumber("Arm Position", Robot.arm.getPos());
        SmartDashboard.putBoolean("Shooter Loaded?", !RobotMap.ballLimitSwitch.get());
        SmartDashboard.putBoolean("Shooter Charged?", Robot.shooter.isStabilized());
    }
    
    @Override
    protected boolean isFinished() {
        return false;
    }
    
    @Override
    protected void end() {}
    
    @Override
    protected void interrupted() {}
    
}
