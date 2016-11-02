package org.usfirst.frc.team1058.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class LowBarDoubleBreach extends CommandGroup {
    
    public  LowBarDoubleBreach() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	addParallel(new SetIntakeAngle(1850));
    	addSequential(new WaitCommand(1));
    	addSequential(new DriveTankAutonomously(-0.5,-0.5,0,false,3.5));
    	addSequential(new DriveTankAutonomously(0,0,0,false,2)); // wait 2sec
    	addSequential(new DriveTankAutonomously(0.5,0.5,0,false,3)); 
    	addSequential(new DriveTankAutonomously(0,0,0,false,1));
    }
}
