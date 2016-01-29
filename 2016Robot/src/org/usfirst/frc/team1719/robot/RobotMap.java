package org.usfirst.frc.team1719.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	
	/** 
	 * Measured in feet
	 */
	private static final double DRIVE_WHEEL_CIRCUMFRENCE = Math.PI * 10 / 12;
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
	
	static SpeedController leftController;
	static SpeedController rightController;
	static Compressor mainCompressor;
	static Solenoid shifterSolenoid;
	
	public static AnalogGyro gyro;
	
	public static Encoder rightDriveWheelEncoder;
	public static Encoder leftDriveWheelEncoder;
	
	public static void init(){
		leftController = new Talon(0);
		rightController = new Talon(1);
		mainCompressor = new Compressor();
		shifterSolenoid = new Solenoid(0);
		
		//create and configure encoders
		rightDriveWheelEncoder = new Encoder(-1337, -1337, false, Encoder.EncodingType.k4X);
		rightDriveWheelEncoder.setDistancePerPulse(DRIVE_WHEEL_CIRCUMFRENCE / 256);
		leftDriveWheelEncoder = new Encoder(-1337, -1337, false, Encoder.EncodingType.k4X);
		leftDriveWheelEncoder.setDistancePerPulse(DRIVE_WHEEL_CIRCUMFRENCE / 256);
		
		gyro = new AnalogGyro(0);
	}
	
}
