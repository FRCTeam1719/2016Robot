package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
/**
 * 
 * Using vision angle the arm for shooting
 *
 */
public class DetermineArmAngle extends CommandGroup{

	public DetermineArmAngle(){
		addSequential(new AutoSenseTower());
		addSequential(new MoveArmToPos(Robot.GET_VALUE_FROM_SMARTDASHBOARD));
	}

}
