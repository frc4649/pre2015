package com.team4639.lib;
public interface MotionProfile {
  public double updateSetpoint(double curSetpoint, double curSource, double curTime);
  public double setGoal(double goal, double curSource, double t);
}