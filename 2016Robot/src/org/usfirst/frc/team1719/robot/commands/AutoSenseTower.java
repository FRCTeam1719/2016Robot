package org.usfirst.frc.team1719.robot.commands;

import java.util.Comparator;
import java.util.Vector;

import org.usfirst.frc.team1719.robot.Robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ParticleReport;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutoSenseTower extends Command {
    
    private static class Contour implements Comparator<Contour>, Comparable<Contour> {
        final double area1, area2, width, height, posX, posY;
        Contour(double _area1, double _area2, double _width, double _height, double _posX, double _posY) {
            area1 = _area1;
            area2 = _area2;
            width = _width;
            height = _height;
            posX = _posX;
            posY = _posY;
        }
        @Override
        public int compareTo(Contour o) {
            return (int) (o.area1 - this.area1);
        }
        @Override
        public int compare(Contour o1, Contour o2) {
            return (int) (o1.area1 - o2.area1);
        }
        static Vector<Contour> getContours(double[] areas1, double[] areas2, double[] width, double[] height, double[] posX, double[] posY) {
            Vector<Contour> set = new Vector<Contour>();
            for(int i = 0; i < areas1.length; i++) {
                set.add(new Contour(areas1[i], areas2[i], width[i], height[i], posX[i], posY[i]));
            }
            set.sort(null);
            return set;
        }
    }
    
    private static final double VIEW_WIDTH_PX = 1000.0D;
    private static final double VIEW_HEIGHT_PX = 700.0D;
    private static final double VIEW_ANGLE_DEG = 49.4D; //View angle fo camera, set to Axis m1011 by default, 64 for m1013, 51.7 for 206, 52 for HD3000 square, 60 for HD3000 640x480
    private static final double VIEW_ANGLE_HEIGHT_DEG = 50.0D;
    private static final double CAM_HEIGHT_FT = 1.0D;
    private static final double SCORE_MIN = 75.0D;
    private static final double TARGET_WIDTH_IN = 20.0D;
    private static final double MIN_DISTANCE_FT = 2.0D;
    private static final double MAX_DISTANCE_FT = 10.0D;
    private static final double TARGET_HEIGHT_FT = 7.583D;
    private static final double AZIMUTH_ACC_DEG = 10.0D;
    private static final double ANGLE_ACC_DEG = 10.0D;
    
    boolean done;
    private NetworkTable table1;
    private NetworkTable table2;
    
    public AutoSenseTower() {
        // Use requires() here to declare subsystem dependencies
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        done = false;
        table1 = NetworkTable.getTable("GRIP/table1");
        table2 = NetworkTable.getTable("GRIP/table2");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        System.out.println("Begin AutoSenseTower loop");
        double[] areas1 = table1.getNumberArray("area", new double[] {});
        double[] areas2 = table2.getNumberArray("area", new double[] {});
        double[] width = table2.getNumberArray("width", new double[] {});
        double[] height = table2.getNumberArray("height", new double[] {});
        double[] posX = table2.getNumberArray("centerX", new double[] {});
        double[] posY = table2.getNumberArray("centerY", new double[] {});
        System.out.println("prevector" + areas1.length);
        Vector<Contour> contours = Contour.getContours(areas1, areas2, width, height, posX, posY);
        System.out.println("Contours: " + contours.size());
        if(contours.size() > 0)
        {

            //This example only scores the largest particle. Extending to score all particles and choosing the desired one is left as an exercise
            //for the reader. Note that this scores and reports information about a single particle (single L shaped target). To get accurate information 
            //about the location of the tote (not just the distance) you will need to correlate two adjacent targets in order to find the true center of the tote.
            double aspectScore = scoreAspect(contours.elementAt(0));
            SmartDashboard.putNumber("Aspect Score", aspectScore);
            System.out.println("Aspect" + aspectScore);
            double areaScore = scoreArea(contours.elementAt(0));
            SmartDashboard.putNumber("Area Score", aspectScore);
            System.out.println("Area" + areaScore);
            boolean lock = aspectScore > SCORE_MIN && areaScore > SCORE_MIN;

            //Send distance and tote status to dashboard. The bounding rect, particularly the horizontal center (left - right) may be useful for rotating/driving towards a tote
            SmartDashboard.putBoolean("Target Lock", lock);
            if(!lock) {
                //turn to try to find target
                return;
            }
            double widthcalc_distance = computeDistance(contours.elementAt(0));
            double azimuth = computeTargetAzimuth(contours.elementAt(0));
            double altitude = computeTargetAltitude(contours.elementAt(0));
            double altcalc_distance = (TARGET_HEIGHT_FT - CAM_HEIGHT_FT) / Math.tan((Math.PI / 180.0D) * altitude);
            SmartDashboard.putNumber("Target Distance", altcalc_distance);
            SmartDashboard.putNumber("Target Azimuth", azimuth);
            SmartDashboard.putNumber("Target Altitude", altitude);
            System.out.println("Distance" + altcalc_distance);
            System.out.println("Azimuth" + azimuth);
            System.out.println("Altitude" + altitude);
            double angleToNormal = Math.acos(widthcalc_distance / altcalc_distance);
            if((altcalc_distance > MIN_DISTANCE_FT) && (altcalc_distance < MAX_DISTANCE_FT) && (azimuth < AZIMUTH_ACC_DEG) && (angleToNormal < ANGLE_ACC_DEG)) {
                Robot.weapon.aimAndFire(altcalc_distance, TARGET_HEIGHT_FT + 0.9D);
                done = true;
                return;
            }
            // aim at target
        } else {
            SmartDashboard.putBoolean("Target Lock", false);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
        
        done = false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        
    }
    
    double ratioToScore(double ratio) {
        return (Math.max(0, Math.min(100*(1-Math.abs(1-ratio)), 100)));
    }

    double scoreArea(Contour contour) {
        return ratioToScore((280.0D/88.0D) * contour.area1 / contour.area2);
    }

    double scoreAspect(Contour contour) {
        return ratioToScore((14.0D/20.0D) * (contour.width / contour.height));
    }
    
    double computeDistance(Contour contour) {
        double normalizedWidth;
        normalizedWidth = 2.0D * contour.width / VIEW_WIDTH_PX;

        return  TARGET_WIDTH_IN / (normalizedWidth * 12 * Math.tan(VIEW_ANGLE_DEG * Math.PI / (180*2)));
    }
    
    double computeTargetAzimuth(Contour contour) {
        double normalizedPosX = 2.0D * contour.posX / VIEW_WIDTH_PX - 1.0D;
        return (Math.atan(normalizedPosX * Math.tan(VIEW_ANGLE_DEG*Math.PI/(180*2))) * (180.0D / Math.PI));
    }
    
    double computeTargetAltitude(Contour contour) {
        double normalizedPosY = 2.0D * contour.posY / VIEW_HEIGHT_PX - 1.0D;
        return (Math.atan(normalizedPosY * Math.tan(VIEW_ANGLE_HEIGHT_DEG * Math.PI / (180*2))) * (180.0D / Math.PI));
    }
}
