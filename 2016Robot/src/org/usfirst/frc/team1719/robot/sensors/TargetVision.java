package org.usfirst.frc.team1719.robot.sensors;

import java.util.Comparator;
import java.util.Vector;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TargetVision {
    
    /**
     * A 'struct' (as C programmers call it) containing information about a target's
     * relative position to the robot.
     */
    public static class TargetPos {
        public final double distance;
        public final double azimuth;
        public final double altitude;
        public final double targetAngle;
        
        public TargetPos(double _distance, double _azimuth, double _altitude, double _targetAngle) {
            distance = _distance;
            azimuth = _azimuth;
            altitude = _altitude;
            targetAngle = _targetAngle;
        }
    }
    
    /**
     * A comparable 'struct' containing information about a contour that GRIP has analyzed.
     * In a .sort(null), the largest (by convex hull area) will appear first.
     */
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
            return (int) (o.area2 - this.area2);
        }
        @Override
        public int compare(Contour o1, Contour o2) {
            return (int) (o1.area2 - o2.area2);
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
    
    // Image from camera dimensions (in pixels)
    private static final double VIEW_WIDTH_PX = 640.0D; //1280.0D;
    private static final double VIEW_HEIGHT_PX = 480.0D; //720.0D;
    // Camera field of view (in degrees)
    private static final double VIEW_ANGLE_DEG = 61.336D; //Unsure of this
    private static final double VIEW_ANGLE_HEIGHT_DEG = 36.939D;
    private static final double TAN_VIEW_HEIGHT = Math.tan(VIEW_ANGLE_HEIGHT_DEG * Math.PI / 180.0D);
    // Height of camera from the ground
    private static final double CAM_HEIGHT_FT = 1.25;
    private static final double CAM_ANGLE_DEG = 25.0D;
    private static final double TAN_CAM_ANGLE = Math.tan(CAM_ANGLE_DEG * Math.PI / 180.0D);
    // Height of target from the ground
    private static final double TARGET_HEIGHT_FT = 7.583D;
    
    private static final double JEREMYS_CONSTANT_A = 13.76D;
    private static final double JEREMYS_CONSTANT_B = 6394.0D;
    
    // Tables to read from GRIP
    private static final NetworkTable table1;
    private static final NetworkTable table2;
    
    /**
     * Process the contour data from GRIP into a target position
     * @return a TargetPos object detailing the location and orientation of the target relative to the robot
     */
    public static TargetPos detect() {
        // Read data from GRIP network tables
        double[] areas1 = table1.getNumberArray("area", new double[] {});
        double[] areas2 = table2.getNumberArray("area", new double[] {});
        double[] width = table2.getNumberArray("width", new double[] {});
        double[] height = table2.getNumberArray("height", new double[] {});
        double[] posX = table2.getNumberArray("centerX", new double[] {});
        double[] posY = table2.getNumberArray("centerY", new double[] {});
        Vector<Contour> contours = Contour.getContours(areas1, areas2, width, height, posX, posY);
        System.out.println(contours.size());
        if(contours.size() > 0)
        {
            // We only pay attention to the largest (in terms of area) contour
            Contour contour = contours.elementAt(0);
            // Score the contour based on the ratio of its area to that of its convex hull
            double areaScore = scoreArea(contour);
            SmartDashboard.putNumber("Area Score", areaScore);
            System.out.println("Area" + areaScore);
            boolean lock = true;//areaScore > SCORE_MIN;
            System.out.println("LockStatus: "+lock);
            // Is the target detected?
            SmartDashboard.putBoolean("Target Lock", lock);

            System.out.println("Still here");
            // Calculate position of the target from the contour data
            double azimuth = computeTargetAzimuth(contour);
            double altitude = computeTargetAltitude(contour);
            double distanceduncan = computeTargetDistanceD(contour);
            double distancejeremy = computeTargetDistance(contour);
            // And put them on the smart dashboard as well for good measure
            SmartDashboard.putNumber("Target Distance", distanceduncan);
            SmartDashboard.putNumber("Target Azimuth", azimuth);
            SmartDashboard.putNumber("Target Altitude", altitude);
            System.out.println("Distance (Duncan's Method): " + distanceduncan);
            System.out.println("Distance (Jeremy's Method): " + distancejeremy);
            System.out.println("Distance (Ripped off Method): " + computeTargetDistanceRipoff(contour));
            System.out.println("Azimuth" + azimuth);
            System.out.println("Altitude" + altitude);
            System.out.println("Height" + contour.height);
            System.out.println("Highest point" + (contour.posY + (contour.height/2)));
            System.out.println("posY: "+ contour.posY);
            
            
            // Orientation of the target
            double angleToNormal = Math.acos((14.0D/20.0D) * (contour.width / contour.height) * Math.cos(CAM_ANGLE_DEG));
            // Return the position
            return new TargetPos(distanceduncan, azimuth, altitude, angleToNormal);
        } else { // no matching contours found
            SmartDashboard.putBoolean("Target Lock", false);
            return null;
        }
    }
    
    /**
     * convert a ratio (unity is 'ideal') to a percentage score
     * @param ratio the ratio to score
     * @return the percentage score
     */
    private static double ratioToScore(double ratio) {
        return (Math.max(0, Math.min(100*(1-Math.abs(1-ratio)), 100)));
    }

    /**
     * Score the contour based on the ratio of its area to that of its convex hull
     * @param contour the contour to score
     * @return a percentage score of how target-like the contour is
     */
    private static double scoreArea(Contour contour) {
        return ratioToScore((280.0D/88.0D) * contour.area1 / contour.area2);
    }
    
    /**
     * Find the azimuth of the target depicted by a contour relative to to the robot's current facing
     * @param contour the contour for which to compute
     * @return the azimuth (in degrees)
     */
    private static double computeTargetAzimuth(Contour contour) {
        double normalizedPosX = 2.0D * contour.posX / VIEW_WIDTH_PX - 1.0D;
        return (Math.atan(normalizedPosX * Math.tan(VIEW_ANGLE_DEG * Math.PI / (180*2))) * (180.0D / Math.PI));
    }
    
    /**
     * Find the altitude of the target depicted by a contour relative to to the robot's current facing
     * @param contour the contour for which to compute
     * @return the altitude (in degrees)
     */
    private static double computeTargetAltitude(Contour contour) {
        double normalizedPosY = 1.0D - 2.0D * contour.posY / VIEW_HEIGHT_PX;
        return (Math.atan(normalizedPosY * Math.tan(VIEW_ANGLE_HEIGHT_DEG * Math.PI / (180*2))) * (180.0D / Math.PI)) + CAM_ANGLE_DEG;
    }
    
    private static double computeTargetDistance(Contour contour) {
    	return (JEREMYS_CONSTANT_A * (contour.posY + contour.height / 2) + JEREMYS_CONSTANT_B) / contour.height;
    }
    
    private static double computeTargetDistanceD(Contour contour) {
        double normalizedPosY = 1.0D - 2.0D * contour.posY / VIEW_HEIGHT_PX;
        System.out.println("nPosY = " + normalizedPosY);
        return (TARGET_HEIGHT_FT - CAM_HEIGHT_FT) * (1.0D - TAN_CAM_ANGLE * TAN_VIEW_HEIGHT * normalizedPosY) / (TAN_CAM_ANGLE + TAN_VIEW_HEIGHT * normalizedPosY);
    }
    private static double computeTargetDistanceRipoff(Contour contour) {
        double normalizedPosY = 1.0D - 2.0D * contour.posY / VIEW_HEIGHT_PX;
        return (TARGET_HEIGHT_FT - CAM_HEIGHT_FT) / Math.tan((normalizedPosY * VIEW_ANGLE_HEIGHT_DEG / 2.0D + CAM_ANGLE_DEG) * Math.PI / 180.0D);
    }
    
    static {
        // initialize shit
        table1 = NetworkTable.getTable("GRIP/table1");
        table2 = NetworkTable.getTable("GRIP/table2");
    }
}
