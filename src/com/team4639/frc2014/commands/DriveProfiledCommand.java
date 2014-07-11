/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team4639.frc2014.commands;
import com.team4639.lib.MotionProfile;
/**
 *
 * @author Prateek Mahajan
 */
public class DriveProfiledCommand extends CommandBase {
  private double distance;
  private double speed;
  private double timeout;
  private double angle;
  MotionProfile profile = null;
  boolean doEnd = true;

  public DriveProfiledCommand(double distance, double speed, double angle, double timeout, boolean doEnd) {
    requires(drive);
    this.distance = distance;
    this.speed = speed;
    this.timeout = timeout;
    this.angle = angle;
    this.doEnd = doEnd;
  }

   public DriveProfiledCommand(double distance, double speed, double angle, double timeout) {
     this(distance, speed, angle, timeout, true);
   }

  public DriveProfiledCommand(double distance, double speed, double angle, double timeout,
                              MotionProfile profile) {
    this(distance, speed, angle, timeout);
    this.profile = profile;
  }

  protected void initialize() {
    if (profile != null) {
      drive.setStraightProfile(profile);
    }
    drive.setPositionGoal(distance * 12, angle, speed * 12);  // Drive controller works in inches
    setTimeout(timeout);
  }

  protected void execute() {
  }

  protected boolean isFinished() {
    return doEnd && (drive.onTarget() || isTimedOut());
  }

  protected void end() {
    drive.disableControllers();
    drive.resetControllers();
  }

  protected void interrupted() {
  }
}
