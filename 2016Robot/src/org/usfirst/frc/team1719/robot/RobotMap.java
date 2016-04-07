package org.usfirst.frc.team1719.robot;

import org.usfirst.frc.team1719.robot.sensors.AutoScalingPotentiometer;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Ultrasonic;

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
	public final static boolean COMPILINGFORPRODUCTIONBOT = true;

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
	
	public static SpeedController leftDriveController;
	public static SpeedController rightDriveController;
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
	public static AutoScalingPotentiometer armPot;	
	
	public static AnalogInput dial;
	public static DigitalInput buttonA;
	public static DigitalInput buttonB;
	public static Relay photonCannon;
	public static DigitalInput potCallibrationSwitch;
	public static Ultrasonic rightUltrasonic;
	public static Ultrasonic leftUltrasonic;
	public enum sides{
		LEFT,
		RIGHT
	}
	public static void init(){
		
		//Main hardware allocation
		

		//Motor Controllers
		rightDriveController = configureMotor(rightDriveController,0);
		leftDriveController = configureMotor(leftDriveController,1);   
		leftFlyWheelController = new Spark(2);
		rightFlyWheelController = new Spark(3);
		armController = new Spark(4);
		innerLeftShooterWheelController = new Spark(5);
		innerRightShooterWheelController = new Spark(6);

		//Sensors
		
		//DIO
		//rightFlyWheelEncoder = new Encoder(2, 3, true, Encoder.EncodingType.k4X);	
		//rightFlyWheelEncoder.setDistancePerPulse(FLYWHEEL_CIRCUMFRENCE_FEET / 20);
//		leftFlyWheelEncoder = new Encoder(4, 5, true, Encoder.EncodingType.k4X);
//		leftFlyWheelEncoder.setDistancePerPulse(FLYWHEEL_CIRCUMFRENCE_FEET / 20);
		potCallibrationSwitch = new DigitalInput(0);
		
		rightDriveEncoder = new Encoder(6, 7, true, Encoder.EncodingType.k4X);
		rightDriveEncoder.setDistancePerPulse(1);
		leftDriveEncoder = new Encoder(8, 9, true, Encoder.EncodingType.k4X);
		leftDriveEncoder.setDistancePerPulse(1);


		//Analog In
		//armPot = new AnalogPotentiometer(1);

		
		//.116
		//.754
		//Analog In
		gyro = new AnalogGyro(0);
		//dial = new AnalogInput(3);
        
		
		photonCannon = new Relay(0);
		//.127
		//.773
		//.646
        //armPot = new AnalogPotentiometer(1, 141, -110.4);

		//armPot = new ScaledPotentiometer(potChannel, 139.32, -106);
		//armPot = autoConfigurePotentiometer(2, 139.32, 90);
		//armPot = autoConfigurePotentiometer(2, 360, 90);
		armPot = new AutoScalingPotentiometer(new AnalogInput(2),200.44, -63);
        buttonA = new DigitalInput(19);
		buttonB = new DigitalInput(20);
		
		//leftUltrasonic.setAutomaticMode(true);
		leftUltrasonic = new Ultrasonic(2,3,Ultrasonic.Unit.kInches);
		//leftUltrasonic.setAutomaticMode(true);
		rightUltrasonic = new Ultrasonic(4,5,Ultrasonic.Unit.kInches);
		leftUltrasonic.setAutomaticMode(true);
	}
	
	/**
	 * Function for configuring encoders depending on the 
	 * @param encoder
	 */
	private static SpeedController configureMotor(SpeedController controller, int port){
		if(COMPILINGFORPRODUCTIONBOT){
			controller = new Spark(port);
		}else{
			//We are compiling for the practice bot, use Talons instead
			controller = new Talon(port);
			System.out.println("Setting things to Talons");
		}
		return controller;
	}
	
	
	
}