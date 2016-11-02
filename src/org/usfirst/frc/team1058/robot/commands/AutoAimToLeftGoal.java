package org.usfirst.frc.team1058.robot.commands;

import org.usfirst.frc.team1058.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutoAimToLeftGoal extends Command {
	NetworkTable dataTable;
	public static boolean goalLRInRange = false;
	double[] lfGPXA;
	double[] defaultvl;
	double leftGoalPositionX;
	double imageSizeX = 320; // the size of the GRIP RESIZED IMAGE in pixels
	double goalIdealCenterX = imageSizeX/2;
    public AutoAimToLeftGoal() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivebase);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	dataTable = NetworkTable.getTable("GRIP/myContoursReport"); // create a new imageTable with the image data
    	lfGPXA = new double[5];
    	defaultvl = new double[5];
    	lfGPXA[0] = 0;
    	lfGPXA[1] = 0;
    	lfGPXA[2] = 0;
    	lfGPXA[3] = 0;
    	lfGPXA[4] = 0;
    	defaultvl[0] = 0;
    	defaultvl[1] = 0;
    	defaultvl[2] = 0;
    	defaultvl[3] = 0;
    	defaultvl[4] = 0;


    }

    // Called repeatedly when this Command is scheduled to run
	protected void execute() {
    	
    	defaultvl[0] = 1 ;
    	lfGPXA = dataTable.getNumberArray("centerX", defaultvl); // get the contour-ed goal image 
    	try{
    	leftGoalPositionX = lfGPXA[0];
    	}
    	catch(ArrayIndexOutOfBoundsException e){
    		System.out.println("No goal!");
    		goalLRInRange = false;
    	}
    	double tolerance = 8
    			;
 // set default value to -1 when no image is returned, so the robot waits for an image position)
    	SmartDashboard.putNumber("left goal angle", leftGoalPositionX);
    if((leftGoalPositionX > goalIdealCenterX+0.5*tolerance) || (leftGoalPositionX < goalIdealCenterX-0.5*tolerance)){
    	goalLRInRange = false;
    	SmartDashboard.putBoolean("goalLRInRange", false);

    		// if robot is outside of the acceptable goal range....
    	if(leftGoalPositionX >= (goalIdealCenterX+3*tolerance)){ // if goal is at 230 or greater pixels
    		Robot.drivebase.driveTank(0.35, -0.35);
    		
    	}
    	if(leftGoalPositionX > (goalIdealCenterX+tolerance) && leftGoalPositionX < (goalIdealCenterX+3*tolerance)){
    		//if goal position is between 210 and 230
    		Robot.drivebase.driveTank(0.22, -0.22);
    	}
    	if(leftGoalPositionX <= (goalIdealCenterX-3*tolerance)){
    		//if goal position is less than 170
    		Robot.drivebase.driveTank(-0.35, 0.35);
    	}
    	if(leftGoalPositionX < (goalIdealCenterX-tolerance) && leftGoalPositionX > (goalIdealCenterX-3*tolerance)){
    		//if goal position is less than 190 but greater than 170
    		Robot.drivebase.driveTank(-0.22, 0.22);
    	}
    	}
    
    else{
    	goalLRInRange = true;
    	SmartDashboard.putBoolean("goalLRInRange", true);
		    	this.end();
    }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivebase.driveTank(0,0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}
