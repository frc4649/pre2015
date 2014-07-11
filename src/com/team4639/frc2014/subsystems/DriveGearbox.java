/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4639.frc2014.subsystems;


import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Encoder;
import com.team4639.frc2014.Constants;
/**
 *
 * @author Prateek Mahajan
 */
public class DriveGearbox extends Subsystem{
  // Speed controllers
  private final Victor leftDriveA = new Victor(Constants.leftFront.getInt());
  private final Victor leftDriveB = new Victor(Constants.leftRear.getInt());
  private final Victor rightDriveA = new Victor(Constants.rightFront.getInt());
  private final Victor rightDriveB = new Victor(Constants.rightRear.getInt());

  // Sensors
  private final Encoder leftEncoder = new Encoder(Constants.leftEncoderA.getInt(),
          Constants.leftEncoderB.getInt(), true);
  private final Encoder rightEncoder = new Encoder(Constants.rightEncoderA.getInt(),
          Constants.rightEncoderB.getInt());

  private boolean isDriveMode = true;

  private boolean zeroed = false;

  public Encoder getLeftEncoder() {
    return leftEncoder;
  }

  public Encoder getRightEncoder() {
    return rightEncoder;
  }

  public void driveLR(double leftPower, double rightPower){
    if (isDriveMode) {
      leftDriveA.set(leftPower);
      leftDriveB.set(leftPower);
      rightDriveA.set(rightPower);
      rightDriveB.set(rightPower);
    }
  }

  public void set(double allPower){
    if (!isDriveMode) {
      if (allPower  < 0 && leftEncoder.get() < -3190) // go up power, go down encoder
        allPower = 0;
      leftDriveA.set(allPower);
      leftDriveB.set(allPower);
      rightDriveA.set(-allPower);
      rightDriveB.set(-allPower);
    }
  }

  public void setLeftDriveA(double power) {
    leftDriveA.set(power);
  }

  public void setLeftDriveB(double power) {
    leftDriveB.set(power);
  }

  public void setRightDriveA(double power) {
    rightDriveA.set(power);
  }

  public void setRightDriveB(double power) {
    rightDriveB.set(power);
  }

  public void setDriveMode(boolean wantsDrive) {
    isDriveMode = wantsDrive;
    if (!isDriveMode && !zeroed) {
      System.out.println("ZERORING LEFT ENCODER in motors");
      leftEncoder.reset();
      zeroed = true;
    }
  }

  public boolean isDriveMode() {
    return isDriveMode;
  }

  public double getLeftPower() {
    return leftDriveA.get();
  }

  public double getRightPower() {
    return rightDriveA.get();
  }


  protected void initDefaultCommand() {
  }
}