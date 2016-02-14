package org.usfirst.frc.team1719.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
	static double FLYWHEEL_CIRCUMFRENCE_FEET = 1.57075;
	
	public static Spark leftDriveController;
	public static Spark rightDriveController;
	public static Encoder leftDriveEncoder;
	public static Encoder rightDriveEncoder;
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
	public static AnalogInput dial;
	public static DigitalInput buttonA;
	public static DigitalInput buttonB;
	public static void init(){
		//Main hardware allocation
		
		//Motor Controllers
		rightDriveController = new Spark(0);
		leftDriveController = new Spark(1);    
		leftFlyWheelController = new Spark(2);
		rightFlyWheelController = new Spark(3);
		armController = new Spark(4);
		innerLeftShooterWheelController = new Spark(5);
		innerRightShooterWheelController = new Spark(6);

		//Sensors
		
		//DIO
		rightFlyWheelEncoder = new Encoder(2, 3, true, Encoder.EncodingType.k4X);		
		configureEncoder(rightFlyWheelEncoder);
		leftFlyWheelEncoder = new Encoder(4, 5, true, Encoder.EncodingType.k4X);
		configureEncoder(leftFlyWheelEncoder);
		rightDriveEncoder = new Encoder(6, 7, true, Encoder.EncodingType.k4X);
		configureEncoder(rightDriveEncoder);
		leftDriveEncoder = new Encoder(8, 9, true, Encoder.EncodingType.k4X);
		configureEncoder(leftDriveEncoder);
        buttonA = new DigitalInput(19);
		buttonB = new DigitalInput(20);
		
		//Analog In
		gyro = new AnalogGyro(0);
        armPot = new AnalogPotentiometer(1, 1200.0D);
		dial = new AnalogInput(3);



	}
	
	/**
	 * Function for configuring encoders
	 * @param encoder
	 */
	private static void configureEncoder(Encoder encoder){

		 //Setting values for encoders

		encoder.setMaxPeriod(.02);
		encoder.setMinRate(10);
		encoder.setDistancePerPulse(FLYWHEEL_CIRCUMFRENCE_FEET);
		encoder.setSamplesToAverage(127);
		
	}
	
}