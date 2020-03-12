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
        public static final int l1 = 0;
        public static final int l2 = 1;
        public static final int r1 = 14;
        public static final int r2 = 15;

    }

    public static final class Turret {
        public static final int motor = 10;
        public static final double turretRotationsPerTick = 4100;
    }

    public static final class Shooter {
        // done
        public static final int shooter1 = 12;
        public static final int shooter2 = 2;
        public static final int gate1 = 13;
        public static final int gate2 = 3;

    }

    public static final class Hood {
        // done
        public static final int hoodMotor = 11;

    }

    public static final class Intake {
        public static final int pivot = 9;
        public static final int rollers = 7;
        public static final int belt = 4;
    }

    public static final class Climber {
        public static final int releaser = 6;
        public static final int winch = 5;
    }

    public static final class Constants {
        public static final double inchesPerTick = .00124724;
    }
}
