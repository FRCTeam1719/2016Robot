package org.usfirst.frc.team1719.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
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
	public static Talon armController;
	public static DigitalInput armLowerLimitSwitch;
	public static DigitalInput armUpperLimitSwitch;
	public static DigitalInput ballLimitSwitch;
	public static AnalogGyro gyro;
	public static Talon innerShooterWheelController;
	public static AnalogPotentiometer armPot;
	
	public static void init(){
		//Main hardware allocation
		
		//Motor Controllers
		leftDriveController = new Talon(0);
		rightDriveController = new Talon(1);
		leftFlyWheelController = new Talon(2);
		rightFlyWheelController = new Talon(3);
		armController = new Talon(4);
		innerShooterWheelController = new Talon(5);

		//Sensors
		
		//DIO
		armUpperLimitSwitch = new DigitalInput(0);
		armLowerLimitSwitch = new DigitalInput(1);
		ballLimitSwitch = new DigitalInput(2);
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
