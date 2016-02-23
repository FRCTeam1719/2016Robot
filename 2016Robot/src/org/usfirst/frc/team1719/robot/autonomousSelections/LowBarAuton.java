package org.usfirst.frc.team1719.robot.autonomousSelections;

import org.usfirst.frc.team1719.robot.commands.MoveForwards;

//github.com/FRCTeam1719/2016Robot.git
import edu.wpi.first.wpilibj.command.CommandGroup;

public class LowBarAuton extends CommandGroup {	
	
	public LowBarAuton() {
		addSequential(new MoveForwards(4.5, 0.5));
	}

}
