package org.usfirst.frc.team1719.robot.autonomousSelections;

import org.usfirst.frc.team1719.robot.RobotMap;
import org.usfirst.frc.team1719.robot.commands.AutoSenseTower;
import org.usfirst.frc.team1719.robot.commands.MoveArmToPos;
import org.usfirst.frc.team1719.robot.commands.RevUpShooter;
import org.usfirst.frc.team1719.robot.commands.ShootBoulder;
import org.usfirst.frc.team1719.robot.commands.TurnToAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShootCommand extends CommandGroup{

	//TODO Tune this class
	
	
	final double TURN_ANGLE = 45D;
	final double ARM_ANGLE = -85;
	
	boolean side;
	public ShootCommand(boolean side){
		System.out.println("YOU ARE USING UNTUNED AUTO CLASSES");
		this.side = side;
		double angleToTurn = TURN_ANGLE;
		if(side==RobotMap.LEFT){
			angleToTurn = -TURN_ANGLE;
		}
		addParallel(new RevUpShooter());
		addSequential(new TurnToAngle(angleToTurn, true));
		addSequential(new AutoSenseTower());
		addSequential(new TurnToAngle(-1337D, true));
		addSequential(new AutoSenseTower());
		addSequential(new MoveArmToPos(ARM_ANGLE));
		addSequential(new ShootBoulder(1,1));
	}
	
	
}
