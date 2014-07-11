/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team4639.frc2014;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import com.team4639.lib.XBoxController;
/**
 *
 * @author Prateek Mahajan
 */
public class ControlBoard {
    public XBoxController mainJoystick= new XBoxController(Constants.mainJoystick.getInt());
    
    public boolean getQuickTurn()
    {
        return mainJoystick.getRawButton(7);
    }
    public boolean getShift()
    {
        return mainJoystick.getRawButton(9);
    }
    
    public boolean getAutoShift()
    {
        return mainJoystick.getRawButton(8);
    }
    
  
    
    
}
