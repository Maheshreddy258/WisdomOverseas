package com.example.mahesh.wisdomoverseas.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class PhoneCallReceiver extends BroadcastReceiver {

    TelephonyManager telManager;
    MediaRecorder recorder;
    Boolean recordStarted;

    @Override
    public void onReceive(Context context, Intent intent) {
       /* String action = intent.getAction();
        if (action.equals("android.intent.action.PHONE_STATE"))
        {
            // Phone call recording
            try {
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                //recorder.setOutputFile(<myoutput dir>);
                recorder.prepare();
                recorder.start();
                recordStarted = true;
                telManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
                telManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
            } catch(Exception ex) {

            }
        }
    }*/

    }
}


   /* private final PhoneStateListener phoneListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            try {
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING: {
                        //
                        break;
                    }
                    case TelephonyManager.CALL_STATE_OFFHOOK: {
                        //
                        break;
                    }
                    case TelephonyManager.CALL_STATE_IDLE: {
                        if (recordStarted) {
                            recorder.stop();
                            recordStarted = false;
                        }
                        break;
                    }
                    default: { }
                }
            } catch (Exception ex) {
            }
        }
    };
*/