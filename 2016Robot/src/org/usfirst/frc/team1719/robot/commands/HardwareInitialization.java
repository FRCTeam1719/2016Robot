package org.usfirst.frc.team1719.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Command run at beginning on every mode once to setup hardware
 * @author aaron
 *
 */
public class HardwareInitialization extends CommandGroup{
	
	public HardwareInitialization(){
		addSequential(new InitializeCameraMount());
	}

}