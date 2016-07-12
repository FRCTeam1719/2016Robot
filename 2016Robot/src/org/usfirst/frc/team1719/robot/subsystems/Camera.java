package org.usfirst.frc.team1719.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Camera extends Subsystem {

	private Servo mX;
	private Servo mY;
	private double posX;
	private double posY;
	private boolean odX;
	private boolean odY;
	
	// Tables to read from GRIP
    private static final NetworkTable table2;
    private static final double ANGLE_SCALE = 180.0D;
	
	public Camera(Servo _mX, Servo _mY) {
		mX = _mX;
		mY = _mY;
	    odX = odY = true;
	    posX = posY = 0.0D;
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new AutoCameraLock());
	}
	
	public boolean isXOutdated() {
		return odX;
	}
	
	public boolean isYOutdated() {
		return odY;
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
	        double[] areas2 = table2.getNumberArray("area", new double[] {});
	        double[] ctrX = table2.getNumberArray("centerX", new double[] {});
	        double[] ctrY = table2.getNumberArray("centerY", new double[] {});
	        int n = 0;
	        for(int i = 1; i < areas2.length; i++) {
	        	if (areas2[i] > areas2[n]) n = i;
	        }
	        if(Math.abs(ctrX[n]) < TOLERANCE) odX = false;
	        else {
	        	odX = true;
	        	posX -= INCREMENT * Math.signum(ctrX[n]);
	        	mX.set(posX);
	        }
	        if(Math.abs(ctrY[n]) < TOLERANCE) odY = false;
	        else {
	        	odY = true;
	        	posY -= INCREMENT * Math.signum(ctrY[n]);
	        	mY.set(posY);
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
			throw new RuntimeException("AutoCameraLock should not be interrupted");
		}
		
	}
	
	static {
        table2 = NetworkTable.getTable("GRIP/table2");
    }
}
