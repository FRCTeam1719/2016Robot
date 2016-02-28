package org.usfirst.frc.team1719.robot.autonomousSelections;

import org.usfirst.frc.team1719.robot.commands.MoveArmToPos;
import org.usfirst.frc.team1719.robot.commands.MoveForwards;
import org.usfirst.frc.team1719.robot.commands.TurnToAngle;
import org.usfirst.frc.team1719.robot.commands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RockWallAuton extends CommandGroup {

    public RockWallAuton() {
    	addSequential(new MoveArmToPos(-30));
    	addSequential(new Wait(1));
        addSequential(new MoveForwards(3, 0.7));
        addSequential(new TurnToAngle(0, false));
    }

}
