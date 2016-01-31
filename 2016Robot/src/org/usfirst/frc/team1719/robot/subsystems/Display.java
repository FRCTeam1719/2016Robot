package org.usfirst.frc.team1719.robot.subsystems;

import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team1719.robot.commands.DisplayVoltage;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Display extends Subsystem {
	DigitalInput buttonA;
	DigitalInput buttonB;
	AnalogPotentiometer dial;
	I2C i2c = new I2C(Port.kMXP, 0x70);
			//DigitalInput buttonA = new DigitalInput(9);
			//DigitalInput buttonB = new DigitalInput(10);
			final String defaultAuto = "default.";
			final String customAuto = "Other.";
			String autoSelected;
			SendableChooser chooser;
	public static final Map<Character, byte[]> map;
	public Display(DigitalInput buttonA, DigitalInput buttonB, AnalogPotentiometer dial) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		byte[] osc = new byte[1];
		byte[] blink = new byte[1];
		byte[] bright = new byte[1];
		osc[0] = (byte)0x21;
		blink[0] = (byte)0x81;
		bright[0] = (byte)0xEF;
		
		i2c.writeBulk(osc);
		Timer.delay(.01);
		i2c.writeBulk(blink);
		Timer.delay(.01);
		i2c.writeBulk(bright);
		this.buttonA = buttonA;
		this.buttonB = buttonB;
		this.dial = dial;
    	//SmartDashboard.putString("DB/String 1", "ButtonA = " + buttonA.get());
    	SmartDashboard.putString("DB/String 2", "ButtonB = " + buttonB.get());
    }
    public void displayString(String s){
    	boolean dotMarker[] = new boolean[4];
    	int dotIndex = s.indexOf('.') -1;
    	System.out.println("Dot Index: "+dotIndex);
    	
    	if(dotIndex>0){
    		dotMarker[dotIndex] = true;
    		String split[] = s.split("\\.");
    		s = split[0] + split[1];
    	}
    	
    	
    	byte[] toSend = new byte[10];
    	toSend[0] = (byte)(0b0000111100001111);
    	toSend[1] = (byte)(0b00000000) & 0xFF;
    	for(int i = 0; i < 4; i++){ try {
    		char c = s.toUpperCase().charAt(i);
    		if(dotMarker[i]){
    			byte byteWithDot[] = addDot(map.get(c));
    			System.arraycopy(byteWithDot, 0, toSend, 8-2*i, 2);
    		}else{
    			System.arraycopy(map.get(c), 0, toSend, 8-2*i, 2);
    		}
    	}catch(Exception ex) {}
    	}
    	i2c.writeBulk(toSend);
    }

    
    public byte[] addDot(byte[] input){
    	input[1] = (byte) (input[1] | 0b001000000);
    	return input;
    }
    
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new DisplayVoltage());
	}
	
	public boolean buttonAPressed(){
		return buttonA.get();
	}
	
	public boolean buttonBPressed(){
		return buttonB.get();
	}
	
	public double getDialReading(){
		return dial.get();
	}
	
	static {
		map = new HashMap<Character, byte[]>();
		map.put('0', new byte[] {(byte)0b00111111, (byte)0b00000000});
		map.put('1', new byte[] {(byte)0b00000110, (byte)0b00000000});
		map.put('2', new byte[] {(byte)0b11011011, (byte)0b00000000});
		map.put('3', new byte[] {(byte)0b11001111, (byte)0b00000000});
		map.put('4', new byte[] {(byte)0b11100110, (byte)0b00000000});
		map.put('5', new byte[] {(byte)0b11101101, (byte)0b00000000});
		map.put('6', new byte[] {(byte)0b11111101, (byte)0b00000000});
		map.put('7', new byte[] {(byte)0b00000111, (byte)0b00000000});
		map.put('8', new byte[] {(byte)0b11111111, (byte)0b00000000});
		map.put('9', new byte[] {(byte)0b11101111, (byte)0b00000000});
		map.put('A', new byte[] {(byte)0b11110111, (byte)0b00000000});
		map.put('B', new byte[] {(byte)0b10001111, (byte)0b00010010});
		map.put('C', new byte[] {(byte)0b00111001, (byte)0b00000000});
		map.put('D', new byte[] {(byte)0b00001111, (byte)0b00010010});
		map.put('E', new byte[] {(byte)0b11111001, (byte)0b00000000});
		map.put('F', new byte[] {(byte)0b11110001, (byte)0b00000000});
		map.put('G', new byte[] {(byte)0b10111101, (byte)0b00000000});
		map.put('H', new byte[] {(byte)0b11110110, (byte)0b00000000});
		map.put('I', new byte[] {(byte)0b00001001, (byte)0b00010010});
		map.put('J', new byte[] {(byte)0b00011110, (byte)0b00000000});
		map.put('K', new byte[] {(byte)0b01110000, (byte)0b00001100});
		map.put('L', new byte[] {(byte)0b00111000, (byte)0b00000000});
		map.put('M', new byte[] {(byte)0b00110110, (byte)0b00000101});
		map.put('N', new byte[] {(byte)0b00110110, (byte)0b00001001});
		map.put('O', new byte[] {(byte)0b00111111, (byte)0b00000000});
		map.put('P', new byte[] {(byte)0b11110011, (byte)0b00000000});
		map.put('Q', new byte[] {(byte)0b00111111, (byte)0b00001000});
		map.put('R', new byte[] {(byte)0b11110011, (byte)0b00001000});
		map.put('S', new byte[] {(byte)0b10001101, (byte)0b00000001});
		map.put('T', new byte[] {(byte)0b00000001, (byte)0b00010010});
		map.put('U', new byte[] {(byte)0b00111110, (byte)0b00000000});
		map.put('V', new byte[] {(byte)0b00110000, (byte)0b00100100});
		map.put('W', new byte[] {(byte)0b00110110, (byte)0b00101000});
		map.put('X', new byte[] {(byte)0b00000000, (byte)0b00101101});
		map.put('Y', new byte[] {(byte)0b00000000, (byte)0b00010101});
		map.put('Z', new byte[] {(byte)0b00001001, (byte)0b00100100});
		map.put(' ', new byte[] {(byte)0b00000000, (byte)0b00000000});
		//map.put('.', new byte[] {(byte)0b00000000, (byte)0b01000000});
	}
}
