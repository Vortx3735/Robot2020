/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Navigation extends SubsystemBase {

  private AHRS navx;

  public Navigation() {
    navx = new AHRS();
  }

  public double getYaw() {
    return navx.getYaw();
  }

  public void zeroYaw() {
    navx.zeroYaw();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Yaw Angle", navx.getYaw());
  }
}
