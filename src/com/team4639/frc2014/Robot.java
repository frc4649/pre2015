/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.team4639.frc2014;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import com.team4639.frc2014.commands.CommandBase;
import edu.wpi.first.wpilibj.command.CommandGroup;
import com.team4639.lib.ControlUpdater;
import com.team4639.lib.Latch;
import edu.wpi.first.wpilibj.DriverStationLCD;
import com.team4639.lib.Debouncer;
import com.team4639.frc2014.commands.CheesyDriveCommand;
import com.team4639.frc2014.commands.OneLow;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    private AutoModeSelector autoModeSelector;
    private CommandGroup currentAutoMode;
    Command autonomousCommand;
    private Latch autonSelectLatch = new Latch();
    private Latch reinitGyroLatch = new Latch();
    double lastAngle = 0;
    Debouncer gyroDriftDetector = new Debouncer(1.0);
    int gyroReinits = 0;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // instantiate the command used for the autonomous period
       // autonomousCommand = new ExampleCommand();
        CommandBase.init();
        ControlUpdater.getInstance().start();
        // Initialize all subsystems
        autoModeSelector= new AutoModeSelector();
        autoModeSelector.addAutoCommand("One Ball into Low", OneLow.class);
        autoModeSelector.increment();
    }
    
    public void disabledInit()
    {
        System.out.println("Disabled init.. reloading constants...");
        Constants.readConstantsFromFile();
        CommandBase.drive.disableControllers();
        if (currentAutoMode != null) {
            currentAutoMode.cancel();
            currentAutoMode = null;
        }
        CommandBase.compressor.start();
        CommandBase.drive.resetEncoders();
        CommandBase.drive.resetGyro();
        CommandBase.drive.setLeftRightPower(0, 0);
        CommandBase.motors.set(0);
    }
    
    public void disabledPeriodic()
    {
        boolean autonSelectButton = CommandBase.controlBoard.mainJoystick.getButtonStateA();
        if (autonSelectLatch.update(autonSelectButton)) {
            autoModeSelector.increment();
        }
        boolean reinitGyroButton=CommandBase.controlBoard.mainJoystick.getButtonStateB();
        if (reinitGyroLatch.update(reinitGyroButton)) 
        {
            System.out.println("About to reinit gyro");
            CommandBase.drive.reinitGyro();
            CommandBase.drive.resetGyro();
            System.out.println("Finished reinit gyro");
        }
        updateLCD();
        double curAngle = CommandBase.drive.getGyroAngle();
        if (gyroDriftDetector.update(Math.abs(curAngle - lastAngle) > (.75 / 50.0)) && gyroReinits < 3) {
            gyroReinits++;
            System.out.println("!!! Sensed drift, about to auto-reinit gyro (" + gyroReinits + ")");
            CommandBase.drive.reinitGyro();
            CommandBase.drive.resetGyro();
            gyroDriftDetector.reset();
            curAngle = CommandBase.drive.getGyroAngle();
            System.out.println("Finished auto-reinit gyro");
        }
        lastAngle = curAngle;
    }

    public void autonomousInit() {
        if (currentAutoMode != null) {
            currentAutoMode.cancel();
            currentAutoMode = null;
        }
        CommandBase.drive.resetEncoders();
        CommandBase.drive.resetGyro();
        currentAutoMode = autoModeSelector.getCurrentAutoModeNewInstance();
        currentAutoMode.start();
        CommandBase.autonTimer.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        updateLCD();
    }

    public void teleopInit() {
	// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (currentAutoMode != null) {
            currentAutoMode.cancel();
             currentAutoMode = null;
        }
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        updateLCD();
        if(CommandBase.controlBoard.mainJoystick.getButtonStateX())
            CommandBase.spinner.ballIn();
        else if(CommandBase.controlBoard.mainJoystick.getButtonStateB())
            CommandBase.spinner.ballOut();
        else
            CommandBase.spinner.doNothing();
        if(CommandBase.controlBoard.mainJoystick.getButtonStateA())
            CommandBase.arms.toggleAmrs();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    private void updateLCD() {
        String driveEncoders="L:"+(Math.floor(CommandBase.motors.getLeftEncoder().get()) * 10) / 10.0;
        driveEncoders += " R: " + (Math.floor(CommandBase.drive.getRightEncoderDistance()) * 10) / 10.0;
        DriverStationLCD lcd = DriverStationLCD.getInstance();
        lcd.println(DriverStationLCD.Line.kUser2, 1, driveEncoders + "     ");
        lcd.println(DriverStationLCD.Line.kUser3, 1,
            "Gy: " + Math.floor(CommandBase.drive.getGyroAngle() * 100) / 100);
        lcd.updateLCD();
    }
}
