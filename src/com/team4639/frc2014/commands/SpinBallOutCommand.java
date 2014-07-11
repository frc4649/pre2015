/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4639.frc2014.commands;

/**
 *
 * @author Prateek Mahajan
 */
public class SpinBallOutCommand extends CommandBase {
    public double timeout;
    public SpinBallOutCommand(double timeout) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(spinner);
        this.timeout=timeout;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        setTimeout(timeout);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        spinner.ballOut();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
