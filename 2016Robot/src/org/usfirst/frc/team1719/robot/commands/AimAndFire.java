package org.usfirst.frc.team1719.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AimAndFire extends CommandGroup {
    
    public  AimAndFire() {
        addSequential(new AutoSenseTower());
        addSequential(new TurnToAngle(0.0D));
        addSequential(new MoveForwards(0.0D));
        addSequential(new AutoSenseTower());
        addParallel(new MoveArmToPos(-13.37D));
    }
}
