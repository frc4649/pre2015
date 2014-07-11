/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team4639.frc2014.commands;
import edu.wpi.first.wpilibj.Timer;
/**
 *
 * @author Prateek Mahajan
 */
public class DriveMotorTestCommand extends CommandBase {
  private Timer timer;
  private int outputPortIndex;

  public DriveMotorTestCommand() {
    requires(drive);
    timer = new Timer();
  }

  protected void initialize() {
    timer.reset();
    timer.start();
    outputPortIndex = 0;
  }

  protected void execute() {
    if (timer.get() < 5.0) {
      switch (outputPortIndex) {
        case 0:
          motors.setLeftDriveA(1.0);
          break;
        case 1:
          motors.setLeftDriveB(1.0);
          break;
        case 2:
          motors.setRightDriveA(1.0);
          break;
        case 3:
          motors.setRightDriveB(1.0);
          break;
      }
    } else if (timer.get() < 6.0) {
      motors.driveLR(0, 0);
    } else {
      outputPortIndex++;
      timer.reset();
      timer.start();
    }
  }

  protected boolean isFinished() {
    return outputPortIndex > 3;
  }

  protected void end() {
    timer.stop();
  }

  protected void interrupted() {
  }
    
}
