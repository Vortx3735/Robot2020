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
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.util.VorTXTalonSRX;

public class Shooter extends SubsystemBase {
  
  VorTXTalonSRX shooter;

  public Shooter() {
    shooter = new VorTXTalonSRX(13);
    init();

  }

  public void init(){
    shooter.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);
    shooter.setSensorPhase(true);
    // shooter.setPosition(0);

    shooter.setNeutralMode(NeutralMode.Brake);
    shooter.setSensorPhase(true);
    shooter.setSelectedSensorPosition(0);
    shooter.setNeutralMode(NeutralMode.Brake);
    shooter.configContinuousCurrentLimit(20, 0);
    shooter.enableCurrentLimit(true);
  }

  public void set(double speed){
    shooter.set(speed);
  }
  @Override
  public void periodic() {
    SmartDashboard.putNumber("Shooter Speed", shooter.getSpeed()/256*1000);

  }
}
