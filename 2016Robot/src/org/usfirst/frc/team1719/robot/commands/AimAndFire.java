package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Using vision:
 * 		Turn to face the goal
 * 		Move within range
 * 		Aim the shooter
 * 		Fire the shooter
 */
public class AimAndFire extends CommandGroup {
    

	
    public  AimAndFire() {
    	
    	addParallel(new RevUpShooter());
        addSequential(new AutoSenseTower());
        addSequential(new TurnToAngle(0.0D));
        addSequential(new MoveForwards(0.0D));
        addSequential(new AutoSenseTower());
        addSequential(new MoveArmToPos(Robot.GET_VALUE_FROM_SMARTDASHBOARD));
        addSequential(new ShootBoulder(1,1));
    }
}
