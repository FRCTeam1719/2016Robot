
package org.usfirst.frc.team1719.robot;

import org.usfirst.frc.team1719.robot.commands.ExampleCommand;
import org.usfirst.frc.team1719.robot.commands.UseFlyWheel;
import org.usfirst.frc.team1719.robot.settings.PIDData;
import org.usfirst.frc.team1719.robot.subsystems.Arm;
import org.usfirst.frc.team1719.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1719.robot.subsystems.DualShooter;
import org.usfirst.frc.team1719.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team1719.robot.subsystems.FlyWheel;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
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

	final String CAMERA_NAME = "cam0";
	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;
	public static DriveSubsystem drive;
	public static FlyWheel rightFlywheel;
	public static FlyWheel leftFlywheel;
	public static DualShooter shooter;
	PIDData rightFlywheelPIDData;
	PIDData leftFlywheelPIDData;
	public static Arm arm;
    Command autonomousCommand;
    SendableChooser chooser;
    Image frame;
    int session;
    NIVision.Rect crosshair;

    public static boolean isAuton = false;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		
    	//Network Initialization
	
        //Setup Camera Server
    	
    	//Configure SmartDashboard Things
        	   
        
        //Hardware Initialization
        //Allocate Hardware
        RobotMap.init();
        //Initialize Subsystems
        rightFlywheelPIDData = new PIDData(0,0,0);
    	leftFlywheelPIDData = new PIDData(0,0,0);
        drive = new DriveSubsystem(RobotMap.leftDriveController, RobotMap.rightDriveController);
        rightFlywheel = new FlyWheel(RobotMap.rightFlyWheelController, RobotMap.rightFlyWheelEncoder, rightFlywheelPIDData);
        leftFlywheel =  new FlyWheel(RobotMap.leftFlyWheelController, RobotMap.leftFlyWheelEncoder, leftFlywheelPIDData);
        shooter = new DualShooter(leftFlywheel, rightFlywheel, RobotMap.innerShooterWheelController );
        arm = new Arm(RobotMap.armController, RobotMap.armPot);
        oi = new OI();
        isAuton = false;
        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
        session = NIVision.IMAQdxOpenCamera("cam0",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
        crosshair = new NIVision.Rect(10, 10, 100, 100);
        smartDashboardInit();    
    }
	
    public void smartDashboardInit(){
    	//Setup Autonomous Sendable Chooser
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", new ExampleCommand());
        SmartDashboard.putData("Auto mode", chooser);
        
        //Setup network configurable constants
        
        //puts right flywheel PID values on the smart Dashboard
        SmartDashboard.putNumber("Right flywheel kP: ", 0);
        SmartDashboard.putNumber("Right flywheel kI: ", 0);
        SmartDashboard.putNumber("Right flywheel kD: ", 0);
        
        // puts left flywheel PID values on the smart Dashboard
        SmartDashboard.putNumber("Left flywheel kP: ", 0);
        SmartDashboard.putNumber("Left flywheel kI: ", 0);
        SmartDashboard.putNumber("Left flywheel kD: ", 0);  
        
        // puts Drive PID values on the smart Dashboard
    	SmartDashboard.putNumber("Drive kP", 0);
    	SmartDashboard.putNumber("Drive kI", 0.);
    	SmartDashboard.putNumber("Drive kD", 0.);
    }
    
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){
    	//Make sure everything gets disabled
    	isAuton = false;
    	rightFlywheel.spin(0);
    	leftFlywheel.spin(0);
        NIVision.IMAQdxStopAcquisition(session);

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
    	isAuton = true;
        autonomousCommand = new UseFlyWheel(15, 15);
        

		/* String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
		case "My Auto":
			autonomousCommand = new MyAutoCommand();
			break;
		case "Default Auto":
		default:
			autonomousCommand = new ExampleCommand();
			break;
		} */
    	// schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		/* This makes sure that the autonomous stops running when
         teleop starts running. If you want the autonomous to 
         continue until interrupted by another command, remove
         this line or comment it out. */
    	isAuton = false;
        if (autonomousCommand != null) autonomousCommand.cancel();
        NIVision.IMAQdxStartAcquisition(session);
    }

    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        NIVision.IMAQdxGrab(session, frame, 1);
        CameraServer.getInstance().setImage(frame);

    }
    
   
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	isAuton = false;
        LiveWindow.run();
    }
    
}