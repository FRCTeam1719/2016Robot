package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.RobotMap;
import org.usfirst.frc.team1719.robot.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MoveForwardsDistance extends Command {
	
	final double HALF_SPEED = 0.5;
	/** distances measured in feet
	 * 
	 */
	private double desiredDist;
	private double currentDist = 0D;
	
	
	public double kP = 0.1;
	
	
	DriveSubsystem drive = Robot.drive;
	
	public MoveForwardsDistance() {
		requires(Robot.drive);
		
		
	}

	@Override
	protected void end() {	
		Robot.drive.operateDrive(0, 0);
	}

	@Override
	protected void execute() {
		int rightEncoderVal = RobotMap.rightDriveWheelEncoder.get();
		int leftEncoderVal = RobotMap.leftDriveWheelEncoder.get();
		
		currentDist = (rightEncoderVal + leftEncoderVal) / 2;
        
        Robot.drive.driveStraight(HALF_SPEED); // drive towards heading 0
		
		
	}

	@Override
	protected void initialize() {
		desiredDist = SmartDashboard.getNumber("Move Forwards Distance: ");
		currentDist = 0;
		RobotMap.gyro.reset();
		RobotMap.leftDriveWheelEncoder.reset();
		RobotMap.rightDriveWheelEncoder.reset();
		System.out.println("GYRO RESET: CURRENT ANGLE: "+RobotMap.gyro.getAngle());
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished() {
		return currentDist >= desiredDist;
	}

}
