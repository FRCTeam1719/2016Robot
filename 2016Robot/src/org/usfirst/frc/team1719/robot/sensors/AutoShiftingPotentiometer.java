package org.usfirst.frc.team1719.robot.sensors;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * AutoShiftingPotentiometer implements a ScaledPotentiometer where the offset is dynamically shifted
 * This is to counteract a slipping potentiometer
 * 
 * @author Aaron
 *
 */
public class AutoShiftingPotentiometer extends ScaledPotentiometer{

	
	/**
	 * The Pot will start at the same position every time the robot is turned on. 
	 * The output of the pot will be shifted to match that
	 * @param channel AnalogInput the pot is on
	 * @param scale multiplier for pot output
	 * @param defaultPosition default angle the pot is at
	 */
	public AutoShiftingPotentiometer(AnalogInput channel, double scale, double defaultOffset) {
		super(channel, scale, 0);
		double offset = 0;
	    String fileName = "offset.txt";
	    String line = null;
		try{
			//Read offset from file
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            // Always close files.
            while((line = bufferedReader.readLine()) != null) {
                offset = Double.parseDouble(line);
            }   
            bufferedReader.close();    
		}catch(IOException e){
			offset = defaultOffset;
		}
		
		super.setOffset(offset);
		System.out.println("offset: "+offset); 
		System.out.println("initRawState: "+super.getRaw());
		System.out.println("defaultPosition: "+defaultOffset);
		System.out.println("offset: "+offset);
	}
	
	public AutoShiftingPotentiometer(int channel, double scale, double defaultPosition) {
		this(new AnalogInput(channel), scale, defaultPosition);
	}
	
	/**
	 * Adjusts the offset of the pot so that it currently reads what is passed 
	 * @param desiredReading 
	 */
	public void adjustShift(double desiredReading){
		double currentState = super.getRaw() * super.getScale();
		double newOffset = -1 * (currentState - desiredReading);
		super.setOffset(newOffset);
		try{
		 	File offsetFile = new File(Double.toString(offset));
		    BufferedWriter writer = new BufferedWriter(new FileWriter(offsetFile));
		    writer.write (Double.toString(newOffset));
		    writer.close();
		}catch(Exception e) {
		    e.printStackTrace();
		}
		
	}
	
	

	
	
	
	
	
}
