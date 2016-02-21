package org.usfirst.frc.team1719.robot.autonomousSelections;

import org.usfirst.frc.team1719.robot.RobotMap;
import org.usfirst.frc.team1719.robot.commands.MoveArmToPos;
import org.usfirst.frc.team1719.robot.commands.MoveForwards;
import org.usfirst.frc.team1719.robot.commands.TurnToAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RockWallAuton extends CommandGroup {

    public RockWallAuton() {
    	addSequential(new MoveArmToPos(-45));
    	RobotMap.gyro.reset();
        addSequential(new MoveForwards(6, 0.5));
        addSequential(new TurnToAngle(0, false));
    }

}
