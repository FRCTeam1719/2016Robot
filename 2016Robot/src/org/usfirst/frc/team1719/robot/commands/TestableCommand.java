package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.OI;
import org.usfirst.frc.team1719.robot.interfaces.RobotInterface;
import org.usfirst.frc.team1719.robot.interfaces.TestableDashboard;
import org.usfirst.frc.team1719.robot.subsystems.logical.LogicalSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class TestableCommand extends Command{

	protected LogicalSubsystem system;
	protected RobotInterface robot;
	protected OI oi;
	protected TestableDashboard dashboard;
	
	public TestableCommand(LogicalSubsystem system, RobotInterface robot){
		this.system = system;
		try{
			requires((Subsystem) system);
		}catch(ClassCastException e){
			System.out.println("Non subsystem passed, is a test running?");
		}
		this.robot = robot;
		oi = robot.getOi();
		dashboard = robot.getDashboard();

	}
	
}
