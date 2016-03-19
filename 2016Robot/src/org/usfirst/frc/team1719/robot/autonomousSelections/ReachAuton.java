package org.usfirst.frc.team1719.robot.autonomousSelections;

import org.usfirst.frc.team1719.robot.commands.MoveForwards;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ReachAuton extends CommandGroup{

	public ReachAuton(){
		addSequential(new MoveForwards(1.5, 1));
	}
}
