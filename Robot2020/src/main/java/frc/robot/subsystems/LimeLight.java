/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimeLight extends SubsystemBase {
  

    NetworkTable table;
    NetworkTableEntry tx,ty, ta, ts,tl,tv,getpipe,camtran;

    // tv	Whether the limelight has any valid targets (0 or 1)
    // tx	Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
    // ty	Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
    // ta	Target Area (0% of image to 100% of image)


    NetworkTableEntry ledMode, camMode, pipeline, stream, snapshot;

    double txValue, taValue, distance, camtranValue;

    double x, y, z, yaw;

    public LimeLight() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        ts = table.getEntry("ts");
        tl = table.getEntry("tl");
        tv = table.getEntry("tv");
        getpipe = table.getEntry("getpipe");
        ledMode = table.getEntry("ledMode");
        camMode = table.getEntry("camMode");
        pipeline = table.getEntry("pipeline");
        stream = table.getEntry("stream");
        snapshot = table.getEntry("snapshot");
        camtran = table.getEntry("camtran");

        // change these values when testing.
        // mountAngle = 0;
        // mountHeight = 11;
        // targetHeight = 30;
        //

        setCamMode(0);
        setLedMode(0);
        setStreamMode(0);

        setPipeline(1.0);

    }

    // setters
    /**
     * @return the tx
     */
    public double getTx() {
        if (tv.getDouble(0.0) == 1) {
            txValue = tx.getDouble(txValue);
        } else {
            txValue = 0.0;
        }
        return txValue;
    }

    /**
     * @return the ty
     */
    public double getTy() {
        return ty.getDouble(0.0);
    }

    public double getTv() {
        return tv.getDouble(0.0);
    }

    /**
     * @return the ta
     */
    public double getTa() {
        if (tv.getDouble(0.0) == 1) {
            taValue = ta.getDouble(taValue);
        }
        return taValue;
    }

    /**
     * @return the ts
     */
    public double getTs() {
        return ts.getDouble(0.0);
    }

    /**
     * @return the tl
     */
    public double getTl() {
        return tl.getDouble(0.0);
    }

    public double getDistance() {
        return 64.958 * Math.pow(getTa(), -.695);
    }

    public void setLedMode(double ledType) {
        ledMode.setNumber(ledType);
    }

    public void setCamMode(double CamType) {
        camMode.setNumber(CamType);
    }

    public void setPipeline(double Pipeline) {
        pipeline.setNumber(Pipeline);
    }

    public void setStreamMode(double Stream) {
        stream.setNumber(Stream);
    }

    public void setSnapshot(double Snapshot) {
        snapshot.setNumber(Snapshot);
    }

  @Override
  public void periodic() {
      //All these values can be found in network tables
      SmartDashboard.putNumber("tx", getTx());
      SmartDashboard.putNumber("ty", getTy());
      SmartDashboard.putNumber("ta", getTa());
      SmartDashboard.putNumber("tv" , getTv());
      // SmartDashboard.putNumber("Distance", getDistance());  }
    }  

}
