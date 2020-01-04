/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot.util;

/**
 * Add your docs here.
 */
public class VorTXMath {

  // Copied from WPI's RobotBase
  /**
   * Returns 0.0 if the given value is within the specified range around zero. The
   * remaining range between the deadband and 1.0 is scaled from 0.0 to 1.0.
   *
   * @param value    value to clip
   * @param deadband range around zero
   */
  public static double applyDeadband(double value, double deadband) {
    if (Math.abs(value) > deadband) {
      if (value > 0.0) {
        return (value - deadband) / (1.0 - deadband);
      } else {
        return (value + deadband) / (1.0 - deadband);
      }
    } else {
      return 0.0;
    }
  }

  /**
   * limits a value to a certain range
   * 
   * @param value the value to limit with the bounds
   * @param lower the lower bound
   * @param upper the upper bound
   */
  public static double limit(double value, double lower, double upper) {
    if (lower > upper) {
      System.out.println("Vortx Math Limit Error");
    }
    if (value < lower) {
      value = lower;
    } else if (value > upper) {
      value = upper;
    }
    return value;
  }
}