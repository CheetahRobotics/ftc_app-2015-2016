package com.qualcomm.ftcrobotcontroller.opmodes;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.util.Log;

import com.qualcomm.robotcore.hardware.TouchSensor;

public class DRWAutoBlue extends PushBotTelemetry
{
    double _start_arm_encoder_count = 0;
    double _amount_rotated;
    private TouchSensor v_sensor_touch;
//    private static ToneGenerator toneGen1;

    public DRWAutoBlue()

    {
        v_state = 0;
    } // DRWAutoBlue
    @Override public void start ()
    {
        super.start();
        Log.i("test", "start");
    }
    @Override public void init() {
        super.init();
        reset_drive_encoders();
        v_state = 0;
        Log.i("test", "init");
        v_sensor_touch = hardwareMap.touchSensor.get("sensor_touch");
        update_telemetry (); // Update common telemetry
    }
    @Override public void loop ()
    {
        telemetry.addData("12", "Touch: " + v_sensor_touch.isPressed());
        switch (v_state)
        {
            case 0:
                Log.i("test", "case 0");
                v_state++;
                _start_arm_encoder_count = a_left_arm_encoder_count();

                break;
            case 1:
                Log.i("test", "case 1");
                run_using_encoders();

                set_drive_power (-0.7f, -0.7f);

                if (a_right_encoder_count () < -16501)
                {
                    set_drive_power(0.0f, 0.0f);
                    v_state++;
                }
                break;
            case 2:
                Log.i("test", "case 2");
                run_using_encoders ();

                set_drive_power (0.5f, -0.5f);

                if (a_right_encoder_count () < -19500)
                {
                    set_drive_power(0.0f, 0.0f);
                    v_state++;

                }
                break;
            case 3:
                Log.i("test", "case 3");
                run_using_encoders ();

                set_drive_power (-0.7f, -0.7f);

                if (a_right_encoder_count () < -42500 || v_sensor_touch.isPressed())
                {
                    set_drive_power(0.0f, 0.0f);
                    v_state++;

                }
                break;
            case 4:
                Log.i("test", "case 4");
                run_using_encoders();
                m_left_arm_power(-.5);
                _amount_rotated = _start_arm_encoder_count = - a_left_arm_encoder_count();
//                DRWAutoBlue.toneGen1.startTone(ToneGenerator.TONE_PROP_BEEP, 150);

                if (_amount_rotated > 4240)
                {
                    m_left_arm_power(0.0);
                    v_state++;
                }
                break;
//
        }

        // Send telemetry data to the driver station.
        update_telemetry(); // Update common telemetry
        telemetry.addData ("20", "State: " + v_state);

    } // loop

    //--------------------------------------------------------------------------
    //
    // v_state
    //
    /**
     * This class member remembers which state is currently active.  When the
     * start method is called, the state will be initialized (0).  When the loop
     * starts, the state will change from initialize to state_1.  When state_1
     * actions are complete, the state will change to state_2.  This implements
     * a state machine for the loop method.
     */
    private int v_state = 0;

} // DRWAutoBlue
