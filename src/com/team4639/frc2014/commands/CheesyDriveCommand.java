package com.team4639.frc2014.commands;

import com.team4639.frc2014.Constants;
import com.team4639.lib.ThrottledPrinter;
import com.team4639.lib.Util;
import com.sun.squawk.util.MathUtils;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * Controls the robot drive train using the standard Team 254 scheme.
 *
 * @author richard@team254.com (Richard Lin)
 */
public class CheesyDriveCommand extends CommandBase {
  private double oldWheel = 0.0;
  private double quickStopAccumulator;
  private final double throttleDeadband = 0.02;
  private final double wheelDeadband = 0.02;
  ThrottledPrinter p = new ThrottledPrinter(.1);

  public CheesyDriveCommand() {
    requires(drive);
  }

  protected void initialize() {
    drive.disableControllers() ;
  }

  protected void execute() {
    if (DriverStation.getInstance().isAutonomous()) {
      return;
    }
    boolean isQuickTurn = controlBoard.getQuickTurn();
    boolean isHighGear=drive.isHighGear();
    double wheelNonLinearity;
    double wheel = handleDeadband(controlBoard.mainJoystick.getRawAxis(1), wheelDeadband);
    double throttle = -handleDeadband(controlBoard.mainJoystick.getRawAxis(3), throttleDeadband);

    double negInertia = wheel - oldWheel;
    oldWheel = wheel;

    if (isHighGear) {
      wheelNonLinearity = 0.6;
      // Apply a sin function that's scaled to make it feel better.
      wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) /
          Math.sin(Math.PI / 2.0 * wheelNonLinearity);
      wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) /
          Math.sin(Math.PI / 2.0 * wheelNonLinearity);
    } else {
      wheelNonLinearity = 0.5;
      // Apply a sin function that's scaled to make it feel better.
      wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) /
          Math.sin(Math.PI / 2.0 * wheelNonLinearity);
      wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) /
          Math.sin(Math.PI / 2.0 * wheelNonLinearity);
      wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) /
          Math.sin(Math.PI / 2.0 * wheelNonLinearity);
    }

    double leftPwm, rightPwm, overPower;
    double sensitivity = 1.7;

    double angularPower;
    double linearPower;

    // Negative inertia!
    double negInertiaAccumulator = 0.0;
    double negInertiaScalar;
    if (isHighGear) {
      negInertiaScalar = 5.0;
      sensitivity = Constants.sensitivityHigh.getDouble();
    } else {
      if (wheel * negInertia > 0) {
        negInertiaScalar = 2.5;
      } else {
        if (Math.abs(wheel) > 0.65) {
          negInertiaScalar = 5.0;
        } else {
          negInertiaScalar = 3.0;
        }
      }
      sensitivity = Constants.sensitivityLow.getDouble();

      if (Math.abs(throttle) > 0.1) {
       // sensitivity = 1.0 - (1.0 - sensitivity) / Math.abs(throttle);
      }
    }
    double negInertiaPower = negInertia * negInertiaScalar;
    negInertiaAccumulator += negInertiaPower;

    wheel = wheel + negInertiaAccumulator;
    if (negInertiaAccumulator > 1) {
      negInertiaAccumulator -= 1;
    } else if (negInertiaAccumulator < -1) {
      negInertiaAccumulator += 1;
    } else {
      negInertiaAccumulator = 0;
    }
    linearPower = throttle;

    // Quickturn!
    if (isQuickTurn) {
      if (Math.abs(linearPower) < 0.2) {
        double alpha = 0.1;
        quickStopAccumulator = (1 - alpha) * quickStopAccumulator + alpha *
            Util.limit(wheel, 1.0) * 5;
      }
      overPower = 1.0;
      if (isHighGear) {
        sensitivity = 1.0;
      } else {
        sensitivity = 1.0;
      }
      angularPower = wheel;
    } else {
      overPower = 0.0;
      angularPower = Math.abs(throttle) * wheel * sensitivity - quickStopAccumulator;
      if (quickStopAccumulator > 1) {
        quickStopAccumulator -= 1;
      } else if (quickStopAccumulator < -1) {
        quickStopAccumulator += 1;
      } else {
        quickStopAccumulator = 0.0;
      }
    }

    rightPwm = leftPwm = linearPower;
    leftPwm += angularPower;
    rightPwm -= angularPower;

    if (leftPwm > 1.0) {
      rightPwm -= overPower * (leftPwm - 1.0);
      leftPwm = 1.0;
    } else if (rightPwm > 1.0) {
      leftPwm -= overPower * (rightPwm - 1.0);
      rightPwm = 1.0;
    } else if (leftPwm < -1.0) {
      rightPwm += overPower * (-1.0 - leftPwm);
      leftPwm = -1.0;
    } else if (rightPwm < -1.0) {
      leftPwm += overPower * (-1.0 - rightPwm);
      rightPwm = -1.0;
    }

    setLinearPower(leftPwm, rightPwm);
  }
  
  private double linearize(double x)
  {
      if(Math.abs(x)<0.01)
          x=0.0;
      if(x>0.0)
          return 4.5504*MathUtils.pow(x, 4)+-5.9762*MathUtils.pow(x, 3)+2.5895*MathUtils.pow(x,2)+-0.0869*x+0.0913;
      else if(x<0.0)
          return -linearize(-x);
      else
          return 0.0;
  }
  
  public void setLinearPower(double left, double right)
  {
      double linearLeft=linearize(left);
      double linearRight=linearize(right);
      linearLeft=(linearLeft>1.0)? 1.0: (linearLeft<-1)?-1.0:linearLeft;
      linearRight=(linearRight>1.0)? 1.0: (linearRight<-1)?-1.0:linearRight;
      drive.setLeftRightPower(linearLeft, linearRight);
      if(controlBoard.getAutoShift())
        drive.toggleAutoShift();
      if(drive.isAutoShift())
        drive.autoShift(linearLeft,linearRight);
      else if(controlBoard.getShift())
        drive.shift();
      drive.setExecution();
  }

  protected boolean isFinished() {
    return false;
  }

  protected void end() {
  }

  protected void interrupted() {
  }

  public double handleDeadband(double val, double deadband) {
    return (Math.abs(val) > Math.abs(deadband)) ? val : 0.0;
  }
}