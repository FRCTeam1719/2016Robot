package org.usfirst.team1719.autonomousSelections;

import org.usfirst.frc.team1719.robot.commands.MoveForwards;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LowBarAuton extends CommandGroup {	
	
	public LowBarAuton() {
		addSequential(new MoveForwards(4.5, 0.3));
	}

}
