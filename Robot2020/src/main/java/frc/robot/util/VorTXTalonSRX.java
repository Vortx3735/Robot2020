/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

import javax.management.MXBean;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * Add your docs here.
 */
public class VorTXTalonSRX extends WPI_TalonSRX {

    private String name;
    private int timer = 0;

    // We update each synchronisly, so that we can only multiply, which is faster
    double ticksPerInch = 1;
    double inchesPerTick = 1;

    public VorTXTalonSRX[] followers;

    public VorTXTalonSRX(int id) {
        this(id, "Talon " + (int) Math.abs(id));
    }

    public VorTXTalonSRX(int id, String name) {
        super((int) Math.abs(id));
        this.name = name;
        this.setInverted((id < 0) ? true : false);
    }

    // Array of VortxTalons with 1st ->.... follow 0th VortxTalon.
    public VorTXTalonSRX(int[] ids, String name) {
        this(ids[0], name);
        followers = new VorTXTalonSRX[ids.length - 1];
        for (int i = 0; i < followers.length; i++) {
            followers[i] = new VorTXTalonSRX(ids[i + 1]);
            followers[i].follow(this);
        }
    }

    @Override
    public void setNeutralMode(NeutralMode mode) {
        super.setNeutralMode(mode);
        if (followers != null) {
            for (VorTXTalonSRX t : followers) {
                t.setNeutralMode(mode);
            }
        }
    }

    public ErrorCode configContinuousCurrentLimit(int maxCurrent) {
        super.configContinuousCurrentLimit(maxCurrent, 0);
        if (followers != null) {
            for (VorTXTalonSRX t : followers) {
                t.configContinuousCurrentLimit(maxCurrent, 0);
            }
        }
        return null;
    }

    @Override
    public void enableCurrentLimit(boolean val) {
        super.enableCurrentLimit(val);
        if (followers != null) {
            for (VorTXTalonSRX t : followers) {
                t.enableCurrentLimit(val);
            }
        }
    } 

    public void setTicksPerInch(double ticks) {
        this.ticksPerInch = ticks;
        this.inchesPerTick = 1.0 / ticks;
    }

    public void setInchesPerTick(double inches) {
        this.inchesPerTick = inches;
        this.ticksPerInch = 1.0 / inches;
    }

    public void initSensor(FeedbackDevice device, boolean phase) {
        this.configSelectedFeedbackSensor(device, 0, 0);
        this.setSelectedSensorPosition(0, 0, 0);
        this.setSensorPhase(phase);
        this.configNominalOutputForward(0, 0);
        this.configNominalOutputReverse(0, 0);
        this.configPeakOutputForward(1, 0);
        this.configPeakOutputReverse(-1, 0);
        // this.configClosedLoopPeakOutput(0, .7, 0);

    }

    public void resetPosition() {
        this.setSelectedSensorPosition(0, 0, 0);
    }

    public double getPosition() {
        return super.getSelectedSensorPosition(0) * inchesPerTick; // return inches
    }

    // @return: speed in in/m
    public double getSpeed() {
        return super.getSelectedSensorVelocity(0) * inchesPerTick; // returns
    }

    @Override
    public double getTemperature() {
        // in C
        return super.getTemperature();
    }

    @Override
    public void setInverted(boolean isInverted) {
        super.setInverted(isInverted);
        if (followers != null) {
            for (VorTXTalonSRX talon : followers) {
                talon.setInverted(isInverted);
            }
        }

    }
}
