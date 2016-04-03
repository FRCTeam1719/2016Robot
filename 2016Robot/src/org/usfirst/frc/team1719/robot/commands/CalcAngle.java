package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.RobotMap;
import org.usfirst.frc.team1719.robot.RobotMap.sides;
import edu.wpi.first.wpilibj.Ultrasonic;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Determine the angle we are in relation to a flat plane, and pass it to the
 * SmartDashboard Calculated from ultrasonic readings
 * 
 * @author aaron
 *
 */
public class CalcAngle extends Command {
	private final double TOLERANCE = 0.5;
	private double rightInitAverage;
	private double leftInitAverage;
	private States currentState;
	private sides fartherSide;
	private Timer pulseTimer;
	private final double PULSE_TIME = 0.2;
	private boolean driveRunning;
	private final double ERROR_AVG = -1337;
	private Button deadManSwitch;
	private final double DISTANCE_BETWEEN_SENSORS = 10.25;
	private double lastLeftReading;
	private double lastRightReading;
	
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
		driveRunning = false;
		pulseTimer = new Timer();
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
				double angle = -1 * calcAngle(leftInitAverage, rightInitAverage);
				SmartDashboard.putNumber("TurnToAngleParam", angle);
				System.out.println(angle);
			}else{
				//Right side is farther
				double angle = calcAngle(rightInitAverage, leftInitAverage);
				SmartDashboard.putNumber("TurnToAngleParem", angle);
				fartherSide = RobotMap.sides.RIGHT;
				System.out.println(angle);
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
	
	private double calcAngle(double fartherSide, double closerSide){
		double rad = Math.atan((fartherSide-closerSide)/DISTANCE_BETWEEN_SENSORS);
		double degrees = Math.toDegrees(rad);
		return degrees;
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
	
	//This is implementing a pulsing drive that might give more accuracy in turning to small distances
	//This might not work, and may need to be changes to something more robust.
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
