/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * Add your docs here.
 */
public class VorTXController extends XboxController {
	public JoystickButton a, b, x, y, lb, rb, back, start, ls, rs, lt, rt;

	public VorTXController(int port) {
		super(port);

		a = new JoystickButton(this, 1);
		b = new JoystickButton(this, 2);
		x = new JoystickButton(this, 3);
		y = new JoystickButton(this, 4);
		lb = new JoystickButton(this, 5);
		rb = new JoystickButton(this, 6);
		back = new JoystickButton(this, 7);
		start = new JoystickButton(this, 8);
		ls = new JoystickButton(this, 9);
		rs = new JoystickButton(this, 10);
	}

}