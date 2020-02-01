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
  
  private static final double kInchesPerFoot = 12.0;
  private static final double kMetersPerInch = 0.0254;
  
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
   /**
   * Converts given meters to feet.
   *
   * @param meters The meters to convert to feet.
   * @return Feet converted from meters.
   */
  public static double metersToFeet(double meters) {
    return metersToInches(meters) / kInchesPerFoot;
  }

  /**
   * Converts given feet to meters.
   *
   * @param feet The feet to convert to meters.
   * @return Meters converted from feet.
   */
  public static double feetToMeters(double feet) {
    return inchesToMeters(feet * kInchesPerFoot);
  }

  /**
   * Converts given meters to inches.
   *
   * @param meters The meters to convert to inches.
   * @return Inches converted from meters.
   */
  public static double metersToInches(double meters) {
    return meters / kMetersPerInch;
  }

  /**
   * Converts given inches to meters.
   *
   * @param inches The inches to convert to meters.
   * @return Meters converted from inches.
   */
  public static double inchesToMeters(double inches) {
    return inches * kMetersPerInch;
  }

  /**
   * Converts given degrees to radians.
   *
   * @param degrees The degrees to convert to radians.
   * @return Radians converted from degrees.
   */
  public static double degreesToRadians(double degrees) {
    return Math.toRadians(degrees);
  }

}