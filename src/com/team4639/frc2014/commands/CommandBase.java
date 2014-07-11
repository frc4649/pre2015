package com.team4639.frc2014.commands;

import com.team4639.frc2014.Constants;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.team4639.frc2014.subsystems.*;
import edu.wpi.first.wpilibj.Compressor;
import com.team4639.frc2014.ControlBoard;
import edu.wpi.first.wpilibj.Timer;
import com.team4639.frc2014.TrajectoryDriveController;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 * @author Author
 */
public abstract class CommandBase extends Command {

    public static ControlBoard controlBoard;
    // Create a single static instance of all of your subsystems
    public static DriveGearbox motors=new DriveGearbox();
    public static Drive drive= new Drive(motors);
    public static Spinner spinner=new Spinner();
    public static Arms arms= new Arms();
    public static Compressor compressor= new Compressor(Constants.pressureSensor.getInt(),Constants.compressorRelay.getInt());
    public static Timer autonTimer = new Timer();
    public static TrajectoryDriveController driveController = new TrajectoryDriveController();
    
    public static void init() {
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        controlBoard = new ControlBoard();
        // Show what command your subsystem is running on the SmartDashboard
        compressor.start();
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
