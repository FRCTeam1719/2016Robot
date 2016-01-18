package org.usfirst.frc.team1719.robot;

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
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
	static double FLYWHEEL_CIRCUMFRENCE_FEET = 1.57075;
	
	static SpeedController leftController;
	static SpeedController rightController;
	static Compressor mainCompressor;
	static Solenoid shifterSolenoid;

	
	public static Talon rightFlyWheelTalon;
	public static Encoder rightFlyWheelEncoder;
	
	public static void init(){
		leftController = new Talon(0);
		rightController = new Talon(1);
		mainCompressor = new Compressor();
		shifterSolenoid = new Solenoid(0);
		
		rightFlyWheelTalon = new Talon(2);
		rightFlyWheelEncoder = new Encoder(4, 5, true, Encoder.EncodingType.k4X);
		configureEncoder(rightFlyWheelEncoder);

		
	}
	
	private static void configureEncoder(Encoder encoder){
		encoder.setMaxPeriod(.02);
		encoder.setMinRate(10);
		encoder.setDistancePerPulse(FLYWHEEL_CIRCUMFRENCE_FEET);
		encoder.setSamplesToAverage(127);
	}
	
}
