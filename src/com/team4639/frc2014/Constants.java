/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team4639.frc2014;
import com.team4639.lib.ConstantsBase;
/**
 *
 * @author Prateek Mahajan
 */
public class Constants extends ConstantsBase  {
    public static final Constant mainJoystick= new Constant("mainJoystickPort",1);
    public static final Constant leftRear=new Constant("leftRear",1);
    public static final Constant leftFront=new Constant("leftFront",2);
    public static final Constant rightRear=new Constant("rightRear",3);
    public static final Constant rightFront=new Constant("rightFront",4);
    public static final Constant spinner=new Constant("spinner",5);
    public static final Constant armUp=new Constant("armUp",1);
    public static final Constant armDown=new Constant("armDown",2);
    public static final Constant shifterForward=new Constant("shifterForward",5);
    public static final Constant shifterReverse=new Constant("shifterReverse",6);
    public static final Constant rightEncoderA=new Constant("rightEncoderA",2);
    public static final Constant rightEncoderB=new Constant("rightEncoderB",3);
    public static final Constant leftEncoderA=new Constant("leftEncoderA",4);
    public static final Constant leftEncoderB=new Constant("leftEncoderB",5);
    public static final Constant pressureSensor=new Constant("pressureSensor",1);
    public static final Constant compressorRelay=new Constant("compressorRelay",1);
    public static final Constant gyro=new Constant("gyro",6);
    public static final Constant sensitivityHigh = new Constant("sensitivityHigh", .85);
    public static final Constant sensitivityLow = new Constant("sensitivityLow", .75);

  public static final Constant driveStraightKPLow = new Constant("driveStraightKPLow", 0.07);
  public static final Constant driveStraightKILow = new Constant("driveStraightKILow", 0);
  public static final Constant driveStraightKDLow = new Constant("driveStraightKDLow", 0.02);

  public static final Constant driveTurnKPLow = new Constant("driveTurnKPLow", 0.03);
  public static final Constant driveTurnKILow = new Constant("driveTurnKILow", 0);
  public static final Constant driveTurnKDLow = new Constant("driveTurnKDLow", 0.045);

  public static final Constant driveStraightKPHigh = new Constant("driveStraightKPHigh", 0.045);
  public static final Constant driveStraightKIHigh = new Constant("driveStraightKIHigh", 0);
  public static final Constant driveStraightKDHigh = new Constant("driveStraightKDHigh", 0.17);

  public static final Constant driveTurnKPHigh = new Constant("driveTurnKPHigh", 0.045);
  public static final Constant driveTurnKIHigh = new Constant("driveTurnKIHigh", 0);
  public static final Constant driveTurnKDHigh = new Constant("driveTurnKDHigh", 0.15);
  
  public static final Constant minTimeBetweenShifts= new Constant("minTimeBetweenShifts",.5);
  public static final Constant downshiftLowSpeed= new Constant("downshiftLowSeed",2);
  public static final Constant downshiftLowWaitTime=new Constant("downshiftLowWaitTime",.5);
  public static final Constant downshiftSpeed= new Constant("downshiftSpeed",6);
  public static final Constant downshiftDeceleration= new Constant("downshiftDeceleration",-1.2);
   public static final Constant downshiftThrottle= new Constant("downshiftThrottle",.75);
  
  static {
    // Set any overridden constants from the file on startup.
    readConstantsFromFile();
  }

  // Prevent instantiation of this class, as it should only be used statically.
  private Constants() {
  }
}
