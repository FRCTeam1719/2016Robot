package org.usfirst.frc.team1719.robot.autonomousSelections;

import org.usfirst.frc.team1719.robot.commands.MoveForwards;
import org.usfirst.frc.team1719.robot.commands.TurnToAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MoatAuton extends CommandGroup {
    
    public  MoatAuton() {
        addSequential(new MoveForwards(7, 0.4));
        addSequential(new TurnToAngle(0, false));
    }
}
