/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4639.frc2014.commands;
import edu.wpi.first.wpilibj.Timer;
import com.team4639.lib.Path;
/**
 *
 * @author Prateek Mahajan
 */
public class DrivePathCommand extends CommandBase {
    double heading;
    Path path;
    public DrivePathCommand(Path route, double timeout) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        path = route;
        setTimeout(timeout);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        System.out.println("Init Drive " + Timer.getFPGATimestamp());
        drive.resetEncoders();
        driveController.loadProfile(path.getLeftWheelTrajectory(), path.getRightWheelTrajectory(), 1.0, heading);
        drive.useController(driveController);
        driveController.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        driveController.loadProfileNoReset(path.getLeftWheelTrajectory(), path.getRightWheelTrajectory());
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut() || driveController.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
        System.out.println("Done Drive " + Timer.getFPGATimestamp());
        driveController.disable();
        CommandBase.drive.setLeftRightPower(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
