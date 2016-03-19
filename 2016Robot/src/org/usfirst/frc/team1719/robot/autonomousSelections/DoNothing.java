package org.usfirst.frc.team1719.robot.autonomousSelections;

import org.usfirst.frc.team1719.robot.commands.MoveArmToPos;
import org.usfirst.frc.team1719.robot.commands.UseArm;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DoNothing extends CommandGroup {

	public DoNothing(){
		addSequential(new MoveArmToPos(0));
		addParallel(new UseArm());
	}

}
