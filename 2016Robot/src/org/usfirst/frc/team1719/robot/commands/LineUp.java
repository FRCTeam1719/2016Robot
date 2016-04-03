package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.RobotMap;
import org.usfirst.frc.team1719.robot.RobotMap.sides;
import org.usfirst.frc.team1719.robot.sensors.Ultrasonic;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Determine the angle we are in relation to a flat plane, and pass it to the
 * SmartDashboard Calculated from ultrasonic readings
 * 
 * @author aaron
 *
 */
public class LineUp extends Command {
	private final double TOLERANCE = 4;
	private double rightInitAverage;
	private double leftInitAverage;
	private States currentState;
	private sides fartherSide;
	private Timer pulseTimer;
	private final double PULSE_TIME = 0.2;
	private boolean driveRunning;
	private final double ERROR_AVG = -1337;
	
	public LineUp(){
		requires(Robot.drive);
	}
	
	private enum States {
		CALC_LEFT_AVERAGE,
		CALC_RIGHT_AVERAGE,
		DETERMINE_SIDE, 
		LINE_UP
	}

	@Override
	protected void initialize() {
		currentState = States.DETERMINE_SIDE;
		driveRunning = false;
	}

	@Override
	protected void execute() {
		// Figure out which side is closer
		if (currentState == States.CALC_LEFT_AVERAGE) {
			double leftAverage = findInitAverage(RobotMap.leftUltrasonic);
			if(leftAverage!=ERROR_AVG){
				leftInitAverage = leftAverage;
				currentState = States.CALC_RIGHT_AVERAGE;
			}
		}else if(currentState == States.CALC_RIGHT_AVERAGE){
			double rightAverage = findInitAverage(RobotMap.rightUltrasonic);
			if(rightAverage!=ERROR_AVG){
				rightInitAverage = rightAverage;
				currentState = States.DETERMINE_SIDE;
			}
		}else if(currentState == States.DETERMINE_SIDE){
			if(rightInitAverage < leftInitAverage){
				//Left side is farther
				fartherSide = RobotMap.sides.LEFT;
			}else{
				//Right side is farther
				fartherSide = RobotMap.sides.RIGHT;
			}
			pulseTimer.start();
			currentState = States.LINE_UP;
		}else if(currentState == States.LINE_UP){
			if(pulseTimer.get() > PULSE_TIME){
				toggleDrive(fartherSide);
				pulseTimer.reset();
			}
		}
	}
	
	private double findInitAverage(Ultrasonic sensor){
		double lastReading = sensor.getDistanceCM();
		int foundCounter = 0;
		boolean foundEnough = false;
		int tries = 0;
		double validReadings[] = new double[3];
		while ((foundCounter < 3) || (tries < 3)) {
			double readings[] = new double[3];
			for (int i = 0; i < 3; i++) {
				double newReading = sensor.getDistanceCM();
				if (withinTolerance(lastReading, newReading)) {
					readings[i] = newReading;
					foundCounter++;
					lastReading = newReading;
				}
			}
			tries++;
			if(foundCounter < 3){
				foundEnough = true;
				validReadings = readings;
			}
		}
		double averageDistance = -1337;
		if(foundEnough){
			averageDistance = 0;
			for(int i=0;i<validReadings.length;i++){
				averageDistance += validReadings[i];
			}
			averageDistance /= validReadings.length;
		}
		return averageDistance;
	}

	/**
	 * Checks if two distances are within a tolerance
	 * 
	 * @param distance1
	 * @param distance2
	 * @return boolean distances within tolerance
	 */
	private boolean withinTolerance(double distance1, double distance2) {
		return TOLERANCE < Math.abs(distance1 - distance2);
	}
	
	private void toggleDrive(RobotMap.sides sideToToggle){
		double drive_parem[] = new double[2];
		if(driveRunning){
			//Stop the drive
			drive_parem[0] = 0;
			drive_parem[1] = 0;
		}else if (sideToToggle==RobotMap.sides.LEFT){
			drive_parem[0] = 1;
			drive_parem[1] = 0;
		}else if (sideToToggle==RobotMap.sides.RIGHT){
			drive_parem[0] = 1;
			drive_parem[1] = 0;
		}
		Robot.drive.operateDrive(drive_parem[0], drive_parem[1]);
		driveRunning = !driveRunning;
	}
	

	@Override
	protected boolean isFinished() {
		
		try{
			return SmartDashboard.getBoolean("linedUp");
		}catch(Exception e){
			System.out.println("CANT FIND VALUE ON DASHBOARD, QUITING");
			return true;
		}
		
		
	}

	@Override
	protected void end() {
		Robot.drive.operateDrive(0, 0);

	}

	@Override
	protected void interrupted() {
		Robot.drive.operateDrive(0, 0);

	}

}
