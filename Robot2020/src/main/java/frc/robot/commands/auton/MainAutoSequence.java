/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Navigation;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;
import frc.robot.commands.drive.DriveStraight;
import frc.robot.commands.turret.TurretAim;

public class MainAutoSequence extends SequentialCommandGroup {
  /**
   * Creates a new MainAutoSequence.
   */
  public MainAutoSequence(DriveTrain drive, Intake intake, Shooter shooter, Turret turret, Limelight limelight,
      Navigation navx) {

    super(
        new ParallelCommandGroup(new RunCommand(() -> {
          shooter.setShooter(1, 1);
          intake.setBelt(.3);
        }, intake, shooter).withTimeout(3), new TurretAim(turret, limelight).withTimeout(3)).withTimeout(3),

        new ParallelRaceGroup(new DriveStraight(drive, navx.getNavX(), 200.0), new RunCommand(() -> {
          intake.suckBallsAuton(1);
        }, intake)),

        new ParallelCommandGroup(new RunCommand(() -> {
          intake.setBelt(1);
          shooter.setShooter(1, 1);
        }, intake, shooter), new TurretAim(turret, limelight)));
  }
}
