/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;

public class NormalDrive extends CommandBase {
  /**
   * Creates a new NormalDrive.
   */
  private final DriveTrain drive;
  public NormalDrive(DriveTrain driveTrain) {
    drive = driveTrain;
    addRequirements(driveTrain);
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double move = RobotContainer.getDriveValue();
    double turn = RobotContainer.getTurnValue();

    // Robot.drive.r1.setOpenLoopRampRate(RobotMap.Constants.rampRate);
    // Robot.drive.l1.setOpenLoopRampRate(RobotMap.Constants.rampRate);
    // if (move == 0) {
    //   Robot.drive.r1.setOpenLoopRampRate(0);
    //   Robot.drive.l1.setOpenLoopRampRate(0);
    // }

    drive.setLeftRight(move + turn, move - turn);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if(!interrupted)
      drive.setLeftRight(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
