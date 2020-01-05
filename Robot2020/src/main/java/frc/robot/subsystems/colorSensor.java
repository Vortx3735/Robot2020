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

  ColorMatch match;
  Color detectedColor;
  ColorMatchResult matchResult;
  public String colorString;
  private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

  public ColorSensor() {
    sensor = new ColorSensorV3(I2C.Port.kOnboard);
    match = new ColorMatch();

    match.addColorMatch(kBlueTarget);
    match.addColorMatch(kGreenTarget);
    match.addColorMatch(kRedTarget);
    match.addColorMatch(kYellowTarget);
  }

  public void detectColor() {
    detectedColor = sensor.getColor();

    matchResult = match.matchClosestColor(detectedColor);

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
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("Confidence", matchResult.confidence);
    SmartDashboard.putString("Detected Color", colorString);
  }
}
