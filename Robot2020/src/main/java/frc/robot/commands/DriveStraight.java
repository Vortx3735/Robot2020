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
import frc.robot.RobotMap;
import frc.robot.subsystems.DriveTrain;
import frc.robot.util.VorTXMath;

public class DriveStraight extends CommandBase {
  /**
   * Creates a new DriveToPosition.
   */
  private PIDController dispid;
  private PIDController angpid;
  private DriveTrain drive;
  private AHRS navx;

  private double targetAngle;
  private double targetDist;

  public DriveStraight(DriveTrain drive, AHRS navx, double targetDist) {
    dispid = new PIDController(.01, 0, 0.002);
    dispid.setTolerance(2 / RobotMap.Constants.inchesPerTick);

    angpid = new PIDController(.008, 0, 0);
    angpid.enableContinuousInput(-180f, 180f);
    angpid.setTolerance(1);

    this.drive = drive;
    this.navx = navx;
    this.targetDist = targetDist;

    this.targetAngle = 0;
    addRequirements(this.drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drive.zeroEncoders();
    navx.zeroYaw();
    dispid.reset();
    angpid.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double move = VorTXMath.limit(dispid.calculate(drive.getAvgDistance(), targetDist), -.5, .5);
    double turn = VorTXMath.limit(angpid.calculate(navx.getYaw(), targetAngle), -.1, .1);
    drive.normalDrive(move, -turn);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.setLeftRight(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return dispid.atSetpoint();
  }
}
