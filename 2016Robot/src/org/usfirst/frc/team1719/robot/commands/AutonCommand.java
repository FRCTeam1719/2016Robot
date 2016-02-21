package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.RobotMap;
import org.usfirst.frc.team1719.robot.autonomousSelections.ShootCommand;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Skeleton Autonomous command that simply outputs which auton we are using
 * 
 * @author aaroneline
 *
 */
public class AutonCommand extends CommandGroup{


	Command obstacleCommand;
	Command shootingCommand;
	boolean side;
	boolean shouldShoot;
	public AutonCommand(){
		obstacleCommand = (Command) SmartDashboard.getData("autoMode");
		addSequential(obstacleCommand);
		shouldShoot = SmartDashboard.getBoolean("shouldShoot");
		double obstaclePosition = SmartDashboard.getNumber("obstaclePosition");
		if(obstaclePosition<3){
			side = RobotMap.LEFT;
		}else if(obstaclePosition>3){
			side = RobotMap.RIGHT;
		}else{
			/*
			 * Either we got invalid input, or at position 3
			 * Either way, we can't shoot
			 * So override the driver station
			 */
			shouldShoot = false;
		}
		//Check if we need to shoot
		if(shouldShoot){
			//Assemble the command, based on which side of the field we are on 
			addSequential(new ShootCommand(side));
			
		}
		
		
		
		
		
		
	}
	

}
