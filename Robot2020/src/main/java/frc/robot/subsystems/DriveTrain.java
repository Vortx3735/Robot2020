/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class DriveTrain extends SubsystemBase {

  // WPI_TalonFX l1;
  // WPI_TalonFX l2;
  // WPI_TalonFX r1;
  // WPI_TalonFX r2;

  private CANSparkMax l1;
  private CANSparkMax l2;
  private CANSparkMax r1;
  private CANSparkMax r2;

  private CANEncoder leftEnc;
  private CANEncoder rightEnc;

  public DriveTrain() {
    // l1 = new WPI_TalonFX(RobotMap.Drive.l1);
    // l2 = new WPI_TalonFX(RobotMap.Drive.l2);
    // r1 = new WPI_TalonFX(RobotMap.Drive.r1);
    // r2 = new WPI_TalonFX(RobotMap.Drive.r2);

    l1 = new CANSparkMax(RobotMap.Drive.l1, MotorType.kBrushless);
    l2 = new CANSparkMax(RobotMap.Drive.l2, MotorType.kBrushless);
    r1 = new CANSparkMax(RobotMap.Drive.r1, MotorType.kBrushless);
    r2 = new CANSparkMax(RobotMap.Drive.r2, MotorType.kBrushless);

    leftEnc = l1.getEncoder();
    rightEnc = r1.getEncoder();

    r1.restoreFactoryDefaults();
    r2.restoreFactoryDefaults();
    l1.restoreFactoryDefaults();
    l2.restoreFactoryDefaults();

    l2.follow(l1);
    r2.follow(r1);

    l1.setInverted(true);

    r1.setSmartCurrentLimit(30);
    r2.setSmartCurrentLimit(30);
    l1.setSmartCurrentLimit(30);
    l2.setSmartCurrentLimit(30);


    r1.setIdleMode(IdleMode.kBrake);
    l1.setIdleMode(IdleMode.kBrake);
    r2.setIdleMode(IdleMode.kBrake);
    l2.setIdleMode(IdleMode.kBrake);

  }

  public void zeroEncoders(){
    leftEnc.setPosition(0);
    rightEnc.setPosition(0);
  }

  public void setLeftRight(double left, double right) {
    // l1.set(ControlMode.PercentOutput, left);
    // r1.set(ControlMode.PercentOutput, right);
    l1.set(left);
    r1.set(right);
  }

  public void normalDrive(double move, double turn) {
    // r1.setOpenLoopRampRate(3);
    // l1.setOpenLoopRampRate(3);
    // if (Math.abs(move) < .35 && Math.abs(turn)<.3) {
    //   r1.setOpenLoopRampRate(0);
    //   l1.setOpenLoopRampRate(0);
    // }
    setLeftRight(move + turn, move - turn);
  }

  public double getAvgDistance() {
    return (leftEnc.getPosition() * RobotMap.Constants.inchesPerRotation
        + rightEnc.getPosition() * RobotMap.Constants.inchesPerRotation) / 2;
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Right Speed", r1.get());
    SmartDashboard.putNumber("Left Speed", l1.get());

    SmartDashboard.putNumber("Left Inches", leftEnc.getPosition() * RobotMap.Constants.inchesPerRotation);
    SmartDashboard.putNumber("Right Inches", rightEnc.getPosition() * RobotMap.Constants.inchesPerRotation);
  }
}
