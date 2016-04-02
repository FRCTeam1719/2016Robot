package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ReadUltraSonics extends Command{

	private double desiredDistance;
	private final double TOLERANCE_IN_CM = 4;
	private double timesDetected;
	private Timer resetTimer;
	private final double RESET_TIME = 0.5;
	private final int REQUIRED_DETECTIONS = 2;
	private boolean withinDistance;
	private boolean linedUp;
	
	
	
	NetworkTable table = NetworkTable.getTable("Test");
	
	public ReadUltraSonics(double desiredDistance){
		this.desiredDistance = desiredDistance;
		resetTimer = new Timer();
		withinDistance = false;
		linedUp = false;
	}
	
	
	
	@Override
	protected void initialize() {
		timesDetected = 0;
		resetTimer.start();
		
	}

	@Override
	protected void execute() {
		withinDistance = withinDistance();
		linedUp = linedUp();
		updateSmartDashboard();
		
		//System.out.println(table.getBoolean("withinDistance"));
//		System.out.println("CURRENT DISTANCE: " + RobotMap.ultrasonic.getDistanceCM());
//		System.out.println("CURRENT HITS: " + timesDetected);
	}
	
	/**
	 * Check if a distance is within the tolerance of the hardware
	 * @param distance to be checked
	 * @return whether or not we are within tolerance
	 */
	private boolean withinTolerance(double distance){
		return Math.abs(distance - desiredDistance) < TOLERANCE_IN_CM;
	}
	
	/**
	 * 
	 * @return average reading from both ultrasonics
	 */
	private double averageReadings(){
		double leftReading = RobotMap.leftUltrasonic.getDistanceCM();
		double rightReading = RobotMap.rightUltrasonic.getDistanceCM();
		return (leftReading+rightReading)/2;
	}
	
	/**
	 * Update the smartdashboard values with the values calculated in this command
	 */
	private void updateSmartDashboard(){
		SmartDashboard.putBoolean("withinDistance", linedUp&&withinDistance);
		SmartDashboard.putBoolean("linedUp", linedUp);
	}
	
	private boolean linedUp(){
		double readingDifference = Math.abs(
				RobotMap.leftUltrasonic.getDistanceCM() - RobotMap.rightUltrasonic.getDistanceCM());
		return TOLERANCE_IN_CM > readingDifference;
	}
	
	private boolean withinDistance(){
		if(withinTolerance(averageReadings())){
			timesDetected++;
		}
		if(resetTimer.get() >= RESET_TIME){
			resetTimer.reset();
			timesDetected = 0;
		}
		if(timesDetected >=REQUIRED_DETECTIONS){
			return true;
		}else{
			return false;
		}
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
