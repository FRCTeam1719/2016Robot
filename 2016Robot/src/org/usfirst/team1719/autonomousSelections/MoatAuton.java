package org.usfirst.team1719.autonomousSelections;

import org.usfirst.frc.team1719.robot.commands.MoveForwards;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MoatAuton extends CommandGroup {
    
    public  MoatAuton() {
        addSequential(new MoveForwards(7, 0.5));
    }
}
