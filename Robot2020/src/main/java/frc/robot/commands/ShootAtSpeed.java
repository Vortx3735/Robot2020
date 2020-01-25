/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Shooter;

public class ShootAtSpeed extends PIDCommand {
  Shooter shooter;

  public ShootAtSpeed(double targetSpeed, Shooter shooter) {
    super(
        // The controller that the command will use
        new PIDController(.5, 0, 0),
        // This should return the measurement
        () -> shooter.getSpeed(),
        // This should return the setpoint (can also be a constant)
        targetSpeed,
        // This uses the output
        output -> shooter.set(output));
    this.shooter = shooter;
    addRequirements(shooter);
    // Configure additional PID options by calling `getController` here.
    getController().setTolerance(10);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
