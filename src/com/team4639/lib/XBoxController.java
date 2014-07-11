/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team4639.lib;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
/**
 *
 * @author Prateek Mahajan
 */
public class XBoxController extends Joystick
{
    private static final int AXIS_LEFT_X = 1;
    private static final int AXIS_LEFT_Y = 2;
    private static final int AXIS_SHOULDER = 3;
    private static final int AXIS_RIGHT_X = 4;
    private static final int AXIS_RIGHT_Y = 5;
    private static final int AXIS_DPAD = 6;
    
    private static final int BUTTON_A = 1;
    private static final int BUTTON_B = 2;
    private static final int BUTTON_X = 3;
    private static final int BUTTON_Y = 4;
    private static final int BUTTON_SHOULDER_LEFT = 5;
    private static final int BUTTON_SHOULDER_RIGHT = 6;
    private static final int BUTTON_BACK = 7;
    private static final int BUTTON_START = 8;
    private static final int BUTTON_LEFT_STICK = 9;
    private static final int BUTTON_RIGHT_STICK = 10;

    public XBoxController(int port)
    {
        super(port);
    }
    
    /**
   * Returns the X position of the left stick.
   */
    public double getLeftX() {
      return getRawAxis(AXIS_LEFT_X);
    }

    /**
     * Returns the X position of the right stick.
     */
    public double getRightX() {
      return getRawAxis(AXIS_RIGHT_X);
    }

    /**
     * Returns the Y position of the left stick.
     */
    public double getLeftY() {
      return getRawAxis(AXIS_LEFT_Y);
    }

    /**
     * Returns the Y position of the right stick.
     */
    public double getRightY() {
      return getRawAxis(AXIS_RIGHT_Y);
    }

    public double getTriggerAxis()
    {
        return getRawAxis(AXIS_SHOULDER);
    }

    /**
     * Checks whether Button A is being pressed and returns true if it is.
     */
    public boolean getButtonStateA() {
      return getRawButton(BUTTON_A);
    }

    /**
     * Checks whether Button B is being pressed and returns true if it is.
     */
    public boolean getButtonStateB() {
      return getRawButton(BUTTON_B);
    }

    /**
     * Checks whether Button X is being pressed and returns true if it is.
     */
    public boolean getButtonStateX() {
      return getRawButton(BUTTON_X);
    }

    /**
     * Checks whether Button Y is being pressed and returns true if it is.
     */
    public boolean getButtonStateY() {
      return getRawButton(BUTTON_Y);
    }
    
    public boolean getButtonStateShoulderLeft()
    {
        return getRawButton(BUTTON_SHOULDER_LEFT);
    }
    
    public boolean getButtonStateShoulderRight()
    {
        return getRawButton(BUTTON_SHOULDER_RIGHT);
    }
    
    public boolean getButtonStateBack()
    {
        return getRawButton(BUTTON_BACK);
    }
    
    public boolean getButtonStateStart()
    {
        return getRawButton(BUTTON_START);
    }
    
    public boolean getButtonStateLeftStick()
    {
        return getRawButton(BUTTON_LEFT_STICK);
    }
    
    public boolean getButtonStateRightStick()
    {
        return getRawButton(BUTTON_RIGHT_STICK);
    }
    
    /**
     * Returns an object of Button A.
     */
    public JoystickButton getButtonA() {
      return new JoystickButton(this, BUTTON_A);
    }

    /**
     * Returns an object of Button B.
     */
    public JoystickButton getButtonB() {
      return new JoystickButton(this, BUTTON_B);
    }

    /**
     * Returns an object of Button X.
     */
    public JoystickButton getButtonX() {
      return new JoystickButton(this, BUTTON_X);
    }

    /**
     * Returns an object of Button Y.
     */
    public JoystickButton getButtonY() {
      return new JoystickButton(this, BUTTON_Y);
    }

    /**
     * Return the DPad axis positions.
     */
    public double getDPadX() {
      return getRawAxis(AXIS_DPAD);
    }

    /**
     * DPad Left and Right only
     * WPILIB cannot access the vertical axis of the Logitech Game Controller Dpad
     */

    public boolean getDPadLeft() {
      double x = getDPadX();
      return (x < -0.5);
    }

    public boolean getDPadRight() {
      double x = getDPadX();
      return (x > 0.5);
    }

    /**
     * Gets the state of the Start button
     * @return the state of the Start button
     */
    public JoystickButton getStartButton(){
      return new JoystickButton(this, BUTTON_START);
    }

    public JoystickButton getBackButton() {
      return new JoystickButton(this, BUTTON_BACK);
    }

    /**
     * Gets the state of the left shoulder
     * @return the state of the left shoulder
     */
    public JoystickButton getLeftShoulder() {
      return new JoystickButton(this, BUTTON_SHOULDER_LEFT);
    }

    /**
     * Gets the state of the right shoulder
     * @return the state of the right shoulder
     */
    public JoystickButton getRightShoulder() {
      return new JoystickButton(this, BUTTON_SHOULDER_RIGHT);
    }

    public JoystickButton getLeftStickClick() {
      return new JoystickButton(this, BUTTON_LEFT_STICK);
    }

    public JoystickButton getRightStickClick() {
      return new JoystickButton(this, BUTTON_RIGHT_STICK);
    }


}
