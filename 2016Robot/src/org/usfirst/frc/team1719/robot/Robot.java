
package org.usfirst.frc.team1719.robot;

import org.usfirst.frc.team1719.robot.commands.ExampleCommand;
import org.usfirst.frc.team1719.robot.commands.UseFlyWheel;
import org.usfirst.frc.team1719.robot.settings.PIDData;
import org.usfirst.frc.team1719.robot.subsystems.Arm;
import org.usfirst.frc.team1719.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1719.robot.subsystems.DualShooter;
import org.usfirst.frc.team1719.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team1719.robot.subsystems.FlyWheel;
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

	final String CAMERA_NAME = "";
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
    public static boolean isAuton = false;
    CameraServer camServer;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		
    	//Network Initialization
	
        //Setup Camera Server
    	camServer = CameraServer.getInstance();
    	camServer.setQuality(50);
    	camServer.startAutomaticCapture(CAMERA_NAME);
    	
    	//Setup Autonomous Sendable Chooser
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", new ExampleCommand());
        SmartDashboard.putData("Auto mode", chooser);
        
        //Setup network configurable constant
        
        //puts right flywheel PID values on the smart Dashboard
        SmartDashboard.putNumber("Right flywheel kP: ", rightFlywheelPIDData.kP);
        SmartDashboard.putNumber("Right flywheel kI: ", rightFlywheelPIDData.kI);
        SmartDashboard.putNumber("Right flywheel kD: ", rightFlywheelPIDData.kD);
        
        // puts left flywheel PID values on the smart Dashboard
        SmartDashboard.putNumber("Left flywheel kP: ", leftFlywheelPIDData.kP);
        SmartDashboard.putNumber("Left flywheel kI: ", leftFlywheelPIDData.kI);
        SmartDashboard.putNumber("Left flywheel kD: ", leftFlywheelPIDData.kD);        
        smartDashboardInit();
        
        //Hardware Initialization
        RobotMap.init();
        rightFlywheelPIDData = new PIDData();
    	leftFlywheelPIDData = new PIDData();
        drive = new DriveSubsystem(RobotMap.leftController, RobotMap.rightController);
        rightFlywheel = new FlyWheel(RobotMap.rightFlyWheelTalon, RobotMap.rightFlyWheelEncoder, rightFlywheelPIDData);
        leftFlywheel =  new FlyWheel(RobotMap.leftFlyWheelTalon, RobotMap.leftFlyWheelEncoder, leftFlywheelPIDData);
        shooter = new DualShooter(leftFlywheel, rightFlywheel, RobotMap.innerIntakeMotorTalon );
        arm = new Arm();
        oi = new OI();
        isAuton = false;
    }
	
    public void smartDashboardInit(){
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
    	isAuton = false;
    	rightFlywheel.spin(0);
    	leftFlywheel.spin(0);
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
    	//rightFlywheel.spin(256);
    	System.out.println("encoder rate: " + RobotMap.rightFlyWheelEncoder.getRate());

        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
    	isAuton = false;
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	isAuton = false;
        LiveWindow.run();
    }
    
}
