/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Hood extends SubsystemBase {

  CANSparkMax hood;
  CANEncoder encoder;

  PIDController pid;

  public Hood() {
    hood = new CANSparkMax(RobotMap.Hood.hoodMotor, MotorType.kBrushless);
    encoder = hood.getEncoder();
    pid = new PIDController(0,0,0);
    encoder.setPosition(0);
    pid.setTolerance(2);
  }

  public void set(double speed) {
    hood.set(speed);
  }

  public void setPosition(double pos){
    double val = pid.calculate(encoder.getPosition(), pos);
    set(val);
  }


  @Override
  public void periodic() {
    SmartDashboard.putNumber("Hood Position Raw", encoder.getPosition());
  }
}
