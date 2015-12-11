package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.BeaconSeekerActivity;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class BeaconSeekerOp extends OpMode {
  private static final String  TAG              = "BeaconSeekerOp";

  @Override
  public void init() {
    BeaconSeekerActivity.mBeaconSeekerState = BeaconSeekerActivity.BeaconSeekerStateEnum.On;
  }

  @Override
  public void start() {

  }

  @Override
  public void loop() {

    telemetry.addData("Beacon X,Y (Pixels)", ((int) BeaconSeekerActivity.mBeaconCenterPointPixels.x) + "," + ((int) BeaconSeekerActivity.mBeaconCenterPointPixels.y));
    telemetry.addData("Beacon X,Y (Percent)", ((int) (100*BeaconSeekerActivity.mBeaconCenterPointPercent.x)) + "%, " +
            ((int) (100 * BeaconSeekerActivity.mBeaconCenterPointPercent.y)) + "%");

  }
  @Override
  public void stop() {
    BeaconSeekerActivity.mBeaconSeekerState = BeaconSeekerActivity.BeaconSeekerStateEnum.Off;
  }
}
