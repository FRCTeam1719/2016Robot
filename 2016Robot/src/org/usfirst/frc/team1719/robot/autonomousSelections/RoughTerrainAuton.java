package org.usfirst.frc.team1719.robot.autonomousSelections;

import org.usfirst.frc.team1719.robot.commands.MoveForwards;
import org.usfirst.frc.team1719.robot.commands.TurnToAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RoughTerrainAuton extends CommandGroup {
	
	public RoughTerrainAuton() {
		addSequential(new MoveForwards(2000, 0.7));
		addSequential(new TurnToAngle(0, false));
	}

}
