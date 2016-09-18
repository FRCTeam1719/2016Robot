package org.usfirst.frc.team1719.robot.autonomousSelections;

import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.commands.MoveArmToPos;
import org.usfirst.frc.team1719.robot.commands.MoveForwards;
import org.usfirst.frc.team1719.robot.commands.ResetGyro;
import org.usfirst.frc.team1719.robot.commands.UseArmPID;
import org.usfirst.frc.team1719.robot.commands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RockWallAuton extends CommandGroup {

    public RockWallAuton() {
		addSequential(new ResetGyro());
		addSequential(new MoveArmToPos(60));
		addParallel(new UseArmPID(Robot.arm, Robot.robotData));
		addSequential(new Wait(1));
		addSequential(new MoveForwards(2.3, 1));
		//addSequential(new TurnToAngle(0, false));
//		addSequential(new MoveArmToPos(0));
		addParallel(new UseArmPID(Robot.arm, Robot.robotData));
		addSequential(new MoveArmToPos(30));
		addParallel(new UseArmPID(Robot.arm, Robot.robotData));

    }

}
