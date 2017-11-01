package android.tom.playground.augmentedreality;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.tom.playground.R;

public class HorizonActivity extends AppCompatActivity {
    float[] aValues = new float[3];
    float[] mValues = new float[3];
    HorizonView horizonView;
    SensorManager sensorManager;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizon);
        horizonView = (HorizonView)this.findViewById(R.id.horizonView);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        updateOrientation(new float[] {0, 0, 0});
    }

    private void updateOrientation(float[] floats) {
        if (horizonView!= null) {
            horizonView.setBearing(floats[0]);
            horizonView.setPitch(floats[1]);
            horizonView.setRoll(-floats[2]);
            horizonView.invalidate();
        }
    }

    private float[] calculateOrientation() {
        float[] values = new float[3];
        float[] R = new float[9];
        float[] outR = new float[9];
        SensorManager.getRotationMatrix(R, null, aValues, mValues);
        SensorManager.remapCoordinateSystem(R,
                SensorManager.AXIS_X,
                SensorManager.AXIS_Z,
                outR);
        SensorManager.getOrientation(outR, values);
        values[0] = (float) Math.toDegrees(values[0]);
        values[1] = (float) Math.toDegrees(values[1]);
        values[2] = (float) Math.toDegrees(values[2]);
        return values;
    }

    private final SensorEventListener sensorEventListener = new
            SensorEventListener() {
                public void onSensorChanged(SensorEvent event) {
                    if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
                        aValues = event.values;
                    if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
                        mValues = event.values;
                    updateOrientation(calculateOrientation());
                }
                public void onAccuracyChanged(Sensor sensor, int accuracy) {}
            };

    @Override
    protected void onResume() {
        super.onResume();
        Sensor accelerometer =
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor magField =
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(sensorEventListener,
                accelerometer,
                SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(sensorEventListener,
                magField,
                SensorManager.SENSOR_DELAY_FASTEST);
    }



    @Override
    protected void onStop() {
        sensorManager.unregisterListener(sensorEventListener);
        super.onStop();
    }


    }
