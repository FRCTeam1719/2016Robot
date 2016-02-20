package org.usfirst.frc.team1719.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Spark;

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
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
	static double FLYWHEEL_CIRCUMFRENCE_FEET = 1.5708;
	static double DRIVEWHEEL_CIRCUMFRENCE_FEET = 2.618;
	
	static Spark leftDriveController;
	static Spark rightDriveController;



	public static Spark rightFlyWheelController;
	public static Encoder rightFlyWheelEncoder;
	public static Spark leftFlyWheelController;
	public static Encoder leftFlyWheelEncoder;
	public static Spark armController;
	public static DigitalInput armLowerLimitSwitch;
	public static DigitalInput armUpperLimitSwitch;	
	public static AnalogGyro gyro;
	public static Spark innerLeftShooterWheelController;
	public static Spark innerRightShooterWheelController;
	public static AnalogPotentiometer armPot;	
	public static Encoder rightDriveWheelEncoder;
	public static Encoder leftDriveWheelEncoder;
	public static AnalogInput dial;
	public static DigitalInput buttonA;
	public static DigitalInput buttonB;
	public static Relay photonCannon;

	public static void init(){
		//Main hardware allocation
		
		//create and configure encoders
		rightDriveWheelEncoder = new Encoder(6, 7, false, Encoder.EncodingType.k4X);
		rightDriveWheelEncoder.setDistancePerPulse(DRIVEWHEEL_CIRCUMFRENCE_FEET / 256);
		leftDriveWheelEncoder = new Encoder(8, 9, false, Encoder.EncodingType.k4X);
		leftDriveWheelEncoder.setDistancePerPulse(DRIVEWHEEL_CIRCUMFRENCE_FEET / 256);
		
		gyro = new AnalogGyro(0);

		//Motor Controllers
		leftDriveController = new Spark(1);    
		rightDriveController = new Spark(0);
		leftFlyWheelController = new Spark(2);
		rightFlyWheelController = new Spark(3);
		armController = new Spark(4);
		innerLeftShooterWheelController = new Spark(5);
		innerRightShooterWheelController = new Spark(6);

		//Sensors
		
		//DIO
		rightFlyWheelEncoder = new Encoder(2, 3, true, Encoder.EncodingType.k4X);		
		rightFlyWheelEncoder.setDistancePerPulse(FLYWHEEL_CIRCUMFRENCE_FEET / 20);
		leftFlyWheelEncoder = new Encoder(4, 5, true, Encoder.EncodingType.k4X);
		leftFlyWheelEncoder.setDistancePerPulse(FLYWHEEL_CIRCUMFRENCE_FEET / 20);

		//Analog In
		dial = new AnalogInput(3);
		//armPot = new AnalogPotentiometer(1);
        armPot = new AnalogPotentiometer(1, 141.06, -108.2);
        buttonA = new DigitalInput(19);
		buttonB = new DigitalInput(20);
		
		photonCannon = new Relay(0);
	}
	
	/**
	 * Function for configuring encoders
	 * @param encoder
	 */
	
	
}
