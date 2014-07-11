/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team4639.lib;
import com.team4639.lib.Updatable;
/**
 *
 * @author Prateek Mahajan
 */
public abstract class ControllerNew implements Updatable {
  protected boolean enabled = false;
  
  public abstract void update();
  public abstract void reset();
  public abstract double getGoal();
  
  public void enable() {
    enabled = true;
  }
  
  public void disable() {
    enabled = false;
  }

  public boolean enabled() {
    return enabled;
  }
}