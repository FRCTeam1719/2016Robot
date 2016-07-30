package org.usfirst.frc.team1719.robot.commands;

import org.usfirst.frc.team1719.robot.Robot;
import org.usfirst.frc.team1719.robot.RobotMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Move forward a specified distance
 * 
 * @author aaroneline
 *
 */
public class MoveForwards extends Command implements PIDOutput {

	double HALF_SPEED = 0.5D;
	double speed;
	
	double kP;
	double kI;
	double kD;
	
	PIDController controller;
	double leftOutput;
	double rightOutput;
	
	//TODO: Fix encoders and use them instead, going by time is ugly
	Timer timer;

	/** distances measured in feet
	 * 
	 */
	private double desiredTime;
	
	/**
	 * 
	 * @param distFeet
	 *            to move forward
	 */
	public MoveForwards(double distFeet, double speed) {
		requires(Robot.drive);
		this.desiredTime = distFeet;
		this.speed = speed;
		
		timer = new Timer();
	}

	@Override
	protected void end() {	
		//STop the drive
		Robot.drive.operateDrive(0, 0);
		System.out.println("Ended");
	}

	@Override
	protected void execute() {
		controller.setSetpoint(0);
		controller.enable();
        Robot.drive.operateDrive(0.7 + leftOutput, 0.7 + rightOutput); // drive towards heading 0
        System.out.println("Output: " + leftOutput);
        System.out.println("Angle: " + RobotMap.navX.getAngle());
	}

	@Override
	
	protected void initialize() {
		timer.reset();
		timer.start();
		
		kP = SmartDashboard.getNumber("Drive kP");
		kI = SmartDashboard.getNumber("Drive kI");
		kD = SmartDashboard.getNumber("Drive kD");
		
		controller = new PIDController(kP, kI, kD, RobotMap.navX, this);
		controller.setInputRange(-180, 180);
		controller.setOutputRange(-1, 1);
		controller.setContinuous();

		
		System.out.println("MoveForwardStarted");
		//Robot.drive.resetEncoders();
		if(desiredTime == 0.0D) desiredTime = SmartDashboard.getNumber("MoveDistParam");
	}

	@Override
	protected void interrupted() {
		Robot.drive.operateDrive(0, 0);

	}

	@Override
	protected boolean isFinished() {
		System.out.println(timer.get());
		return timer.get() >= desiredTime;
	}

	@Override
	public void pidWrite(double output) {
		leftOutput = output;
		rightOutput = -output;
		
	}

}
