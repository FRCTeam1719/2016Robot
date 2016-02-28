package org.usfirst.frc.team1719.robot.autonomousSelections;

import org.usfirst.frc.team1719.robot.commands.MoveArmToPos;
import org.usfirst.frc.team1719.robot.commands.MoveForwards;
import org.usfirst.frc.team1719.robot.commands.Wait;

//github.com/FRCTeam1719/2016Robot.git
import edu.wpi.first.wpilibj.command.CommandGroup;

public class LowBarAuton extends CommandGroup {	
	
	public LowBarAuton() {
		addSequential(new MoveArmToPos(-30));
		addSequential(new Wait(1));
		addSequential(new MoveForwards(3, 0.5));
	}

}
