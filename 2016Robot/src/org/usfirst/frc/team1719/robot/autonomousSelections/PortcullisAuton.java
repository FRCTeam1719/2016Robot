package org.usfirst.frc.team1719.robot.autonomousSelections;

import org.usfirst.frc.team1719.robot.commands.MoveArmToPos;
import org.usfirst.frc.team1719.robot.commands.MoveForwards;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PortcullisAuton extends CommandGroup {
	
	final static double ARM_POS_GROUND = -15;
	final static double ARM_POS_RAISED = 20;
    
    public  PortcullisAuton() {
        addSequential(new MoveArmToPos(ARM_POS_GROUND));
        addSequential(new MoveForwards(3, 0.3));
        addSequential(new MoveArmToPos(ARM_POS_RAISED));
        addSequential(new MoveForwards(3, 0.3));
    }
}
