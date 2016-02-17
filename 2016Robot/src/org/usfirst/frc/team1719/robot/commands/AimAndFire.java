package org.usfirst.frc.team1719.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AimAndFire extends CommandGroup {
    
    public  AimAndFire() {
        addSequential(new AutoSenseTower());
        addSequential(new TurnToAngle(0.0D,true));
        addSequential(new MoveForwards(0.0D, 0.5D));
        addSequential(new AutoSenseTower());
        addSequential(new MoveArmToPos(-1337));
        addSequential(new Fire());
    }
}
