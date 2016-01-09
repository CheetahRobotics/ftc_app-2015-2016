package com.qualcomm.ftcrobotcontroller.opmodes;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

public class DRWAutoArm extends PushBotTelemetry

{
    double _amount_rotated;
    double _start_arm_encoder_count = 0;
    public DRWAutoArm ()

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
//        update_telemetry (); // Update common telemetry
    }
    @Override public void loop ()
    {
        switch (v_state)
        {
            case 0:
                Log.i("test", "case 0");
                _start_arm_encoder_count = a_left_arm_encoder_count();
                v_state++;

                break;
            case 1:
                Log.i("test", "case 1");
                run_using_encoders();
                m_left_arm_power(-0.1);
                _amount_rotated = _start_arm_encoder_count - a_left_arm_encoder_count();
                if (_amount_rotated > 4240)
                {
                    m_left_arm_power (0.0);
                    v_state++;
                }
                break;
        }

        // Send telemetry data to the driver station.
//        update_telemetry (); // Update common telemetry
        telemetry.addData ("02", "amount rotated: " + _amount_rotated);

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
