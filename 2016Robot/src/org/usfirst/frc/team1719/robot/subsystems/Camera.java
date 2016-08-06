package org.usfirst.frc.team1719.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Camera extends Subsystem {

	private Servo xServo;
	private Servo yServo;
	private double posX;
	private double posY;
	private boolean xLockedOn;
	private boolean yLockedOn;
	
	// Tables to read from GRIP
    private static final NetworkTable table;
    private static final double ANGLE_SCALE = 180.0D;
	
	public Camera(Servo xServo, Servo yServo) {
		this.xServo = xServo;
		this.yServo = yServo;
	    xLockedOn = yLockedOn = false;
	    posX = posY = 0.0D;
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new AutoCameraLock());
	}
	
	public boolean isXLockedOn() {
		return xLockedOn;
	}
	
	public boolean isYLockedOn() {
		return yLockedOn;
	}
	
	public double getAzimuth() {
		return ANGLE_SCALE * (posX - 0.5D);
	}
	
	public double getAltitude() {
		return ANGLE_SCALE * (posY - 0.5D);
	}

	private class AutoCameraLock extends Command {

		private static final double TOLERANCE = 2.0D;
		private static final double INCREMENT = 0.05D;
		
		@Override
		protected void initialize() {
			
		}

		@Override
		protected void execute() {
			//Update data from GRIP
			
			//Stores area of each contour
	        double[] areas = table.getNumberArray("area", new double[] {});
	        //Store center points of each contour
	        double[] centerX = table.getNumberArray("centerX", new double[] {});
	        double[] centerY = table.getNumberArray("centerY", new double[] {});

	        ///Largest Index
	        int largestContourIndex = 0;
	        for(int i = 1; i < areas.length; i++) {
	        	if (areas[i] > areas[largestContourIndex]) largestContourIndex = i;
	        }

	        if(Math.abs(centerX[largestContourIndex]) < TOLERANCE){
	        	xLockedOn = true;
	        }else{
  	        	xLockedOn = false;
	        	posX -= INCREMENT * Math.signum(centerX[largestContourIndex]);
	        	xServo.set(posX);
	        }
	        if(Math.abs(centerY[largestContourIndex]) < TOLERANCE){
	        	yLockedOn = true;
			}else{
	        	yLockedOn = false;
	        	posY -= INCREMENT * Math.signum(centerY[largestContourIndex]);
	        	yServo.set(posY);
	        }
		}

		@Override
		protected boolean isFinished() {
			return false;
		}

		@Override
		protected void end() {}

		@Override
		protected void interrupted() {
			System.out.println("Camera Tracking Command Was Interrupted");
		}
		
	}
	
	static {
        table = NetworkTable.getTable("GRIP/table2");
    }
}
