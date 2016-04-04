package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WithinDistance extends Command{

	private double desiredDistance;
	private final double TOLERANCE_IN_CM = 4;
	private double timesDetected;
	private double readings[] = new double[20];
	private Timer resetTimer;
	private final double RESET_TIME = 0.5;
	private final int REQUIRED_DETECTIONS = 2;
	
	
	
	
	NetworkTable table = NetworkTable.getTable("Test");
	
	public WithinDistance(double desiredDistance){
		this.desiredDistance = desiredDistance;
		resetTimer = new Timer();
	}
	
	
	
	@Override
	protected void initialize() {
		timesDetected = 0;
		resetTimer.start();
		
	}

	@Override
	protected void execute() {
		if(withinTolerance(RobotMap.getAverageRange())){
			timesDetected++;
		}
		if(resetTimer.get() >= RESET_TIME){
			resetTimer.reset();
			timesDetected = 0;
		}
		if(timesDetected >=2){
			SmartDashboard.putBoolean("withinDistance", true);
			System.out.println("WITHIN RANGE");
		}else{
			SmartDashboard.putBoolean("withinDistance", false);
		}
		//System.out.println(table.getBoolean("withinDistance"));
//		System.out.println("CURRENT DISTANCE: " + RobotMap.ultrasonic.getDistanceCM());
//		System.out.println("CURRENT HITS: " + timesDetected);
	}
	
	private boolean withinTolerance(double distance){
		return Math.abs(distance - desiredDistance) < TOLERANCE_IN_CM;
	}
	
	
	
	
	

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
