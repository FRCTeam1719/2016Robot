package org.usfirst.frc.team1719.robot.autonomousSelections;

import org.usfirst.frc.team1719.robot.commands.MoveArmToPos;
import org.usfirst.frc.team1719.robot.commands.MoveForwards;
import org.usfirst.frc.team1719.robot.commands.UseArm;
import org.usfirst.frc.team1719.robot.commands.Wait;

//github.com/FRCTeam1719/2016Robot.git
import edu.wpi.first.wpilibj.command.CommandGroup;

public class LowBarAuton extends CommandGroup {	
	
	public LowBarAuton() {
		addSequential((new MoveArmToPos(90)));
		addParallel(new UseArm());
		addSequential(new Wait(1.5));
		addSequential(new MoveArmToPos(100.6));
		addParallel(new UseArm());
		addSequential(new Wait(0.5));
		addSequential(new MoveForwards(1.5,1));
		addSequential(new MoveArmToPos(30));
		addParallel(new UseArm());

	}

}
