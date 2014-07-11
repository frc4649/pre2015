/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team4639.lib;

/**
 *
 * @author Prateek Mahajan
 */
public class Latch {
    private boolean lastVal;

    public boolean update(boolean newVal) {
      boolean result = newVal && !lastVal;
      lastVal = newVal;
      return result;
    }
}
