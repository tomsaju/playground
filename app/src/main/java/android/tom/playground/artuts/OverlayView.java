package android.tom.playground.artuts;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.tom.playground.Manifest;
import android.util.Log;
import android.view.View;

/**
 * Created by tom.saju on 2/8/2018.
 */

public class OverlayView extends View implements SensorEventListener,LocationListener {
    public static final String DEBUG_TAG = "OverlayView Log";
    private final LocationManager locationManager;
    private final Location kakkanadLocation;
    private  float verticalFOV;
    private  float horizontalFOV;
    String accelData = "Accelerometer Data";
    String compassData = "Compass Data";
    String gyroData = "Gyro Data";
    private Location lastLocation = null;
    private float curBearingToMW;
    private float[] lastAccelerometer;
    private float[] lastCompass;
    private float[] orientation;
    private float[] rotation;
    private float[] identity;
    static final float ALPHA = 0.5f;


    public OverlayView(Context context) {
        super(context);
        SensorManager sensors = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor accelSensor = sensors.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor compassSensor = sensors.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor gyroSensor = sensors.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        boolean isAccelAvailable = sensors.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
        boolean isCompassAvailable = sensors.registerListener(this, compassSensor, SensorManager.SENSOR_DELAY_NORMAL);
        boolean isGyroAvailable = sensors.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);

        String best = locationManager.getBestProvider(criteria, true);

        Log.v(DEBUG_TAG,"Best provider: " + best);
        if(ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(best, 50, 0, this);
        }
        kakkanadLocation = new Location("manual");
        kakkanadLocation.setLatitude(10.01585);
        kakkanadLocation.setLongitude(76.34187);
        kakkanadLocation.setAltitude(19.5d);

        try {
            Camera camera = Camera.open();
            Camera.Parameters params = camera.getParameters();
            verticalFOV = params.getVerticalViewAngle();
            horizontalFOV = params.getHorizontalViewAngle();
            camera.release();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint contentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        contentPaint.setTextAlign(Paint.Align.CENTER);
        contentPaint.setTextSize(20);
        contentPaint.setColor(Color.RED);

        Paint targetPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        targetPaint.setTextAlign(Paint.Align.CENTER);
        targetPaint.setTextSize(20);
        targetPaint.setColor(Color.GREEN);
        canvas.drawText(accelData, canvas.getWidth()/2, canvas.getHeight()/4, contentPaint);
        canvas.drawText(compassData, canvas.getWidth()/2, canvas.getHeight()/2, contentPaint);
        canvas.drawText(gyroData, canvas.getWidth()/2, (canvas.getHeight()*3)/4, contentPaint);

        canvas.save();
        try {


            // use roll for screen rotation
            canvas.rotate((float)(0.0f- Math.toDegrees(orientation[2])));
// Translate, but normalize for the FOV of the camera -- basically, pixels per degree, times degrees == pixels
            float dx = (float) ( (canvas.getWidth()/ horizontalFOV) * (Math.toDegrees(orientation[0])-curBearingToMW));
            float dy = (float) ( (canvas.getHeight()/ verticalFOV) * Math.toDegrees(orientation[1])) ;

// wait to translate the dx so the horizon doesn't get pushed off
            canvas.translate(0.0f, 0.0f-dy);

// make our line big enough to draw regardless of rotation and translation
            canvas.drawLine(0f - canvas.getHeight(), canvas.getHeight()/2, canvas.getWidth()+canvas.getHeight(), canvas.getHeight()/2, targetPaint);


// now translate the dx
            canvas.translate(0.0f-dx, 0.0f);

// draw our point -- we've rotated and translated this to the right spot already
            canvas.drawCircle(canvas.getWidth()/2, canvas.getHeight()/2, 20.0f, targetPaint);
            canvas.restore();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        StringBuilder msg = new StringBuilder(event.sensor.getName()).append(" ");
        for(float value: event.values)
        {
            msg.append("[").append(value).append("]");
        }

        switch(event.sensor.getType())
        {
            case Sensor.TYPE_ACCELEROMETER:
                lastAccelerometer = lowPass(event.values.clone(),lastAccelerometer);
                //lastAccelerometer = event.values.clone();
                accelData = msg.toString();
                break;
            case Sensor.TYPE_GYROSCOPE:
                gyroData = msg.toString();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                lastCompass = lowPass(event.values.clone(),lastCompass);
                //lastCompass = event.values.clone();
                compassData = msg.toString();
                break;
        }
// compute rotation matrix
        try {
             rotation = new float[9];
             identity = new float[9];
            boolean gotRotation = SensorManager.getRotationMatrix(rotation,
                    identity, lastAccelerometer, lastCompass);

            if (gotRotation) {
                float cameraRotation[] = new float[9];
                // remap such that the camera is pointing straight down the Y axis
                SensorManager.remapCoordinateSystem(rotation, SensorManager.AXIS_X,
                        SensorManager.AXIS_Z, cameraRotation);

                // orientation vector
                 orientation = new float[3];
                SensorManager.getOrientation(cameraRotation, orientation);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        this.invalidate();
    }


    protected float[] lowPass( float[] input, float[] output ) {
        if ( output == null ) return input;

        for ( int i=0; i<input.length; i++ ) {
            output[i] = output[i] + ALPHA * (input[i] - output[i]);
        }
        return output;
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
    lastLocation = location;
        curBearingToMW = lastLocation.bearingTo(kakkanadLocation);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
