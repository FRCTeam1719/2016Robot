package org.usfirst.frc.team1719.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
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
	static double FLYWHEEL_CIRCUMFRENCE_FEET = 1.57075;
	
	static SpeedController leftDriveController;
	static SpeedController rightDriveController;
	static Compressor mainCompressor;
	static Solenoid shifterSolenoid;

	
	public static Talon rightFlyWheelController;
	public static Encoder rightFlyWheelEncoder;
	public static Talon leftFlyWheelController;
	public static Encoder leftFlyWheelEncoder;
	public static Spark armController;
	public static DigitalInput armLowerLimitSwitch;
	public static DigitalInput armUpperLimitSwitch;	
	public static AnalogGyro gyro;
	public static Talon innerShooterWheelController;
	public static AnalogPotentiometer armPot;
	
	public static Encoder rightDriveWheelEncoder;
	public static Encoder leftDriveWheelEncoder;
	
	public static void init(){
		//Main hardware allocation
		
		//create and configure encoders
		rightDriveWheelEncoder = new Encoder(6, 7, false, Encoder.EncodingType.k4X);
		rightDriveWheelEncoder.setDistancePerPulse(DRIVE_WHEEL_CIRCUMFRENCE / 256);
		leftDriveWheelEncoder = new Encoder(8, 9, false, Encoder.EncodingType.k4X);
		leftDriveWheelEncoder.setDistancePerPulse(DRIVE_WHEEL_CIRCUMFRENCE / 256);
		
		gyro = new AnalogGyro(0);
		armLowerLimitSwitch = new DigitalInput(2);
		armUpperLimitSwitch = new DigitalInput(1);
		armMotor = new Talon(4);
		armPot = new AnalogPotentiometer(1, 100);
		rightFlyWheelTalon = new Talon(2);
		rightFlyWheelEncoder = new Encoder(4, 5, true, Encoder.EncodingType.k4X);
		configureEncoder(rightFlyWheelEncoder);
=======
		//Motor Controllers
		leftDriveController = new Talon(0);
		rightDriveController = new Talon(1);
		leftFlyWheelController = new Talon(2);
		rightFlyWheelController = new Talon(3);
		armController = new Spark(4);
		innerShooterWheelController = new Talon(5);
>>>>>>> refs/remotes/origin/RobotStaging

		//Sensors
		
		//DIO
		armUpperLimitSwitch = new DigitalInput(0);
		armLowerLimitSwitch = new DigitalInput(1);
		rightFlyWheelEncoder = new Encoder(2, 3, true, Encoder.EncodingType.k4X);		
		configureEncoder(rightFlyWheelEncoder);
		leftFlyWheelEncoder = new Encoder(4, 5, true, Encoder.EncodingType.k4X);
		configureEncoder(leftFlyWheelEncoder);
		
		//Analog In
		gyro = new AnalogGyro(0);
        armPot = new AnalogPotentiometer(1, 1200.0D);
	}
	
	private static void configureEncoder(Encoder encoder){

		 //Setting values for encoders

		encoder.setMaxPeriod(.02);
		encoder.setMinRate(10);
		encoder.setDistancePerPulse(FLYWHEEL_CIRCUMFRENCE_FEET);
		encoder.setSamplesToAverage(127);
	}
	
}
