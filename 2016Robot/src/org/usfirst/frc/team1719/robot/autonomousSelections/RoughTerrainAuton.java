package org.usfirst.frc.team1719.robot.autonomousSelections;

import org.usfirst.frc.team1719.robot.commands.MoveArmToPos;
import org.usfirst.frc.team1719.robot.commands.MoveForwards;
import org.usfirst.frc.team1719.robot.commands.ResetGyro;
import org.usfirst.frc.team1719.robot.commands.UseArm;
import org.usfirst.frc.team1719.robot.commands.Wait;
import org.usfirst.frc.team1719.robot.commands.printCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RoughTerrainAuton extends CommandGroup {
	
	public RoughTerrainAuton() {
		addSequential(new ResetGyro());
		addSequential(new MoveArmToPos(-30));
		addParallel(new UseArm());
		addSequential(new Wait(1));
		addSequential(new MoveForwards(1.75, 1));
		addSequential(new MoveArmToPos(0));
		addParallel(new UseArm());
		addSequential(new printCommand("meme2"));
		//addSequential(new TurnToAngle(0, false));
		
	}

}
