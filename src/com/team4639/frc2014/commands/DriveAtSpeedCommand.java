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
public class DriveAtSpeedCommand extends CommandBase {
  private double distance;
  private double speed;
  private double timeout;
  private double angle;

  public DriveAtSpeedCommand(double distance, double speed, double angle, double timeout) {
    this.distance = distance;
    this.speed = speed;
    this.angle = angle;
    this.timeout = timeout;
    requires(drive);
  }

  protected void initialize() {
    drive.setSpeedGoal(speed * 12, angle);
    setTimeout(timeout);
  }

  protected void execute() {

  }

  protected boolean isFinished() {
    if (speed >= 0) {
      return (drive.getLeftEncoderDistance() / 12.0 > distance ||
                  drive.getRightEncoderDistance() / 12.0 > distance || isTimedOut());
    } else {
      return (drive.getLeftEncoderDistance() / 12.0 < distance ||
                  drive.getRightEncoderDistance() / 12.0 < distance || isTimedOut());
    }
  }

  protected void end() {
    drive.setLeftRightPower(0, 0);
    drive.setSpeedGoal(0, 0);
    drive.disableControllers();
  }

  protected void interrupted() {
  }
}

