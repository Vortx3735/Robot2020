/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ColorSensor extends SubsystemBase {

  ColorSensorV3 sensor;
  ColorMatch matcher;
  Color detectedColor;
  ColorMatchResult matchResult;
  
  public String colorString;
  private final Color kBlueTarget = ColorMatch.makeColor(0 , 255, 255);
  private final Color kGreenTarget = ColorMatch.makeColor(0, 255, 0);
  private final Color kRedTarget = ColorMatch.makeColor(255, 0, 0);
  private final Color kYellowTarget = ColorMatch.makeColor(255, 255, 0);

  public ColorSensor() {
    sensor = new ColorSensorV3(I2C.Port.kOnboard);
    matcher = new ColorMatch();
    detectedColor = sensor.getColor();

    matcher.addColorMatch(kBlueTarget);
    matcher.addColorMatch(kGreenTarget);
    matcher.addColorMatch(kRedTarget);
    matcher.addColorMatch(kYellowTarget);
  }

  public void detectColor() {
    detectedColor = sensor.getColor();

    matchResult = matcher.matchClosestColor(detectedColor);

    if (matchResult.color == kBlueTarget) {
      colorString = "Blue";
    } else if (matchResult.color == kRedTarget) {
      colorString = "Red";
    } else if (matchResult.color == kGreenTarget) {
      colorString = "Green";
    } else if (matchResult.color == kYellowTarget) {
      colorString = "Yellow";
    } else {
      colorString = "Unknown";
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    detectColor();
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("Confidence", matchResult.confidence);
    SmartDashboard.putString("Detected Color", colorString);
  }
}
