package org.usfirst.frc.team1719.robot.autonomousSelections;

import org.usfirst.frc.team1719.robot.commands.MoveArmToPos;
import org.usfirst.frc.team1719.robot.commands.UseArmPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Do nothing command
 * Holds the arm stable at 0 degrees
 * Doesn't move the drive
 * @author aaron
 *
 */
public class DoNothing extends CommandGroup {

	public DoNothing(){
		addSequential(new MoveArmToPos(0));
		addParallel(new UseArmPID());
	}

}
