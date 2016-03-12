package com.example.ninatran.represent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

    private TextView mTextView;
    private Button mBtn;
    private SensorManager sSensorManager;
    private Sensor sAccelerometer;
    private long lastUpdate = 0;
    private float lastX, lastY, lastZ;
    private static final int SHAKE_THRESHOLD = 600;
    private String json;



    public void buttonList(View v) {
        Intent intent = new Intent(this, CongressActivity.class);
        intent.putExtra("JSON", json);
        Log.d("JSON", "it is " + json);
        startActivity(intent);
        setActivityBackgroundColor(0x083D85);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtn = (Button) findViewById(R.id.zipcodeBttn);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            json = extras.getString("JSON");
            mBtn.setText("Results");
            Log.d("Get", "Json is" + json);
        }

        sSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sAccelerometer = sSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sSensorManager.registerListener(this, sAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mSensor = event.sensor;
        if (mSensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long currentTime = System.currentTimeMillis();
//            Log.d("CheckX", "value of x is " + Float.toString(x));
            if ((currentTime - lastUpdate) > 100) {
                long diff = currentTime - lastUpdate;
                lastUpdate = currentTime;

                float speed = Math.abs(x - lastX)/diff*10000;
//                String spd = Float.toString(speed);
//                Log.d("CheckSpeed", "   " + speed);
                if (speed > SHAKE_THRESHOLD) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                lastX = x;
//                Log.d("CheckLastX", "value of x is " + Float.toString(lastX));
                lastY = y;
                lastZ = z;

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onPause() {
        super.onPause();
        sSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        sSensorManager.registerListener(this, sAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }

}
