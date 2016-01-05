package com.qualcomm.ftcrobotcontroller.opmodes;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

public class DRWAuto extends PushBotTelemetry

{
    public DRWAuto ()

    {
        v_state = 0;
    } // DRWAuto
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
        update_telemetry (); // Update common telemetry
    }
    @Override public void loop ()
    {
        switch (v_state)
        {
            case 0:
                Log.i("test", "case 0");
                v_state++;

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

                if (a_right_encoder_count () < -19300)
                {
                    set_drive_power(0.0f, 0.0f);
                    v_state++;

                }
                break;
            case 3:
                Log.i("test", "case 3");
                run_using_encoders ();

                set_drive_power (-0.7f, -0.7f);

                if (a_right_encoder_count () < -29000)
                {
                    set_drive_power(0.0f, 0.0f);
                    v_state++;

                }
                break;
//            case 3:
//                Log.i("test", "case 3");
//                run_using_encoders ();
//
//                set_drive_power (0.7f, 0.7f);
//
//                if (has_right_drive_encoder_reached(20000))
//                {
//                    set_drive_power(0.0f, 0.0f);
//                    v_state++;
//                }
//                break;
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

} // DRWAuto
