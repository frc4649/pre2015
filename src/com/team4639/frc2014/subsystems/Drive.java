/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4639.frc2014.subsystems;


import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import com.team4639.lib.ChezyGyro;
import com.team4639.lib.ControlSource;
import com.team4639.lib.ControlOutput;
import com.team4639.lib.TrapezoidProfile;
import com.team4639.lib.PIDGains;
import com.team4639.frc2014.Constants;
import com.team4639.lib.ProfiledPIDController;
import com.team4639.lib.PIDController;
import com.team4639.frc2014.commands.CheesyDriveCommand;
import com.team4639.lib.MotionProfile;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import com.team4639.lib.ControllerNew;
/**
 *
 * @author Prateek Mahajan
 */
public class Drive extends Subsystem {

  private DriveGearbox motors;
  Timer timer= new Timer();
  private double lastShift=0;
  private double timeDownshift=0;
  private DoubleSolenoid shifter = new DoubleSolenoid(Constants.shifterForward.getInt(),Constants.shifterReverse.getInt());
  private ChezyGyro gyro = new ChezyGyro(Constants.gyro.getInt());
  private boolean isHighGear = true;
  private boolean isAutoShift=true;
  protected ControllerNew controller;
  
  protected class DriveControlSource implements ControlSource {
    boolean straight = true;
    DriveControlSource(boolean straight) {
      this.straight = straight;
    }

    public double get() {
      if (straight) {
        return (getLeftEncoderDistance() + getRightEncoderDistance()) / 2.0;
      } else {
        return getGyroAngle();
      }
    }

    public void updateFilter() {
    }

    public boolean getLowerLimit() {
      return false;
    }

    public boolean getUpperLimit() {
      return false;
    }
  }

  double lastStraight = 0;
  double lastTurn = 0;
  protected class DriveControlOutput implements ControlOutput {
    boolean straight;
    DriveControlOutput(boolean straight) {
      this.straight = straight;
    }

    public void set(double value) {
      if (straight) {
        lastStraight = value;
      } else {
        lastTurn = value;
      }
      setLeftRightPower(lastStraight + lastTurn, lastStraight - lastTurn);
    }
  }

  TrapezoidProfile profile = new TrapezoidProfile(6 * 12.0, .25);
  PIDGains lowStraightGains = new PIDGains(Constants.driveStraightKPLow,
          Constants.driveStraightKILow, Constants.driveStraightKDLow);
  PIDGains highStraightGains = new PIDGains(Constants.driveStraightKPHigh,
          Constants.driveStraightKIHigh, Constants.driveStraightKDHigh);
  ProfiledPIDController straightController = new ProfiledPIDController("straightController",
          highStraightGains,
          new DriveControlSource(true), new DriveControlOutput(true), profile);

  PIDGains lowTurnGains = new PIDGains(Constants.driveTurnKPLow,
          Constants.driveTurnKILow, Constants.driveTurnKDLow);
  PIDGains highTurnGains = new PIDGains(Constants.driveTurnKPHigh,
          Constants.driveTurnKIHigh, Constants.driveTurnKDHigh);
  PIDController turnController = new PIDController("turnController",
          highTurnGains,
          new DriveControlSource(false), new DriveControlOutput(false));;

  public Drive(DriveGearbox motors) {
    super();
    this.motors = motors;
    motors.getLeftEncoder().setDistancePerPulse(0.00409061543436170994591490023865);
    motors.getRightEncoder().setDistancePerPulse(0.00409061543436170994591490023865);
    motors.getLeftEncoder().start();
    motors.getRightEncoder().start();
    disableControllers();
    timer.start();
  }

  protected void initDefaultCommand() {
    setDefaultCommand(new CheesyDriveCommand());
  }

  public void setLeftRightPower(double leftPower, double rightPower) {
    motors.driveLR(leftPower, -rightPower);
  }

  public double getLeftEncoderDistance() {
    return motors.getLeftEncoder().getDistance();
  }

