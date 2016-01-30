package org.usfirst.frc.team1719.robot.autonomousSelections;

import org.usfirst.frc.team1719.robot.commands.MoveForwards;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RampartsAuton extends CommandGroup {
    
    public  RampartsAuton() {
        addSequential(new MoveForwards(6, 0.4));
    }
}
