package com.team4639.lib;
public interface ControlSource {
  public double get();
  public void updateFilter();
  public boolean getLowerLimit();
  public boolean getUpperLimit();
}