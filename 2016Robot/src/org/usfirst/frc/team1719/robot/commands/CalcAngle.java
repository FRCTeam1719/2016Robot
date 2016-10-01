package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.RobotMap;
import org.usfirst.frc.team1719.robot.RobotMap.sides;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Line the robot up with a straight wall
 * 
 * @author aaron
 *
 */
public class CalcAngle extends Command {
	private final double TOLERANCE = 0.75;
	private double rightInitAverage;
	private double leftInitAverage;
	private States currentState;
	private sides fartherSide;
	private Button deadManSwitch;
	private double lastLeftReading;
	private double lastRightReading;
	
	/**
	 * @param deadManSwitch Button to monitor, command stops when released
	 */
	public CalcAngle(Button deadManSwitch){
		requires(Robot.drive);
		this.deadManSwitch = deadManSwitch;
	}
	
	private enum States {
		CALC_LEFT_AVERAGE,
		CALC_RIGHT_AVERAGE,
		DETERMINE_SIDE, 
		LINE_UP
	}

	@Override
	protected void initialize() {
		System.out.println("STARTED COMMAND");
		currentState = States.CALC_LEFT_AVERAGE;
	}

	@Override
	protected void execute() {
		// Figure out which side is closer
		if (currentState == States.CALC_LEFT_AVERAGE) {
			leftInitAverage = findInitAverage(RobotMap.rightUltrasonic);
			System.out.println("LEFT AVERAGE: "+leftInitAverage);
			currentState = States.CALC_RIGHT_AVERAGE;
		}else if(currentState == States.CALC_RIGHT_AVERAGE){
			rightInitAverage = findInitAverage(RobotMap.leftUltrasonic);
			currentState = States.DETERMINE_SIDE;
		}else if(currentState == States.DETERMINE_SIDE){
			if(rightInitAverage < leftInitAverage){
				//Left side is farther
				fartherSide = RobotMap.sides.LEFT;
			}else{
				//Right side is farther
				fartherSide = RobotMap.sides.RIGHT;
			}
			currentState = States.LINE_UP;
			
		}else if(currentState == States.LINE_UP){
			System.out.println("LINING UP");
			double leftPower = 0;
			double rightPower = 0;
			if(fartherSide == RobotMap.sides.LEFT){
				leftPower = -0.5;
				rightPower = 0.5;
			}else{
				rightPower = -0.5;
				leftPower = 0.5;
			}
			Robot.drive.operateDrive(leftPower, rightPower);
		}
	}
	
	private double findInitAverage(Ultrasonic sensor){
		double averageDistance = 0;
		for(int i=0;i<3;i++){
			averageDistance += sensor.getRangeInches();
		}
		averageDistance /= 3;
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
		return TOLERANCE > Math.abs(distance1 - distance2);
	}
	
	@Override
	protected boolean isFinished() {
		System.out.println(deadManSwitch.get());
		lastLeftReading = RobotMap.leftUltrasonic.getRangeInches();
		lastRightReading = RobotMap.rightUltrasonic.getRangeInches();
		return withinTolerance(lastLeftReading, lastRightReading)
				|| !deadManSwitch.get();
		
		
	}

	@Override
	protected void end() {
		Robot.drive.operateDrive(0, 0);
		System.out.println("ENDED");
		System.out.println("LEFT: "+lastLeftReading + " RIGHT: "+lastRightReading);

	}

	@Override
	protected void interrupted() {
		Robot.drive.operateDrive(0, 0);

	}

}
