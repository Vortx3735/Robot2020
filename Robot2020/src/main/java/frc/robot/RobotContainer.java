/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.ColorSensor;
import frc.robot.subsystems.DriveTrain;
import frc.robot.util.VorTXController;
import frc.robot.util.VorTXMath;

public class RobotContainer {

  // Subsystems
  private final DriveTrain drive = new DriveTrain();
  private final ColorSensor color = new ColorSensor();

  // Commands

  // Controllers
  private static final VorTXController main = new VorTXController(0);
  private static final VorTXController co = new VorTXController(1);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    drive.setDefaultCommand(new RunCommand(() -> drive.normalDrive(getDriveValue(), getTurnValue()), drive));
    configureButtonBindings();
  }

  public double getDriveValue() {
    return -Math.copySign(
        Math.pow(VorTXMath.applyDeadband(main.getTriggerAxis(Hand.kRight) - main.getTriggerAxis(Hand.kLeft), .2), 2),
        VorTXMath.applyDeadband(main.getTriggerAxis(Hand.kRight) - main.getTriggerAxis(Hand.kLeft), .2));
  }

  public double getTurnValue() {
    double val = VorTXMath.applyDeadband(main.getX(Hand.kLeft), .1);
    return Math.copySign(val * val, val);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

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
