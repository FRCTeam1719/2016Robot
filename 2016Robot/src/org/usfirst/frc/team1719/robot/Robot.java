package org.usfirst.frc.team1719.robot;

import org.usfirst.frc.team1719.robot.autonomousSelections.DoNothing;
import org.usfirst.frc.team1719.robot.autonomousSelections.LowBarAuton;
import org.usfirst.frc.team1719.robot.autonomousSelections.MoatAuton;
import org.usfirst.frc.team1719.robot.autonomousSelections.PortcullisAuton;
import org.usfirst.frc.team1719.robot.autonomousSelections.RampartsAuton;
import org.usfirst.frc.team1719.robot.autonomousSelections.RockWallAuton;
import org.usfirst.frc.team1719.robot.autonomousSelections.RoughTerrainAuton;
import org.usfirst.frc.team1719.robot.commands.AimAndFire;
import org.usfirst.frc.team1719.robot.commands.AutoSenseTower;
import org.usfirst.frc.team1719.robot.commands.TurnToAngle;
import org.usfirst.frc.team1719.robot.settings.PIDData;
import org.usfirst.frc.team1719.robot.subsystems.Arm;
import org.usfirst.frc.team1719.robot.subsystems.Display;
import org.usfirst.frc.team1719.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1719.robot.subsystems.DualShooter;
import org.usfirst.frc.team1719.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team1719.robot.subsystems.FlyWheel;
import org.usfirst.frc.team1719.robot.subsystems.PhotonCannon;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.VisionException;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot {

	// degrees
	final double PHOTON_CANNON_ANGLE = 60;

	final String CAMERA_NAME = "cam0";
	public static final double GET_VALUE_FROM_SMARTDASHBOARD = -1337.0D;
	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;
	public static Display display;
	public static PhotonCannon photonCannon;
	public static DriveSubsystem drive;
	public static FlyWheel rightFlywheel;
	public static FlyWheel leftFlywheel;
	public static DualShooter shooter;
	PIDData rightFlywheelPIDData;
	PIDData leftFlywheelPIDData;
	public static Arm arm;
	public int autonomousMode = 0;
	final boolean VOLTAGEDISPLAY = true;
	final boolean AUTONDISPLAY = false;
	boolean currentDisplayMode = VOLTAGEDISPLAY;
	final double TOLERANCE = 0.01;
	double maxPotValue = .274;
	double scalingFactor;
	double minPotValue = .255;
	boolean foundCamera = false;

	Command autonomousCommand;
	SendableChooser chooser;
	Command DisplayVoltage;
	Image frame;
	int session;
	NIVision.Rect crosshair;

	public static boolean isAuton = false;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */

	public void robotInit() {

		// Network Initialization

		// Setup Camera Server

		// Configure SmartDashboard Things

        // Hardware Initialization
        // Allocate Hardware
     	RobotMap.init();

		// Initialize Subsystems
		rightFlywheelPIDData = new PIDData(0, 0, 0);
		leftFlywheelPIDData = new PIDData(0, 0, 0);
		drive = new DriveSubsystem(RobotMap.leftDriveController, RobotMap.rightDriveController,
				RobotMap.leftDriveEncoder, RobotMap.rightDriveEncoder);
		rightFlywheel = new FlyWheel(RobotMap.rightFlyWheelController, RobotMap.rightFlyWheelEncoder,
				rightFlywheelPIDData);
		leftFlywheel = new FlyWheel(RobotMap.leftFlyWheelController, RobotMap.leftFlyWheelEncoder, leftFlywheelPIDData);
		shooter = new DualShooter(leftFlywheel, rightFlywheel, RobotMap.innerLeftShooterWheelController,
				RobotMap.innerRightShooterWheelController);
		arm = new Arm(RobotMap.armController, RobotMap.armPot);
		display = new Display(RobotMap.buttonA, RobotMap.buttonB, RobotMap.dial);
		photonCannon = new PhotonCannon();
		oi = new OI();

		isAuton = false;

		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		try {
			session = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
			NIVision.IMAQdxConfigureGrab(session);
			foundCamera = true;
		} catch (VisionException e) {
			foundCamera = false;
			System.out.println("Can't find the camera, failing with style");
		}
		smartDashboardInit();
		RobotMap.gyro.initGyro();
		RobotMap.gyro.calibrate();
	}

	/**
	 * Initialize SmartDashboard values
	 */
	public void smartDashboardInit() {

		chooser = new SendableChooser();

		// Move forwards command
		chooser.addDefault("Do nothing", new DoNothing());
		chooser.addObject("Go Under Low Bar", new LowBarAuton());
		chooser.addObject("Go over Rough Terrain", new RoughTerrainAuton());
		chooser.addObject("Go over Moat", new MoatAuton());
		chooser.addObject("Go over Ramparts", new RampartsAuton());
		chooser.addObject("Go over Rock Wall", new RockWallAuton());
		chooser.addObject("Go through the Portcullis", new PortcullisAuton());
		chooser.addObject("Shoot at tower", new AimAndFire());
		chooser.addObject("Turn 90 degrees", new TurnToAngle(90, true));

		rightFlywheelPIDData = new PIDData();
		chooser.addObject("Sense Tower High Goals", new AutoSenseTower());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);
		SmartDashboard.putNumber("Right flywheel kP: ", rightFlywheelPIDData.kP);
		SmartDashboard.putNumber("Right flywheel kI: ", rightFlywheelPIDData.kI);
		SmartDashboard.putNumber("Right flywheel kD: ", rightFlywheelPIDData.kD);

		SmartDashboard.putNumber("Drive kP", 0.02);
		SmartDashboard.putNumber("Drive kI", 0.003);
		SmartDashboard.putNumber("Drive kD", 0.003);

		SmartDashboard.putNumber("Turn kP", 0.1);
		SmartDashboard.putNumber("Turn kI", 0.0);
		SmartDashboard.putNumber("Turn kD", 0.65);
    	
    	SmartDashboard.putNumber("Arm steady kP", (0.2 / 90));
    	
    	SmartDashboard.putNumber("Move arm to pos kP", .03);
    	SmartDashboard.putNumber("Move arm to pos kI", .001);
    	SmartDashboard.putNumber("Move arm to pos kD", .001);
    	SmartDashboard.putNumber("Arm steady kP", 0.3D);
		SmartDashboard.putNumber("Arm steady kI", 0.01D);
		SmartDashboard.putNumber("Arm steady kD", 0.007D);
		SmartDashboard.putNumber("Arm steady integral range", 7.0D);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	public void disabledInit() {
		// Make sure everything gets disabled
		isAuton = false;
		rightFlywheel.spin(0);
		leftFlywheel.spin(0);

		if (foundCamera) {
			NIVision.IMAQdxStopAcquisition(session);
		}
	}

	public void disabledPeriodic() {

		if (display.buttonAPressed()) {
			currentDisplayMode = AUTONDISPLAY;
		} else if (display.buttonBPressed()) {
			currentDisplayMode = VOLTAGEDISPLAY;
		}

		if (currentDisplayMode == AUTONDISPLAY) {
			// System.out.println("displayingAuton");
			Double dialPos = display.getDialReading();
			if (dialPos - .25 <= TOLERANCE) {
				autonomousMode = 0;
				display.displayString("A  0");
			} else if (dialPos - .255 <= TOLERANCE) {
				autonomousMode = 1;
				display.displayString("A  1");
			} else if (dialPos - .26 <= TOLERANCE) {
				autonomousMode = 2;
				display.displayString("A  2");
			} else if (dialPos - .265 <= TOLERANCE) {
				autonomousMode = 3;
				display.displayString("A  3");
			}
		} else if (currentDisplayMode == VOLTAGEDISPLAY) {
			String voltage = Double.toString(DriverStation.getInstance().getBatteryVoltage());
			display.displayString(voltage);
		}

		Scheduler.getInstance().run();
		System.out.println("Arm angle: " + Robot.arm.getArmAngle());

	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings and commands.
	 */
	public void autonomousInit() {
		autonomousCommand = (Command) chooser.getSelected();
		isAuton = true;
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */
		if (foundCamera) {
			NIVision.IMAQdxStartAcquisition(session);
		}
		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();

	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {

		/*
		 * This makes sure that the autonomous stops running when teleop starts
		 * running. If you want the autonomous to continue until interrupted by
		 * another command, remove this line or comment it out.
		 */
		isAuton = false;
		if (autonomousCommand != null)
			autonomousCommand.cancel();

		if (foundCamera) {
			NIVision.IMAQdxStartAcquisition(session);
		}
		RobotMap.gyro.reset();
	}



	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		// System.out.println("Angle: "+RobotMap.gyro.getAngle());
		// System.out.println("meh" + RobotMap.dial.get());
		Scheduler.getInstance().run();
		if (foundCamera) {
			NIVision.IMAQdxGrab(session, frame, 1);
			CameraServer.getInstance().setImage(frame);
		}
		System.out.println("Enc value: " + RobotMap.leftDriveEncoder);
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		isAuton = false;
		LiveWindow.run();
	}

}