package org.usfirst.frc.team1719.robot.subsystems;

import org.usfirst.frc.team1719.robot.commands.UseClimber;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {
    
    private Spark motor;
    
    public Climber(Spark _motor) {
        motor = _motor;
    }

    public void initDefaultCommand() {
        setDefaultCommand(new UseClimber());
    } 
    
    public void run(double speed) {
        motor.set(speed);
    }
}

