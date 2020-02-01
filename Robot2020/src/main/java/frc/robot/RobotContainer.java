/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.ShootAtSpeed;
import frc.robot.commands.TurnToAngle;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;
import frc.robot.util.VorTXController;
import frc.robot.util.VorTXMath;

public class RobotContainer {

  // Subsystems
  private final DriveTrain drive = new DriveTrain();
  // private final ColorSensor color = new ColorSensor();
  // private final Shooter shooter = new Shooter();
  // private final Intake intake = new Intake();
  // private final Turret turret = new Turret();

  // Commands
  // private final DriveStraight drivestraight = new DriveStraight(drive, navx,
  // 120);
  // private final TurnToAngle turnto90 = new TurnToAngle(drive,navx, 90);
  // private final TurnToAngle turnto135 = new TurnToAngle(drive,navx, 70);
  // private final ShootAtSpeed shoot3000 = new ShootAtSpeed(3000, shooter);

  // Controllers
  private static final VorTXController main = new VorTXController(0);
  // private static final VorTXController co = new VorTXController(1);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    drive.setDefaultCommand(new RunCommand(() -> drive.normalDrive(getDriveValue(), getTurnValue()), drive));
    // shooter.setDefaultCommand(new RunCommand(() -> shooter.set(getShootValue()),
    // shooter));
    // intake.setDefaultCommand(new RunCommand(()-> intake.set(getShootValue()),
    // intake));
    // turret.setDefaultCommand(new RunCommand(() -> turret.set(getTurnValue()),
    // turret));
    configureButtonBindings();
  }

  private double getShootValue() {
    double val = -VorTXMath.applyDeadband(main.getY(Hand.kRight), .1);

    return Math.copySign(val * val, val);
  }

  public double getDriveValue() {
    return Math.copySign(
        Math.pow(VorTXMath.applyDeadband(main.getTriggerAxis(Hand.kRight) - main.getTriggerAxis(Hand.kLeft), .1), 2),
        VorTXMath.applyDeadband(main.getTriggerAxis(Hand.kRight) - main.getTriggerAxis(Hand.kLeft), .1));
  }

  public double getTurnValue() {
    double val = -VorTXMath.applyDeadband(main.getX(Hand.kLeft), .1);
    return Math.copySign(val * val, val);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // // main.y.whenPressed(new SequentialCommandGroup(turnto45,drivestraight,
    // turnto135));
    // main.a.whenPressed(turnto90);
    // main.x.whenPressed(drivestraight);
    // main.b.whenPressed(new InstantCommand(drive::reverseDir, drive));
    // main.y.whenPressed(shoot3000);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }

}
