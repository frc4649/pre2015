/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team4639.lib;
import com.sun.squawk.util.MathUtils;
/**
 *
 * @author Prateek Mahajan
 */
public class FastParser {
  // Assumes the format %.Nf, where N is constant and leading zeros are present.
  public static double parseFormattedDouble(String string) {
    // Find the decimal point
    int decimal = string.indexOf('.');
    
    if (decimal == -1 || decimal + 1 >= string.length()) {
      // The string is not formatted correctly.
      return 0.0;
    }
    
    long whole_part = Long.parseLong(string.substring(0, decimal));
    long fractional_part = Long.parseLong(string.substring(decimal + 1, 
            string.length()));
    
    double divisor = MathUtils.pow(10, string.length() - decimal - 1);
    double sign = ((string.indexOf('-') == -1) ? 1.0 : -1.0);
    
    return (double)whole_part + (double)fractional_part*sign / divisor;
  }
}
