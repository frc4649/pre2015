/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4639.frc2014.subsystems;

import com.team4639.frc2014.Constants;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author Prateek Mahajan
 */
public class Spinner extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    Victor spinner= new Victor(Constants.spinner.getInt());
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void ballIn()
    {
        spinner.set(-1);
    }
    
    public void ballOut()
    {
        spinner.set(1);
    }
    
    public void doNothing()
    {
        spinner.set(0);
    }
}