  public double getRightEncoderDistance() {
    return motors.getRightEncoder().getDistance();
  }

  public void useController(ControllerNew c) {
    if (controller != null) {
      controller.disable();
    }
    controller = c;
    if (controller != null) {
      controller.enable();
    }
  }
  
  public void resetEncoders() {
    motors.getLeftEncoder().reset();
    motors.getRightEncoder().reset();
  }

  public double getGyroAngle() {
    return gyro.getAngle();
  }

  public double getGyroAngleInRadians() {
    return (getGyroAngle() * Math.PI) / 180.0;
  }
  int resets = 0;
  public void resetGyro() {
    System.out.println("Resetting gyro!!!!" + resets++);
    gyro.reset();
  }

  public void shift() {
    isAutoShift=false;
    if(shifter.get()==DoubleSolenoid.Value.kReverse)
    {
        isHighGear=true;
        setHighGear();
    }
    else
    {
        isHighGear=false;
        setLowGear();
    }
    lastShift=timer.get();
    straightController.setGains(isHighGear ? highStraightGains : lowStraightGains);
    turnController.setGains(isHighGear ? highTurnGains : lowTurnGains);
  }
  
  public void toggleAutoShift()
  {
      isAutoShift=!isAutoShift;
  }
  
  public boolean isAutoShift()
  {
      return isAutoShift;
  }
  
  public void autoShift()
  {
    double temp;
    double averageSpeed=Math.abs((motors.getLeftEncoder().getRate()+motors.getRightEncoder().getRate())/2);
    if(timer.get()-lastShift>Constants.minTimeBetweenShifts.getDouble())
    {
        if(isHighGear)
        {
          if(averageSpeed>=Constants.downshiftLowSpeed.getDouble())
          {
            temp=0;
          }
          else
          {
            temp=(timer.get()-lastShift)+timeDownshift;
          }
          timeDownshift=temp;
          if(temp>Constants.downshiftLowWaitTime.getDouble())
          {
            setLowGear();   
          }
          if(averageSpeed<Constants.downshiftSpeed.getDouble())
          {
              
          }
              
        }
        else 
        {
          
        }
    }
    lastShift=timer.get();
      
  }
  public void setHighGear()
  {
      shifter.set(DoubleSolenoid.Value.kReverse);
  }
  
  public void setLowGear()
  {
      shifter.set(DoubleSolenoid.Value.kReverse);
  }
  
  public boolean isHighGear() {
    return isHighGear;
  }

  public void disableControllers() {
    straightController.disable();
    turnController.disable();
    setLeftRightPower(0, 0);
  }

  public void setSpeedGoal(double speed, double angle) {
    profile.setMaxVelocity(Math.abs(speed));
    profile.setTimeToMaxV(0.001);
    straightController.setGoal(speed < 0 ? -1000 : 1000);
    straightController.enable();
    turnController.setGoal(angle);
    turnController.enable();
  }

  public void setPositionGoal(double distance, double angle, double speed) {
    profile.setMaxVelocity(speed);
    profile.setTimeToMaxV(.2);
    straightController.setGoal(distance);
    straightController.enable();
    turnController.setGoal(angle);
    turnController.enable();
  }

  public void updatePositionGoal(double distance) {
    straightController.setGoalRaw(distance);
  }

  public void setTurnGoal(double angle) {
    straightController.disable();
    lastStraight = 0;
    turnController.setGoal(angle);
    turnController.enable();
  }

  public void updateTurnGoal(double angle) {
    turnController.setGoalRaw(angle);
  }

  public boolean onTarget() {
    return straightController.onTarget() && turnController.onTarget();
  }

  public void resetControllers() {
    straightController.setProfile(profile);
  }

  public void setStraightProfile(MotionProfile profile) {
    straightController.setProfile(profile);
  }

  public void reinitGyro() {
    gyro.initGyro();
  }
}