/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team4639.frc2014;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.command.CommandGroup;
import java.util.Vector;
/**
 *
 * @author Prateek Mahajan
 */
public final class AutoModeSelector {
  private Vector autoModes;
  private int index;
  private DriverStationLCD lcd;

  public AutoModeSelector() {
    autoModes = new Vector();
    addAutoCommand("None", CommandGroup.class);
    index = -1;
    lcd = DriverStationLCD.getInstance();
    increment();
  }

  void addAutoCommand(String name, Class cmd) {
    autoModes.addElement(new AutoMode(name, cmd));
  }

  public void increment() {
    index++;
    if (index >= autoModes.size()) {
      index = 0;
    }
    lcd.println(DriverStationLCD.Line.kUser1, 1, "                                       ");
    lcd.println(DriverStationLCD.Line.kUser1, 1, "Auto: " + getCurrentName());
    lcd.updateLCD();
  }

  public String getCurrentName() {
    return ((AutoMode)autoModes.elementAt(index)).name;
  }

  public CommandGroup getCurrentAutoModeNewInstance() {
    try {
      AutoMode m = (AutoMode)autoModes.elementAt(index);
      return (CommandGroup)m.commandGroupType.newInstance();
    } catch (InstantiationException ex) {
      return new CommandGroup();
    } catch (IllegalAccessException ex) {
      return new CommandGroup();
    }
  }

  private static class AutoMode {
    public String name;
    public Class commandGroupType;

    public AutoMode(String name, Class commandGroupType) {
      this.name = name;
      this.commandGroupType = commandGroupType;
    }
  }
}
