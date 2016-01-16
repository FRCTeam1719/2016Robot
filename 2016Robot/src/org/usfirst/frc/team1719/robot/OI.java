package org.usfirst.frc.team1719.robot;

import org.usfirst.frc.team1719.robot.commands.MoveArmToPos;
import org.usfirst.frc.team1719.robot.commands.toggleShifters;

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
	
		private Joystick leftJoystick;
		private Joystick rightJoystick;
	
		private Button toggleButton;
		private Button moveArmButton;
		public OI(){
			leftJoystick = new Joystick(0);
			rightJoystick = new Joystick(1);
			toggleButton = new JoystickButton(leftJoystick, ATTACK_TRIGGER);
			toggleButton.whenPressed(new toggleShifters());
			
			moveArmButton = new JoystickButton(leftJoystick, ATTACK_BUTTON_4);
			moveArmButton.whenPressed(new MoveArmToPos(60));
		}
		
	
		public double getLeftReading(){
			return leftJoystick.getRawAxis(ATTACK_Y_AXIS);
		}
		
		public double getRightReading(){
			return rightJoystick.getRawAxis(ATTACK_Y_AXIS);
		}
		
		
}

