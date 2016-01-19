package org.usfirst.frc.team1719.robot;

import org.usfirst.frc.team1719.robot.commands.MoveForwards;
import org.usfirst.frc.team1719.robot.commands.ShootBoulder;
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
		private Joystick rightJoystick;
	
		private Button rotateButton;
		private Button driveStraightButton;
		private Button fireButton;		
		
		public OI(){
			driverXBOX = new Joystick(0);
			rightJoystick = new Joystick(1);
			rotateButton = new JoystickButton(driverXBOX, A_BUTTON);
			rotateButton.whenPressed(new TurnToAngle(90));
			driveStraightButton = new JoystickButton(driverXBOX, B_BUTTON);
			driveStraightButton.whenPressed(new MoveForwards(600));
			fireButton = new JoystickButton(rightJoystick, ATTACK_TRIGGER);
			fireButton.whenPressed(new ShootBoulder(10, 10));
		}
		
	
		public double getLeftReading(){
			return driverXBOX.getRawAxis(LEFT_Y);
		}
		
		public double getRightReading(){
			return driverXBOX.getRawAxis(RIGHT_Y);
		}
		
		public boolean getFireButton()
		{
			return fireButton.get();
		}
		
		
}

