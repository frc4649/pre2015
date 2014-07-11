/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team4639.frc2014.subsystems;

import com.team4639.frc2014.Constants;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
/**
 *
 * @author Prateek Mahajan
 */
public class Arms extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    DoubleSolenoid arms=new DoubleSolenoid(Constants.armUp.getInt(),Constants.armDown.getInt());
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void armsUp()
    {
        arms.set(DoubleSolenoid.Value.kForward);
    }
    
    public void armsDown()
    {
        arms.set(DoubleSolenoid.Value.kReverse);
    }
    
    public void toggleAmrs()
    {
        if(arms.get()==DoubleSolenoid.Value.kForward)
            arms.set(DoubleSolenoid.Value.kReverse);
        else if(arms.get()==DoubleSolenoid.Value.kReverse)
            arms.set(DoubleSolenoid.Value.kForward);
    }
}
