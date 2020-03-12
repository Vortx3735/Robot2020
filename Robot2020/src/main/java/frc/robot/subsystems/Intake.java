/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.util.VorTXTalonSRX;

public class Intake extends SubsystemBase {
  CANSparkMax rollers;
  VorTXTalonSRX pivot;
  VorTXTalonSRX belt;

  boolean isSeparate = false;

  public Intake() {
    rollers = new CANSparkMax(RobotMap.Intake.rollers, MotorType.kBrushless);
    pivot = new VorTXTalonSRX(RobotMap.Intake.pivot);
    belt = new VorTXTalonSRX(RobotMap.Intake.belt);

    rollers.setInverted(false);
    rollers.setIdleMode(IdleMode.kBrake);

    pivot.setNeutralMode(NeutralMode.Coast);

  }

  public void setBelt(double speed) {
    belt.set(speed);
  }

  public void setPivot(double speed) {
    pivot.set(speed);
  }

  public void setRollers(double speed) {
    rollers.set(speed);
  }

  public void suckBalls(double speed) {
    if (isSeparate) {
      setBelt(-speed);
      setRollers(speed);
    } else {
      setBelt(-speed);
    }
  }

  public void suckBallsAuton(double speed) {

    setBelt(-speed);
    setRollers(speed);

  }

  public void toggleSeparate() {
    isSeparate = !isSeparate;
  }

  public void toggleRollers() {
    if (rollers.get() == 1)
      rollers.set(0);
    else {
      rollers.set(1);
    }

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
