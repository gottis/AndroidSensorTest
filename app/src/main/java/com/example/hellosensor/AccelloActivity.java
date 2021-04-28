package com.example.hellosensor;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AccelloActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager mSensorManager;
    float ax,ay,az;
    TextView xyz;
    ConstraintLayout layout;
    float[] lowpass_vals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accello);

        xyz = (TextView) findViewById(R.id.xyz);
        layout = (ConstraintLayout) findViewById(R.id.layout);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        // for the system's orientation sensor registered listener

    }

    @Override
    protected void onPause() {
        super.onPause();
        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){

            lowpass_vals = lowPass(event.values, lowpass_vals);

            ax=lowpass_vals[0];
            ay=lowpass_vals[1];
            az=lowpass_vals[2];
            xyz.setText("x: " + roundAvoid(ax,2) + "\ny: " + roundAvoid(ay,2) + "\nz: " + roundAvoid(az, 2));
            xyz.invalidate();

            layout.setBackgroundColor(Color.rgb((int) (ax*25.5),(int) (ay*25.5),(int) (az*25.5)));
        }
    }
    protected float[] lowPass( float[] input, float[] output ) {
        float ALPHA = 0.25f;
        if ( output == null ) return input;
        for ( int i=0; i<input.length; i++ ) {
            output[i] = output[i] + ALPHA * (input[i] - output[i]);
        }
        return output;
    }

    private static double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}