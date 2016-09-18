package org.usfirst.frc.team1719.robot.autonomousSelections;

import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.commands.MoveArmToPos;
import org.usfirst.frc.team1719.robot.commands.MoveForwards;
import org.usfirst.frc.team1719.robot.commands.UseArmPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ReachAuton extends CommandGroup{

	public ReachAuton(){
		addSequential(new MoveForwards(1, 1));
		addSequential(new MoveArmToPos(30));
		addParallel(new UseArmPID(Robot.arm, Robot.robotData));

	}
}
