package com.qualcomm.ftcrobotcontroller.opmodes;

import android.util.Log;

import com.qualcomm.ftcrobotcontroller.BeaconSeekerActivity;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class BeaconSeekerOp extends PushBotTelemetry {
    private static final String TAG = "BeaconSeekerOp";

    @Override
    public void init() {
        BeaconSeekerActivity.enableBeaconSeeker();
    }

    @Override
    public void start() {

    }

    public int count = 0;
    @Override
    public void loop() {
        count = count + 1;
        telemetry.addData("Count", count);


        telemetry.addData("Beacon X,Y (Pixels)", ((int) BeaconSeekerActivity.mBeaconCenterPointPixels.x) + "," + ((int) BeaconSeekerActivity.mBeaconCenterPointPixels.y));

        telemetry.addData("Beacon X,Y (Percent)", ((int) (100 * BeaconSeekerActivity.mBeaconCenterPointPercent.x)) + "%, " +
                ((int) (100 * BeaconSeekerActivity.mBeaconCenterPointPercent.y)) + "%");

        switch (v_state) {
            case 0:
                Log.i("test", "case 0");
                v_state++;

                break;
            case 1:
                Log.i("test", "case 1");
                run_using_encoders();

                set_drive_power(0.5f, 0.5f);

                if (have_drive_encoders_reached(16501, 16501)) {
                    set_drive_power(0.0f, 0.0f);
                    v_state++;
                }
                break;
            case 2:
                Log.i("test", "case 2");
                run_using_encoders();

                if (BeaconSeekerActivity.mBeaconCenterPointPercent.x > 0.5) {
                    telemetry.addData("Beacon X (Percent)", " > 50 right rightMotor = hardwareMap.dcMotor.get(\"right_drive\");");
                    set_drive_power(-0.5f, 0.5f);
                }
                else {
                    telemetry.addData("Beacon X (Percent)", " < 50 left leftMotor = hardwareMap.dcMotor.get(\"left_drive\");");
                    set_drive_power(0.5f, -0.5f);
                }

                if (has_right_drive_encoder_reached(4880)) {
                    set_drive_power(0.0f, 0.0f);
                    v_state++;

                }
                break;
            case 3:
                Log.i("test", "case 3");
                run_using_encoders();

                set_drive_power(0.5f, 0.5f);

                if (has_right_drive_encoder_reached(7880)) {
                    set_drive_power(0.0f, 0.0f);
                    v_state++;
                }
                break;

        }


    }

    @Override
    public void stop() {
        BeaconSeekerActivity.disableBeaconSeeker();
    }

    private int v_state = 0;
}
