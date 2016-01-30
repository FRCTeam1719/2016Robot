package org.usfirst.team1719.autonomousSelections;

import org.usfirst.frc.team1719.robot.commands.MoveForwards;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RoughTerrainAuton extends CommandGroup {
	
	public RoughTerrainAuton() {
		addSequential(new MoveForwards(6, 0.5));
	}

}
