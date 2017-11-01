package android.tom.playground.augmentedreality;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.tom.playground.R;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Permission;

public class AugmentedCameraActivity extends AppCompatActivity {
    private static final String TAG = "AugmentedCameraActivity";
    SensorManager sensorManager;
    int orientationSensor;
    float headingAngle;
    float pitchAngle;
    float rollAngle;
    int accelerometerSenesor;
    float xAxis;
    float yAxis;
    float zAxis;
    LocationManager locationManager;
    double latitude;
    double longitude;
    double altitude;
    SurfaceView cameraPreview;
    SurfaceHolder previewHolder;
    Camera camera;
    boolean inPreview;
    TextView xAxisValue;
    TextView yAxisValue;
    TextView zAxisValue;
    TextView headingValue;
    TextView pitchValue;
    TextView rollValue;
    TextView altitudeValue;
    TextView latitudeValue;
    TextView longitudeValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_augmented_camera);

        inPreview = false;
        cameraPreview = (SurfaceView) findViewById(R.id.cameraPreview);
        xAxisValue = (TextView) findViewById(R.id.xAxisValue);
        yAxisValue = (TextView) findViewById(R.id.yAxisValue);
        zAxisValue = (TextView) findViewById(R.id.zAxisValue);
        headingValue = (TextView) findViewById(R.id.headingValue);
        pitchValue = (TextView) findViewById(R.id.pitchValue);
        rollValue = (TextView) findViewById(R.id.rollValue);
        altitudeValue = (TextView) findViewById(R.id.altitudeValue);
        longitudeValue = (TextView) findViewById(R.id.longitudeValue);
        latitudeValue = (TextView) findViewById(R.id.latitudeValue);
        previewHolder = cameraPreview.getHolder();
        previewHolder.addCallback(SurfaceCallBack);
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        orientationSensor = Sensor.TYPE_ORIENTATION;
        accelerometerSenesor = Sensor.TYPE_ACCELEROMETER;
        sensorManager.registerListener(sensorEventListener,sensorManager.getDefaultSensor(orientationSensor),sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListener,sensorManager.getDefaultSensor(accelerometerSenesor),sensorManager.SENSOR_DELAY_NORMAL);
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 2, locationListener);
        }else{
            Toast.makeText(this, "provide permission and reload", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},124);
        }
    }

    SurfaceHolder.Callback SurfaceCallBack = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try{
            camera.setPreviewDisplay(previewHolder);
        }catch(Exception e){
            e.printStackTrace();
        }
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder,  int format, int width, int
                height) {

            Camera.Parameters parameters = camera.getParameters();
            Camera.Size size = getBestPreviewSize(width,height,parameters);
            if(size!=null){

                parameters.setPreviewSize(size.width,size.height);
                camera.setParameters(parameters);
                camera.startPreview();
                inPreview = true;
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

          /*  camera.stopPreview();
            camera.release();
            camera = null;*/
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 123);

        }else {
            camera = Camera.open();
        }
        sensorManager.registerListener(sensorEventListener,sensorManager.getDefaultSensor(orientationSensor),sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListener,sensorManager.getDefaultSensor(accelerometerSenesor),sensorManager.SENSOR_DELAY_NORMAL);
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 2, locationListener);
        }else{
            Toast.makeText(this, "provide permission and reload", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},124);
        }

    }


    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            altitude = location.getAltitude();

            Log.d(TAG, "onLocationChanged: Latitude :"+String.valueOf(latitude));
            Log.d(TAG, "onLocationChanged: Longitude :"+String.valueOf(longitude));
            Log.d(TAG, "onLocationChanged: Altitude :"+String.valueOf(altitude));

            latitudeValue.setText(String.valueOf(latitude));
            longitudeValue.setText(String.valueOf(longitude));
            altitudeValue.setText(String.valueOf(altitude));

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
    };


    final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if(sensorEvent.sensor.getType()==Sensor.TYPE_ORIENTATION){

                headingAngle = sensorEvent.values[0];
                pitchAngle = sensorEvent.values[1];
                rollAngle = sensorEvent.values[2];

                Log.d(TAG, "onSensorChanged: Heading :"+String.valueOf(headingAngle));
                Log.d(TAG, "onSensorChanged: PitchAngle :"+String.valueOf(pitchAngle));
                Log.d(TAG, "onSensorChanged: rollAngle :"+String.valueOf(rollAngle));

                headingValue.setText(String.valueOf(headingAngle));
                pitchValue.setText(String.valueOf(pitchAngle));
                rollValue.setText(String.valueOf(rollAngle));

            }else if(sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER){

                xAxis = sensorEvent.values[0];
                yAxis = sensorEvent.values[1];
                zAxis = sensorEvent.values[2];

                Log.d(TAG, "onSensorChanged: xAxis :"+String.valueOf(xAxis));
                Log.d(TAG, "onSensorChanged: yAxis :"+String.valueOf(yAxis));
                Log.d(TAG, "onSensorChanged: zAxis :"+String.valueOf(zAxis));

                xAxisValue.setText(String.valueOf(xAxis));
                yAxisValue.setText(String.valueOf(yAxis));
                zAxisValue.setText(String.valueOf(zAxis));

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
    @Override
    public void onPause() {
        if (inPreview) {
            camera.stopPreview();
        }
        camera.release();
        camera=null;
        inPreview=false;
        sensorManager.unregisterListener(sensorEventListener);
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            locationManager.removeUpdates(locationListener);
        }
        super.onPause();
    }
    private Camera.Size getBestPreviewSize(int width, int height,
                                           Camera.Parameters parameters) {
        Camera.Size result=null;
        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width<=width && size.height<=height) {
                if (result==null) {
                    result=size;
                }
                else {
                    int resultArea=result.width*result.height;
                    int newArea=size.width*size.height;
                    if (newArea>resultArea) {
                        result=size;
                    }
                }
            }
        }
        return(result);
    }
}
