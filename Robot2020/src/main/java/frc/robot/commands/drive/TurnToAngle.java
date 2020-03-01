/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Navigation;

public class TurnToAngle extends CommandBase {

  private Navigation nav;
  private DriveTrain drive;
  private PIDController pid;
  private double angle;

  public TurnToAngle(DriveTrain drive, Navigation nav, double angle) {
    this.drive = drive;
    this.nav = nav;
    this.angle = angle;

    pid = new PIDController(.7, 0, .1);
    pid.setSetpoint(angle);
    pid.setTolerance(2);
    pid.enableContinuousInput(-180, 180);
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    nav.zeroYaw();
    pid.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double val = pid.calculate(nav.getYaw(), angle);
    drive.setLeftRight(-val, val);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return pid.atSetpoint();
  }
}
