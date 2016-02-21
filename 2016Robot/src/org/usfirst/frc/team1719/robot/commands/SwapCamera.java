package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SwapCamera extends Command {
    
    private static boolean camera = false;
    
    protected void initialize() {}

    protected void execute() {}

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
        RobotMap.camswap.set(camera = !camera);
    }

    protected void interrupted() {}
}
