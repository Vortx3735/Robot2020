/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.util.VorTXMath;
import frc.robot.util.VorTXTalonSRX;

public class Turret extends SubsystemBase {

  VorTXTalonSRX turret;

  public Turret() {
    turret = new VorTXTalonSRX(RobotMap.Turret.motor);
    turret.initSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, false);
    turret.setNeutralMode(NeutralMode.Brake);
  }

  public void set(double speed) {
    turret.set(VorTXMath.limit(speed, -.5, .5));
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Turret Angle", turret.getPosition());
  }
}
