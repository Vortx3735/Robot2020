/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.util.Units;
import frc.robot.util.VorTXMath;

public class DriveTrain extends SubsystemBase {

  WPI_TalonFX l1;
  WPI_TalonFX l2;
  WPI_TalonFX r1;
  WPI_TalonFX r2;

  AHRS navx;

  DifferentialDriveOdometry odometry;

  public DriveTrain(Navigation lol) {
    navx = lol.getNavX();
    odometry = new DifferentialDriveOdometry(getGyroAngle());

    l1 = new WPI_TalonFX(RobotMap.Drive.l1);
    l2 = new WPI_TalonFX(RobotMap.Drive.l2);
    r1 = new WPI_TalonFX(RobotMap.Drive.r1);
    r2 = new WPI_TalonFX(RobotMap.Drive.r2);

    r1.configFactoryDefault();
    r2.configFactoryDefault();
    l1.configFactoryDefault();
    l2.configFactoryDefault();

    r2.follow(r1);
    l2.follow(l1);

    r1.setInverted(true);
    r2.setInverted(true);

    init();

    r1.setNeutralMode(NeutralMode.Coast);
    r2.setNeutralMode(NeutralMode.Coast);
    l1.setNeutralMode(NeutralMode.Coast);
    l2.setNeutralMode(NeutralMode.Coast);

  }

  public Rotation2d getGyroAngle() {
    // Negating the angle because WPILib gyros are CW positive.
    return Rotation2d.fromDegrees(-navx.getYaw());
  }

  public void zeroEncoders() {
    l1.setSelectedSensorPosition(0);
    r1.setSelectedSensorPosition(0);
  }

  public void setLeftRight(double left, double right) {
    l1.set(left);
    r1.set(right);

  }

  public void normalDrive(double move, double turn) {
    if (l1.getInverted())
      setLeftRight(move + turn, move - turn);
    else
      setLeftRight(move - turn, move + turn);
  }

  public void arcadeDrive(double move, double rotate) {
    double leftSpeed;
    double rightSpeed;

    double maxInput = Math.copySign(Math.max(Math.abs(move), Math.abs(rotate)), move);

    if (!l1.getInverted())
      rotate = -rotate;

    if (move >= 0.0) {
      if (rotate >= 0.0) {
        leftSpeed = maxInput;
        rightSpeed = move - rotate;
      } else {
        leftSpeed = move + rotate;
        rightSpeed = maxInput;
      }
    } else {
      if (rotate >= 0.0) {
        leftSpeed = move + rotate;
        rightSpeed = maxInput;
      } else {
        leftSpeed = maxInput;
        rightSpeed = move - rotate;
      }
    }
    setLeftRight(leftSpeed, rightSpeed);
  }

  public double getAvgDistance(Units unit) {
    if (unit == Units.meters) {
      return VorTXMath.inchesToMeters(
          (l1.getSelectedSensorPosition() + r1.getSelectedSensorPosition()) / 2 * RobotMap.Constants.inchesPerTick);
    } else {
      return (l1.getSelectedSensorPosition() + r1.getSelectedSensorPosition()) / 2 * RobotMap.Constants.inchesPerTick;

    }
  }

  public void init() {
    l1.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    l1.setSensorPhase(true);
    l1.setSelectedSensorPosition(0);
    l1.setNeutralMode(NeutralMode.Brake);

    r1.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    r1.setSensorPhase(false);
    r1.setSelectedSensorPosition(0);
    r1.setNeutralMode(NeutralMode.Brake);

    r1.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 40, 0, 0));
    r2.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 40, 0, 0));
    l1.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 40, 0, 0));
    l2.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 40, 0, 0));

  }

  public void reverseDir() {
    l1.setInverted(!l1.getInverted());
    l2.setInverted(!l2.getInverted());
    r1.setInverted(!r1.getInverted());
    r2.setInverted(!r2.getInverted());
  }

  public double getLeftPosition(Units unit) {
    if (unit == Units.meters) {
      return VorTXMath.inchesToMeters(l1.getSelectedSensorPosition() * RobotMap.Constants.inchesPerTick);
    } else {
      return l1.getSelectedSensorPosition() * RobotMap.Constants.inchesPerTick;

    }
  }

  public double getRightPosition(Units unit) {
    if (unit == Units.meters) {
      return VorTXMath.inchesToMeters(r1.getSelectedSensorPosition() * RobotMap.Constants.inchesPerTick);
    } else {
      return r1.getSelectedSensorPosition() * RobotMap.Constants.inchesPerTick;

    }
  }

  public void resetOdometry() {
    navx.zeroYaw();
    zeroEncoders();
    odometry.resetPosition(new Pose2d(), new Rotation2d());
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Avg Position Inches", getAvgDistance(Units.inches));
    SmartDashboard.putNumber("Right Inches", getRightPosition(Units.inches));
    SmartDashboard.putNumber("Left Inches", getLeftPosition(Units.inches));

    // SmartDashboard.putNumber("X-Translation",
    // odometry.getPoseMeters().getTranslation().getX());
    // SmartDashboard.putNumber("Y-Translation",
    // odometry.getPoseMeters().getTranslation().getY());
    SmartDashboard.putNumber("Raw Drive Train Distance",
        (r1.getSelectedSensorPosition() + r2.getSelectedSensorPosition()) / 2);
    odometry.update(getGyroAngle(), getLeftPosition(Units.meters), getRightPosition(Units.meters));
  }
}
