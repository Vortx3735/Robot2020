/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;

public class TurnToAngle extends CommandBase {

  private AHRS navx;
  private DriveTrain drive;
  private PIDController pid;
  private double angle;

  public TurnToAngle(DriveTrain drive, AHRS navx, double angle) {
    this.drive = drive;
    this.navx = navx;
    this.angle = angle;

    pid = new PIDController(.7,0,.1);
    pid.setSetpoint(angle);
    pid.setTolerance(2);
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    navx.zeroYaw();
    pid.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double val = pid.calculate(navx.getYaw(), angle);
    drive.setLeftRight(-val,val);
    System.out.println("aaa");

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("done lol");
  
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    return pid.atSetpoint();
  }
}
