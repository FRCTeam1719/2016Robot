package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.RobotMap;
import org.usfirst.frc.team1719.robot.sensors.Ultrasonic;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Determine the angle we are in relation to a flat plane, and pass it to the SmartDashboard
 * Calculated from ultrasonic readings
 * @author aaron
 *
 */
public class CalculateAngles extends Command{
	private final double DISTANCE_BETWEEN_SENSORS = 6.0;
	private final double TOLERANCE = 4;
	PIDController test;
	private double leftReadings[] = new double[3];
	private double rightReadings[] = new double[3];
	private double rightInitAverage;
	private double leftInitAverage;
	private stages currentState;
	
	private enum stages{
		DETERMINE_SIDE,
		LINE_UP
	}
	
	
	
	@Override
	protected void initialize() {
		currentState = stages.DETERMINE_SIDE;
	}

	@Override
	protected void execute() {
		if(currentState == stages.DETERMINE_SIDE){
			double lastLeftReading = RobotMap.leftUltrasonic.getDistanceCM();
			for(int i=0;i<3;i++){
				
			}
		}
	}

	private boolean withinTolerance(double distance){
		
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
