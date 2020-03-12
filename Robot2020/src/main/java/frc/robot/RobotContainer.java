/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.Hood;
import frc.robot.commands.auton.MainAutoSequence;
import frc.robot.commands.drive.DriveStraight;
import frc.robot.commands.turret.TurretAim;
import frc.robot.subsystems.Climber;
// import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Navigation;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;
import frc.robot.util.VorTXController;
import frc.robot.util.VorTXMath;

public class RobotContainer {

  private final SendableChooser<Command> chooser = new SendableChooser<Command>();

  // Subsystems
  private final Navigation navx = new Navigation();
  private final DriveTrain drive = new DriveTrain(navx);
  // private final ColorSensor color = new ColorSensor();
  private final Shooter shooter = new Shooter();
  private final Intake intake = new Intake();
  private final Limelight limelight = new Limelight();
  private final Turret turret = new Turret();
  private final Hood hood = new Hood();
  private final Climber climber = new Climber();

  // Commands
  private final TurretAim aim = new TurretAim(turret, limelight);
  private final MainAutoSequence primaryauto = new MainAutoSequence(drive, intake, shooter, turret, limelight, navx);
  private final DriveStraight driveStraight = new DriveStraight(drive, navx.getNavX(), 80);

  // Controllers
  private static final VorTXController main = new VorTXController(0);
  private static final VorTXController co = new VorTXController(1);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */

  public RobotContainer() {
    chooser.setDefaultOption("main auto sequence", primaryauto);
    SmartDashboard.putData("Autonomous Chooser", chooser);
    drive.setDefaultCommand(new RunCommand(() -> drive.normalDrive(getDriveValue(), getTurnValue()), drive));
    hood.setDefaultCommand(new RunCommand(() -> hood.set(getHoodValue()), hood));
    shooter.setDefaultCommand(new RunCommand(() -> {
      if (shooter.getReverse()) {
        if (shooter.getShooting()) {
          shooter.setGate(1);
          shooter.set(1);
        } else {
          shooter.setGate(0);
          shooter.set(0);
        }
      } else {
        shooter.setGate(VorTXMath.limit(-getIntakeValue(), -1, 0));
        shooter.set(VorTXMath.limit(-getIntakeValue() * .05, -1, 0));

      }
    }, shooter));
    intake.setDefaultCommand(new RunCommand(() -> {
      intake.suckBalls(getIntakeValue());
    }, intake));
    turret.setDefaultCommand(new RunCommand(() -> turret.set(getTurretValue()), turret));
    climber.setDefaultCommand(new RunCommand(() -> climber.setWinch(getClimbValue()), climber));
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    main.b.whenPressed(drive::reverseDir);
    main.x.whenPressed(new InstantCommand(()-> intake.setPivot(0)));
    main.a.whenPressed(driveStraight.withInterrupt(new BooleanSupplier() {

      @Override
      public boolean getAsBoolean() {
        // TODO Auto-generated method stub
        return Math.abs(getDriveValue()) > .3;
      }
    }));
    main.lb.whenHeld(new StartEndCommand(() -> {
      climber.setReleaser(-.3);
    }, () -> {
      climber.setReleaser(0);
    }));
    main.rb.whenHeld(new StartEndCommand(() -> {
      climber.setReleaser(.3);
    }, () -> {
      climber.setReleaser(0);
    }));

    main.back.whenHeld(new StartEndCommand(() -> {
      intake.setPivot(.4);
    }, () -> {
      intake.setPivot(-.2);
    }));
    main.start.whenHeld(new StartEndCommand(() -> {
      intake.setPivot(-.4);
    }, () -> {
      intake.setPivot(-0.2);
    }));

    co.rb.whileHeld(aim);
    co.lb.whenPressed(shooter::toggleShooting);
    co.y.whenPressed(
        new InstantCommand(() -> intake.toggleSeparate()).alongWith(new InstantCommand(() -> shooter.toggleReverse())));
    co.a.whenPressed(intake::toggleRollers);
  }

  public double getDriveValue() {
    return Math.copySign(
        Math.pow(VorTXMath.applyDeadband(main.getTriggerAxis(Hand.kRight) - main.getTriggerAxis(Hand.kLeft), .05), 2),
        VorTXMath.applyDeadband(main.getTriggerAxis(Hand.kRight) - main.getTriggerAxis(Hand.kLeft), .05));
  }

  public double getTurnValue() {
    final double val = -VorTXMath.applyDeadband(main.getX(Hand.kLeft), .01);
    return Math.copySign(val * val, val);
  }

  public double getTurretValue() {
    final double val = .5
        * VorTXMath.applyDeadband(co.getTriggerAxis(Hand.kRight) - co.getTriggerAxis(Hand.kLeft), .05);
    return Math.copySign(val * val, val);
  }

  public double getHoodValue() {
    final double val = -VorTXMath.limit(VorTXMath.applyDeadband(co.getY(Hand.kLeft), .05), -.4, .4);
    return Math.copySign(val * val, val);
  }

  public double getIntakeValue() {
    final double val = -VorTXMath.applyDeadband(co.getY(Hand.kRight), .01);
    return Math.copySign(val * val, val);
  }

  public double getClimbValue() {
    final double val = -VorTXMath.applyDeadband(main.getY(Hand.kRight), .05);
    return Math.copySign(val * val, val);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {

    return chooser.getSelected();
  }

}
