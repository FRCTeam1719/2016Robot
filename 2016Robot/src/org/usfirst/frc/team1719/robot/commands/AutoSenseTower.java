package org.usfirst.frc.team1719.robot.commands;

import java.util.Comparator;
import java.util.Vector;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutoSenseTower extends Command {

    public class ParticleReport implements Comparator<ParticleReport>, Comparable<ParticleReport>{
        double PercentAreaToImageArea;
        double Area;
        double BoundingRectLeft;
        double BoundingRectTop;
        double BoundingRectRight;
        double BoundingRectBottom;
        
        public int compareTo(ParticleReport r)
        {
            return (int)(r.Area - this.Area);
        }
        
        public int compare(ParticleReport r1, ParticleReport r2)
        {
            return (int)(r1.Area - r2.Area);
        }
    }
    
    private static final String CAM_ID = "cam0";
    private static final double VIEW_ANGLE = 49.4D; //View angle fo camera, set to Axis m1011 by default, 64 for m1013, 51.7 for 206, 52 for HD3000 square, 60 for HD3000 640x480
    private static final double SCORE_MIN = 75.0D;
    private static final double TARGET_WIDTH_IN = 20.0D;
    private static final double OPTIMUM_DISTANCE_FT = 10.0D;
    
    Image frame;
    Image binaryFrame;
    int session;
    NIVision.Range h_rng;
    NIVision.Range s_rng;
    NIVision.Range v_rng;
    
    NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
    NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);
    
    public AutoSenseTower() {
        // Use requires() here to declare subsystem dependencies
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
        binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
        session = NIVision.IMAQdxOpenCamera(CAM_ID, NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
        NIVision.IMAQdxStartAcquisition(session);
        h_rng = new NIVision.Range((int) SmartDashboard.getNumber("Hue Min"), (int) SmartDashboard.getNumber("Hue Max"));
        s_rng = new NIVision.Range((int) SmartDashboard.getNumber("Sat Min"), (int) SmartDashboard.getNumber("Sat Max"));
        v_rng = new NIVision.Range((int) SmartDashboard.getNumber("Val Min"), (int) SmartDashboard.getNumber("Val Max"));
        criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, (float)SmartDashboard.getNumber("Area min %"), 100.0, 0, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        NIVision.IMAQdxGrab(session, frame, 1);
        NIVision.imaqColorThreshold(binaryFrame, frame, 255, NIVision.ColorMode.HSV, h_rng, s_rng, v_rng);
        
        int numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
        SmartDashboard.putNumber("Masked particles", numParticles);

        CameraServer.getInstance().setImage(binaryFrame);

        NIVision.imaqParticleFilter4(binaryFrame, binaryFrame, criteria, filterOptions, null);

        numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
        SmartDashboard.putNumber("Filtered particles", numParticles);
        
        if(numParticles > 0)
        {
            //Measure particles and sort by particle size
            Vector<ParticleReport> particles = new Vector<ParticleReport>();
            for(int particleIndex = 0; particleIndex < numParticles; particleIndex++)
            {
                ParticleReport par = new ParticleReport();
                par.PercentAreaToImageArea = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
                par.Area = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA);
                par.BoundingRectTop = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
                par.BoundingRectLeft = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
                par.BoundingRectBottom = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
                par.BoundingRectRight = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
                particles.add(par);
            }
            particles.sort(null);

            //This example only scores the largest particle. Extending to score all particles and choosing the desired one is left as an exercise
            //for the reader. Note that this scores and reports information about a single particle (single L shaped target). To get accurate information 
            //about the location of the tote (not just the distance) you will need to correlate two adjacent targets in order to find the true center of the tote.
            double aspectScore = scoreAspect(particles.elementAt(0));
            SmartDashboard.putNumber("Aspect Score", aspectScore);
            double areaScore = scoreArea(particles.elementAt(0));
            SmartDashboard.putNumber("Area Score", aspectScore);
            boolean lock = aspectScore > SCORE_MIN && areaScore > SCORE_MIN;

            //Send distance and tote status to dashboard. The bounding rect, particularly the horizontal center (left - right) may be useful for rotating/driving towards a tote
            SmartDashboard.putBoolean("Target Lock", lock);
            if(!lock) {
                //turn to try to aim
            }
            double distance = computeDistance(binaryFrame, particles.elementAt(0));
            SmartDashboard.putNumber("Distance", distance);
            double normalizedDistance = distance / OPTIMUM_DISTANCE_FT;
            
        } else {
            SmartDashboard.putBoolean("Target Lock", false);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        NIVision.IMAQdxStopAcquisition(session);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        NIVision.IMAQdxStopAcquisition(session);
    }
    
    double ratioToScore(double ratio)
    {
        return (Math.max(0, Math.min(100*(1-Math.abs(1-ratio)), 100)));
    }

    double scoreArea(ParticleReport report)
    {
        double boundingArea = (report.BoundingRectBottom - report.BoundingRectTop) * (report.BoundingRectRight - report.BoundingRectLeft);
        
        return ratioToScore((280.0D/88.0D)*report.Area/boundingArea);
    }

    double scoreAspect(ParticleReport report)
    {
        return ratioToScore((14.0D/20.0D)*((report.BoundingRectRight-report.BoundingRectLeft)/(report.BoundingRectBottom-report.BoundingRectTop)));
    }
    
    /**
     * Computes the estimated distance to a target using the width of the particle in the image. For more information and graphics
     * showing the math behind this approach see the Vision Processing section of the ScreenStepsLive documentation.
     *
     * @param image The image to use for measuring the particle estimated rectangle
     * @param report The Particle Analysis Report for the particle
     * @param isLong Boolean indicating if the target is believed to be the long side of a tote
     * @return The estimated distance to the target in feet.
     */
    double computeDistance(Image image, ParticleReport report) {
        double normalizedWidth;
        NIVision.GetImageSizeResult size;

        size = NIVision.imaqGetImageSize(image);
        normalizedWidth = 2*(report.BoundingRectRight - report.BoundingRectLeft)/size.width;

        return  TARGET_WIDTH_IN/(normalizedWidth*12*Math.tan(VIEW_ANGLE*Math.PI/(180*2)));
    }
}
