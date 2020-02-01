/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class RobotMap {
    public static final class Drive {
        public static final int l1 = 14;
        public static final int l2 = 15;
        public static final int r1 = 0;
        public static final int r2 = 1;

    }

    public static final class Turret{
        public static final int motor = 3;
    }
    public static final class Shooter {
        public static final  int shooter1 = 8;
        public static final  int shooter2 = 9;

    }

    public static final class Constants {
        public static final double inchesPerTick = 3.8457;
    }
}
