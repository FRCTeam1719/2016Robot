package org.usfirst.frc.team1719.robot.autonomousSelections;

import org.usfirst.frc.team1719.robot.commands.MoveForwards;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RockWallAuton extends CommandGroup {

    public RockWallAuton() {
        addSequential(new MoveForwards(6, 0.5));
    }

}
