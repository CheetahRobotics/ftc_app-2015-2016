package com.qualcomm.ftcrobotcontroller.opmodes;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.util.Log;

import com.qualcomm.ftcrobotcontroller.BeaconSeekerActivity;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class BeaconSeekerOp extends PushBotTelemetry {
    private static final String TAG = "BeaconSeekerOp";
    private static ToneGenerator toneGen1;
    @Override
    public void init() {
        Log.i("test", "in init()");

        BeaconSeekerActivity.enableBeaconSeeker();
        this.toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        this.toneGen1.startTone(ToneGenerator.TONE_SUP_CONFIRM,150);
        this.toneGen1.startTone(ToneGenerator.TONE_DTMF_0,150);
        this.toneGen1.startTone(ToneGenerator.TONE_DTMF_A,150);
    }

    @Override
    public void start() {
        Log.i("test", "in start()");

    }

    public int count = 0;
    @Override
    public void loop() {
        count = count + 1;
        telemetry.addData("XXXXCount", count);


//        telemetry.addData("Beacon X,Y (Pixels)", ((int) BeaconSeekerActivity.mBeaconCenterPointPixels.x) + "," + ((int) BeaconSeekerActivity.mBeaconCenterPointPixels.y));
//
//        telemetry.addData("Beacon X,Y (Percent)", ((int) (100 * BeaconSeekerActivity.mBeaconCenterPointPercent.x)) + "%, " +
//                ((int) (100 * BeaconSeekerActivity.mBeaconCenterPointPercent.y)) + "%");

        telemetry.addData("State = ", v_state);


        switch (v_state) {
            case 0:
                Log.i("test", "case 0");
                v_state++;

                break;

            case 1:
                run_using_encoders();

                if (count%50 == 0) {
                    telemetry.addData("Here! Count = ", count);
                    telemetry.addData("Beacon X,Y (Percent)", ((int) (100 * BeaconSeekerActivity.mBeaconCenterPointPercent.x)) + "%, " +
                            ((int) (100 * BeaconSeekerActivity.mBeaconCenterPointPercent.y)) + "%");
                    if (BeaconSeekerActivity.mBeaconCenterPointPixels.x > 0 && BeaconSeekerActivity.mBeaconCenterPointPixels.y > 0) {

                        if (BeaconSeekerActivity.mBeaconCenterPointPercent.y > 0.5) {
                            this.toneGen1.startTone(ToneGenerator.TONE_PROP_BEEP, 150);
                            telemetry.addData("Beacon Y (Percent)", " > 50 ");
                            // TURN LEFT!!!!
                            set_drive_power(-0.5f, 0.5f);
                        } else {
                            this.toneGen1.startTone(ToneGenerator.TONE_DTMF_A, 150);
                            telemetry.addData("Beacon Y (Percent)", " < 50 ");
                            set_drive_power(0.5f, -0.5f);
                            // TURN RIGHT!!!
                        }
                    }
                }
                telemetry.addData("Encoder", a_right_encoder_count());
                //were checking the right encoder count
                //Under this is checking both encodeer conuts
//                if (have_drive_encoders_reached(17501, 17501)) {
//                    set_drive_power(0.0f, 0.0f);
//                    v_state++;
//                }
                break;
            case 2:
                run_using_encoders();

                set_drive_power(0.0f, 0.0f);
                v_state++;
                break;
            default:
                break;
        }


    }

    @Override
    public void stop() {
        BeaconSeekerActivity.disableBeaconSeeker();
    }

    private int v_state = 0;
}
