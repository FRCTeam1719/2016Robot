package org.usfirst.frc.team1719.robot;

import org.usfirst.frc.team1719.robot.commands.ManualShoot;
import org.usfirst.frc.team1719.robot.commands.MoveArmToPos;
import org.usfirst.frc.team1719.robot.commands.RunIntake;
import org.usfirst.frc.team1719.robot.commands.TurnToAngle;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
	
	//LOGITECH ATTACK 3 BINDINGS
		final int ATTACK_X_AXIS = 0;
		final int ATTACK_Y_AXIS = 1;
		final int ATTACK_TRIGGER = 1;
		final int ATTACK_BUTTON_2 = 2;
		final int ATTACK_BUTTON_3 = 3;
		final int ATTACK_BUTTON_4 = 4;
		final int ATTACK_BUTTON_5 = 5;
		final int ATTACK_BUTTON_6 = 6;
		final int ATTACK_BUTTON_7 = 7;
		final int ATTACK_BUTTON_8 = 8;
		final int ATTACK_BUTTON_9 = 9;
		final int ATTACK_BUTTON_10 = 10;
		final int ATTACK_BUTTON_11 = 11;
	
	//XBOX BINDINGS
		final int LEFT_X = 0;
		final int LEFT_Y = 1;
		final int LEFT_TRIGGER = 2;
		final int RIGHT_TRIGGER = 3;
		final int RIGHT_X = 4;
		final int RIGHT_Y = 5;
		final int A_BUTTON = 1;
		final int B_BUTTON = 2;
		final int X_BUTTON = 3;
		final int Y_BUTTON = 4;
		final int LEFT_BUMPER = 5;
		final int RIGHT_BUMPER = 6;
		final int BACK_BUTTON = 7;
		final int START_BUTTON = 8;
		final int LEFT_BUTTON = 9;
		final int RIGHT_BUTTON = 10;
		private Joystick driverXBOX;
		private Joystick operatorJoystick;
	
		private Button rotateRight90Button;
		private Button rotateLeft90Button;
		private Button rotate180Button;
		private Button fireButton;			
		private Button armToPos45;
//		private Button moveArmButton;
		private Button primeButton;
		private Button intakeButton;
		
		private Button lowerButton;
		public OI(){
			//Define Controllers
			driverXBOX = new Joystick(0);
			operatorJoystick = new Joystick(1);
			
			//Define Buttons
			rotateRight90Button = new JoystickButton(driverXBOX, X_BUTTON);
			rotateRight90Button.whenPressed(new TurnToAngle(90,true));
			rotateLeft90Button = new JoystickButton(driverXBOX, B_BUTTON);
			rotate180Button = new JoystickButton(driverXBOX, Y_BUTTON);
			rotate180Button.whenPressed(new TurnToAngle(90, true));

			fireButton = new JoystickButton(operatorJoystick, ATTACK_TRIGGER);
			primeButton = new JoystickButton(operatorJoystick, ATTACK_BUTTON_5);
			primeButton.whenPressed(new ManualShoot());
			//moveArmButton = new JoystickButton(operatorJoystick, ATTACK_BUTTON_6);
			//moveArmButton.whenPressed(new MoveArmToPos(60));
			intakeButton = new JoystickButton(operatorJoystick, ATTACK_BUTTON_2);
			intakeButton.whenPressed(new RunIntake());
			armToPos45 = new JoystickButton(operatorJoystick, ATTACK_BUTTON_6);
			armToPos45.whenPressed(new MoveArmToPos(45));
			
			lowerButton = new JoystickButton(operatorJoystick, ATTACK_BUTTON_3);
		}
		
		//Functions for getting input
		
		/**
		 * Grab the left Y axis from the Driver XBox
		 * @return Axis Reading
		 */
		public double getLeftDriveReading(){
			return driverXBOX.getRawAxis(LEFT_Y);
		}
		
		/**
		 * Grab the right Y axis from the Driver XBox
		 * @return Axis Reading
		 */
		public double getRightDriveReading(){
			return driverXBOX.getRawAxis(RIGHT_Y);
		}
		
		/**
		 * Grab the Y axis from the operator controller for driving the arm
		 * @return Axis Reading
		 */
		public double getArmReading(){
			return operatorJoystick.getRawAxis(ATTACK_Y_AXIS);
		}
		
		
		public boolean getFireButton()
		{
			return fireButton.get();
		}
		
		public boolean getIntakeButton(){
			return intakeButton.get();
		}
		
		public boolean getDontStopButton(){
			return rotateLeft90Button.get();
		}
		
		public boolean getLowerButton() {
			return lowerButton.get();
		}
		
}