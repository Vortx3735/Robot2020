/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.util.VorTXMath;

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

    // r1.configFactoryDefault();
    // r2.configFactoryDefault();
    // l1.configFactoryDefault();
    // l2.configFactoryDefault();

    l2.follow(l1);
    r2.follow(r1);

    init();

  }

  public void zeroEncoders() {

    // l1.setSelectedSensorPosition(0);
    // r1.setSelectedSensorPosition(0);

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
    move = VorTXMath.limit(move,-.5,.5);
    
    if(l1.getInverted())
      setLeftRight(move + turn, move - turn);
    else
      setLeftRight(move - turn, move + turn);
  }

  public void arcadeDrive(double move, double rotate){
    double leftSpeed;
    double rightSpeed;

    double maxInput = Math.copySign(Math.max(Math.abs(move), Math.abs(rotate)), move);

    if(!l1.getInverted())
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

  public double getAvgDistance() {
    return (leftEnc.getPosition() * RobotMap.Constants.inchesPerRotation
        + rightEnc.getPosition() * RobotMap.Constants.inchesPerRotation) / 2;
    // return (l1.getSelectedSensorPosition() * RobotMap.Constants.inchesPerRotation
    //     + r1.getSelectedSensorPosition() * RobotMap.Constants.inchesPerRotation) / 2;
  }

  public void init() {
    // l1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    // l1.setSensorPhase(true);
    // l1.setSelectedSensorPosition(0);
    // l1.setNeutralMode(NeutralMode.Brake);

    // r1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    // r1.setSensorPhase(true);
    // r1.setSelectedSensorPosition(0);
    // r1.setNeutralMode(NeutralMode.Brake);
    

    // r1.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 40, 0, 0));
    // r2.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 40, 0, 0));
    // l1.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 40, 0, 0));
    // l2.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 40, 0, 0));


    l1.setInverted(true);

    r1.setSmartCurrentLimit(25);
    r2.setSmartCurrentLimit(25);
    l1.setSmartCurrentLimit(25);
    l2.setSmartCurrentLimit(25);

    r1.setIdleMode(IdleMode.kBrake);
    l1.setIdleMode(IdleMode.kBrake);
    r2.setIdleMode(IdleMode.kBrake);
    l2.setIdleMode(IdleMode.kBrake);
  }

  public void reverseDir(){
    l1.setInverted(!l1.getInverted());
    r1.setInverted(!r1.getInverted());
  }
  @Override
  public void periodic() {
    SmartDashboard.putNumber("Right Speed", r1.get());
    SmartDashboard.putNumber("Left Speed", l1.get());

    // SmartDashboard.putNumber("Left Inches", l1.getSelectedSensorPosition() * RobotMap.Constants.inchesPerRotation);
    // SmartDashboard.putNumber("Right Inches", l2.getSelectedSensorPosition() * RobotMap.Constants.inchesPerRotation);

    SmartDashboard.putNumber("Left Inches", leftEnc.getPosition() * RobotMap.Constants.inchesPerRotation);
    SmartDashboard.putNumber("Right Inches", rightEnc.getPosition() * RobotMap.Constants.inchesPerRotation);
  }
}
