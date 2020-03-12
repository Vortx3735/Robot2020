/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.util.VorTXTalonSRX;

public class Climber extends SubsystemBase {

  VorTXTalonSRX releaser;
  VorTXTalonSRX winch;

  public Climber() {
    releaser = new VorTXTalonSRX(RobotMap.Climber.releaser);
    winch = new VorTXTalonSRX(RobotMap.Climber.winch);
  }

  public void setReleaser(double speed) {
    releaser.set(speed);
  }

  public void setWinch(double speed) {
    winch.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}