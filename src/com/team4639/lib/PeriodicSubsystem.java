/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team4639.lib;
import edu.wpi.first.wpilibj.command.Subsystem;
        
/**
 *
 * @author Prateek Mahajan
 */
public abstract class PeriodicSubsystem extends Subsystem implements Updatable {

  public PeriodicSubsystem() {
    ControlUpdater.getInstance().add(this);
  }

  public abstract void update();
}
