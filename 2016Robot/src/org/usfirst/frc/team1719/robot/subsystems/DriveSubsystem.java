
package org.usfirst.frc.team1719.robot.subsystems;

import org.usfirst.frc.team1719.robot.RobotMap;
import org.usfirst.frc.team1719.robot.commands.UseDrive;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * Drive Subsystem
 * Controls a six wheel belt drive, driven by 2 Spark motor controllers
 * @author aaroneline
 *
 */
public class DriveSubsystem extends Subsystem{

	Spark leftController;
	Spark rightController;
	RobotDrive mainDrive;
	final double HALF_SPEED = 0.5;
	double previousError = 0;
	double pidOutput = 0;
	double integral = 0;
	double derivative = 0;
	double kP;
	double kI;
	double kD;
	
	final double PIDTolerance = 0.5D;
	/**
	 * Define controllers
	 * @param leftController Spark
	 * @param rightController Spark
	 */
	public DriveSubsystem(Spark leftController,Spark rightController){
		mainDrive = new RobotDrive(leftController, rightController);
		this.leftController = leftController;
		this.rightController = rightController;
	}
	
	/**
	 * Operate the drive as a standard tank drive
	 * @param left speed
	 * @param right speed
	 */
	public void operateDrive(double left, double right){
		mainDrive.tankDrive(left, right);
	}
	
	/**
	 * Drive the robot straight, using a gryo and a PID loop to correct for errors
	 * @param speed to drive
	 */
	public void driveStraight(double speed){
		kP = SmartDashboard.getNumber("Drive kP");
		kI = SmartDashboard.getNumber("Drive kI");
		kD = SmartDashboard.getNumber("Drive kD");
		
		double currentAngle = RobotMap.gyro.getAngle();
		double error = currentAngle;
		integral += error;
		if(Math.abs(currentAngle) < PIDTolerance){
			integral = 0;
			error = 0;
		}
		derivative = error - previousError;
		double output = (error*kP) + (integral*kI) + (derivative * kD);
		
		mainDrive.arcadeDrive(speed, output);
		previousError = error;
	}

	public void initDefaultCommand(){
		setDefaultCommand(new UseDrive());
	}
	
}
