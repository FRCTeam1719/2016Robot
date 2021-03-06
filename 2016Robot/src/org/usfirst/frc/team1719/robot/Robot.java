package org.usfirst.frc.team1719.robot;

import org.usfirst.frc.team1719.robot.autonomousSelections.DoNothing;
import org.usfirst.frc.team1719.robot.autonomousSelections.LowBarAutonAndBack;
import org.usfirst.frc.team1719.robot.autonomousSelections.ReachAuton;
import org.usfirst.frc.team1719.robot.autonomousSelections.RockWallAuton;
import org.usfirst.frc.team1719.robot.autonomousSelections.RoughTerrainAuton;
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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot {


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
	public int autonomousMode = 3; //DEFAULT TO REACH AUTOMA
	public final int autonomousModes = 4; // maximum number to count to while
									// selecting auton modes starts at 0.
	final boolean VOLTAGEDISPLAY = true;
	final boolean AUTONDISPLAY = false;
	boolean currentDisplayMode = AUTONDISPLAY;
	final double TOLERANCE = 0.01;
	double maxPotValue = .274;
	double scalingFactor;
	double minPotValue = .255;
	boolean foundCamera = false;

	Command autonomousCommand;
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
		// Hardware Initialization
		// Allocate Hardware
		RobotMap.init();

		// Initialize Subsystems
		rightFlywheelPIDData = new PIDData(0, 0, 0);
		leftFlywheelPIDData = new PIDData(0, 0, 0);
		drive = new DriveSubsystem(RobotMap.leftDriveController, RobotMap.rightDriveController,
				RobotMap.leftDriveEncoder, RobotMap.rightDriveEncoder);
		rightFlywheel = new FlyWheel(RobotMap.rightFlyWheelController,
				rightFlywheelPIDData);
		leftFlywheel = new FlyWheel(RobotMap.leftFlyWheelController,  leftFlywheelPIDData);
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
		// Initialize Autonomous Command
		//autonomousCommand = new RoughTerrainAuton();
		System.out.println("AUTONOMOUS: " + autonomousCommand);
	}

	/**
	 * Initialize SmartDashboard values
	 */
	public void smartDashboardInit() {

		
		rightFlywheelPIDData = new PIDData();
		//Send PID constants for tuning
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
		SmartDashboard.putNumber("Arm steady kD", 0.016D);
		SmartDashboard.putNumber("Arm steady integral range", 7.0D);
		SmartDashboard.putBoolean("withinDistance", false);
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
		System.out.println("Pot: " + RobotMap.armPot.get());
		//A button toggles display mode
		if (display.buttonAPressed()) {
			if (currentDisplayMode == AUTONDISPLAY) {
				currentDisplayMode = VOLTAGEDISPLAY;
			} else if (currentDisplayMode == VOLTAGEDISPLAY) {
				currentDisplayMode = AUTONDISPLAY;
			}
		}
		if (currentDisplayMode == AUTONDISPLAY) {
			//Display the currently selected mode, if the button is pressed increment the mode
			display.displayString("A  " + String.valueOf(autonomousMode));
			if (display.buttonBPressed()) {
				autonomousMode++;
				if (autonomousMode > autonomousModes) {
					autonomousMode = 0;
				}
			} 
		} else if (currentDisplayMode == VOLTAGEDISPLAY) {
			//Display the battery voltage
			String voltage = Double.toString(DriverStation.getInstance().getBatteryVoltage());
			display.displayString(voltage);
		}
		
		//Set our auto made based on the selection
		switch (autonomousMode) {
		case 0:
			autonomousCommand = new DoNothing();
			System.out.println("Doing Noting");
			break;
		case 1:
			autonomousCommand = new RockWallAuton();
			System.out.println("Rock Wall?");
			break;
		case 2:
			autonomousCommand = new RoughTerrainAuton();
			System.out.println("RoughTerrainAuton");
			break;
		case 3:
			autonomousCommand = new ReachAuton();
			System.out.println("Reach Auton Selected");
			break;
		case 4:
			//autonomousCommand = new LowBarAuton();
			System.out.println("LowbarAuton");
			break;
		case 5:
			autonomousCommand = new LowBarAutonAndBack();
			System.out.println("Lowbar And back");
			break;
		}
		updateUltrasonic();

		//Run tasks
		Scheduler.getInstance().run();
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
		isAuton = true;
		if (foundCamera) {
			NIVision.IMAQdxStartAcquisition(session);
		}
		// schedule the autonomous command with safety
		if (autonomousCommand != null) {
			System.out.println(autonomousCommand);
			autonomousCommand.start();
		}
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
		//Make sure auto stops
		isAuton = false;
		if (autonomousCommand != null)
			autonomousCommand.cancel();

		if (foundCamera) {
			NIVision.IMAQdxStartAcquisition(session);
		}
		//Reset the gyro
		RobotMap.gyro.reset();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		// System.out.println("Angle: "+RobotMap.gyro.getAngle());
		// System.out.println("meh" + RobotMap.dial.get());
		updateUltrasonic();
		Scheduler.getInstance().run();
		if (foundCamera) {
			NIVision.IMAQdxGrab(session, frame, 1);
			CameraServer.getInstance().setImage(frame);
		}
		//System.out.println("rightUltrasonic: "+RobotMap.rightUltrasonic.getRangeInches());
		//System.out.println("leftUltrasonic: "+RobotMap.leftUltrasonic.getRangeInches());
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		isAuton = false;
		LiveWindow.run();
	}
	
	

	public static double getAverageRange(){
		return (RobotMap.leftUltrasonic.getRangeInches() + RobotMap.rightUltrasonic.getRangeInches())/2;
	}
	
	public void updateUltrasonic(){
		double left = RobotMap.leftUltrasonic.getRangeInches();
		double right = RobotMap.rightUltrasonic.getRangeInches();
		boolean withinTolerance = 0.75 > Math.abs(left - right);
		SmartDashboard.putNumber("leftUltrasjonic", left);
		SmartDashboard.putNumber("rightUltrasonic", right);
		SmartDashboard.putBoolean("withinTolerance", withinTolerance);

	}


}