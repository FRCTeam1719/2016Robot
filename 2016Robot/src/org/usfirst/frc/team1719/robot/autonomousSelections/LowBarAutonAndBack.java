package org.usfirst.frc.team1719.robot.autonomousSelections;

import org.usfirst.frc.team1719.robot.commands.MoveArmToPos;
import org.usfirst.frc.team1719.robot.commands.MoveForwards;
import org.usfirst.frc.team1719.robot.commands.UseArmPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Run the Low Bar Auton and then attempt to come back through for easy scoring
 */
public class LowBarAutonAndBack extends CommandGroup {
    
    public  LowBarAutonAndBack() {
    	addSequential(new LowBarAuton());
    	addSequential(new MoveArmToPos(100.6));
    	addSequential(new UseArmPID());
    	addSequential(new MoveForwards(1.5, -1));
    }
}
