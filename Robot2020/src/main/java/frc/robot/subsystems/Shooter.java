/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase {

  CANSparkMax shooter1;
  CANSparkMax shooter2;

  CANEncoder encoder1;
  CANEncoder encoder2;

  double setpoint;

  public Shooter() {

    shooter1 = new CANSparkMax(RobotMap.Shooter.shooter1, MotorType.kBrushless);
    shooter2 = new CANSparkMax(RobotMap.Shooter.shooter2, MotorType.kBrushless);
    shooter1.restoreFactoryDefaults();
    shooter2.restoreFactoryDefaults();
    shooter2.setInverted(true);

    encoder1 = shooter1.getEncoder();
    encoder2 = shooter2.getEncoder();

    shooter1.setIdleMode(IdleMode.kBrake);
    shooter2.setIdleMode(IdleMode.kBrake);

    shooter2.follow(shooter1);

  }

  public void set(double speed) {
    shooter1.set(speed);
  }

  public double getSpeed() {

    return .5 * (encoder1.getVelocity() + encoder2.getVelocity());
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Shooter Speed", getSpeed());
  }
}
