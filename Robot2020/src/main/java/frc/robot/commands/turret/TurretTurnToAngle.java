/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.turret;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Turret;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class TurretTurnToAngle extends PIDCommand {
  /**
   * Creates a new TurretTurnToAngle.
   */
  Turret turret = new Turret();
  public TurretTurnToAngle(Turret turret, double setpoint) {
    super(
        // The controller that the command will use
        new PIDController(.05, 0, 0),
        // This should return the measurement
        () -> turret.getPosition(),
        // This should return the setpoint (can also be a constant)
        setpoint,
        // This uses the output
        output -> {
          // Use the output here
          turret.set(output);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    this.turret = turret;
    addRequirements(this.turret);
    // Configure additional PID options by calling `getController` here.
    getController().setTolerance(5);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
