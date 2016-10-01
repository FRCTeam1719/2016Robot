package org.usfirst.frc.team1719.robot.autonomousSelections;

import org.usfirst.frc.team1719.robot.commands.MoveArmToPos;
import org.usfirst.frc.team1719.robot.commands.MoveForwards;
import org.usfirst.frc.team1719.robot.commands.UseArmPID;
import org.usfirst.frc.team1719.robot.commands.Wait;

//github.com/FRCTeam1719/2016Robot.git
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Moves the robot under the low bar
 * Lowers the arm, and then moves forward
 * @author aaron
 *
 */
public class LowBarAuton extends CommandGroup {	
	
	public LowBarAuton() {
		//Move the arm down a little bit first, so that we don't slam it into the ground
		addSequential((new MoveArmToPos(90)));
		addParallel(new UseArmPID());
		addSequential(new Wait(1.5));
		addSequential(new MoveArmToPos(100.6));
		addParallel(new UseArmPID());
		addSequential(new Wait(0.5));
		addSequential(new MoveForwards(1.5,1));
		addSequential(new MoveArmToPos(30));
		addParallel(new UseArmPID());

	}

}
