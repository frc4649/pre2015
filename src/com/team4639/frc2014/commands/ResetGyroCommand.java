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
public class ResetGyroCommand extends CommandBase {
  protected void initialize() {
    drive.resetGyro();
  }

  protected void execute() {
  }

  protected boolean isFinished() {
    return true;
  }

  protected void end() {
  }

  protected void interrupted() {
  }
}
