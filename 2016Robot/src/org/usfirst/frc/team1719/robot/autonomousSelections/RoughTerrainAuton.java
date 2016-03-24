package org.usfirst.frc.team1719.robot.autonomousSelections;

import org.usfirst.frc.team1719.robot.commands.MoveArmToPos;
import org.usfirst.frc.team1719.robot.commands.MoveForwards;
import org.usfirst.frc.team1719.robot.commands.ResetGyro;
import org.usfirst.frc.team1719.robot.commands.UseArmPID;
import org.usfirst.frc.team1719.robot.commands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RoughTerrainAuton extends CommandGroup {
	
	public RoughTerrainAuton() {
		addSequential(new ResetGyro());
		addSequential(new MoveArmToPos(60));
		addParallel(new UseArmPID());
		addSequential(new Wait(1));
		addSequential(new MoveForwards(1.5, 1));
		addSequential(new MoveArmToPos(30));
		addParallel(new UseArmPID());

		//addSequential(new TurnToAngle(0, false));
		
	}

}
