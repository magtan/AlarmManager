package com.example.magnust.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.text.Format;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    final static private long ONE_SECOND = 1000;
    final static private long TWENTY_SECONDS = ONE_SECOND * 20;
    PendingIntent pi;
    BroadcastReceiver br;
    AlarmManager am;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
        findViewById(R.id.the_button).setOnClickListener(this);
    }

    private void setup() {
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent i) {
                Toast.makeText(c, "Pull weather data", Toast.LENGTH_LONG).show();
                Log.i("DATE", "Alarm!");
            }
        };
        registerReceiver(br, new IntentFilter("com.example.magnust.myapplication") );
        pi = PendingIntent.getBroadcast( this, 0, new Intent("com.example.magnust.myapplication"),
                0 );
        am = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));
    }
    @Override
    public void onClick(View v) {
        /*am.set( AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() +
                TWENTY_SECONDS, pi );
        */
        Calendar cal = Calendar.getInstance();
        am.setRepeating(AlarmManager.RTC,cal.getTimeInMillis() + 10L*1000L,1L*1000L,pi);
        Log.i("DATE", "Er satt!");
    }

    @Override
    protected void onDestroy() {
        am.cancel(pi);
        unregisterReceiver(br);
        super.onDestroy();
    }
}
