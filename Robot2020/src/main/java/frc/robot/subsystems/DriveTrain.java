/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.commands.NormalDrive;

public class DriveTrain extends SubsystemBase {

  TalonFX l1;
  TalonFX l2;
  TalonFX r1;
  TalonFX r2;

  public DriveTrain() {
    l1 = new TalonFX(RobotMap.Drive.l1);
    l2 = new TalonFX(RobotMap.Drive.l2);
    r1 = new TalonFX(RobotMap.Drive.r1);
    r2 = new TalonFX(RobotMap.Drive.r2);

    l2.follow(l1);
    r2.follow(r1);
    r1.setInverted(true);

  }

  public void setLeftRight(double left, double right) {
      l1.set(ControlMode.PercentOutput, left);
      r1.set(ControlMode.PercentOutput, right);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
