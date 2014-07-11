/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team4639.lib;
import edu.wpi.first.wpilibj.Timer;
/**
 *
 * @author Prateek Mahajan
 */
public class Debouncer {
  Timer t = new Timer();
  double time;
  boolean first = true;

  public Debouncer(double time) {
    this.time = time;
  }

  public boolean update(boolean val) {
    if (first) {
      first = false;
      t.start();
    }
    if(!val) {
      t.reset();
    }
    return t.get() > time;
  }

  public void reset() {
    t.reset();
  }
}
