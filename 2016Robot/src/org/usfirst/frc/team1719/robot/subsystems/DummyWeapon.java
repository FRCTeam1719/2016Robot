package org.usfirst.frc.team1719.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DummyWeapon extends Subsystem implements IFireable {
    
    public DummyWeapon() {}
    
    @Override
    public void fire() {
        SmartDashboard.putBoolean("Fired?", true);
    }
    
    @Override
    protected void initDefaultCommand() {}

    @Override
    public void aimAndFire(double distance, double height) {
        this.fire();
    }
    
}
