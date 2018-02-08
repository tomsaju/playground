package android.tom.playground.augmentedreality;

import android.Manifest;
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
    private Location lastLocation = null;
    private static Location kakkanadLocation;
    private double[] orientation;

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

        kakkanadLocation = new Location("manual");
        kakkanadLocation.setLatitude(10.01585);
        kakkanadLocation.setLongitude(76.34187);
        kakkanadLocation.setAltitude(19.5d);
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
            tryDrawing(surfaceHolder);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

          /*  camera.stopPreview();
            camera.release();
            camera = null;*/
        }
    };

    private void tryDrawing(SurfaceHolder surfaceHolder) {
        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas == null) {
            Log.e(TAG, "Cannot draw onto the canvas as it's null");
        } else {
            Log.e(TAG, "tryDrawing: drawing" );
            drawMyStuff(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawMyStuff(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);

        Camera.Parameters params = Camera.open().getParameters();
        float verticalFOV = params.getVerticalViewAngle();
        float horizontalFOV = params.getHorizontalViewAngle();

        float dx = (float) ( (canvas.getWidth()/ horizontalFOV) * (Math.toDegrees(orientation[0])-curBearingToMW));
        float dy = (float) ( (canvas.getHeight()/ verticalFOV) * Math.toDegrees(orientation[1])) ;

// wait to translate the dx so the horizon doesn't get pushed off
        canvas.translate(0.0f, 0.0f-dy);

// make our line big enough to draw regardless of rotation and translation
        canvas.drawLine(0f - canvas.getHeight(), canvas.getHeight()/2, canvas.getWidth()+canvas.getHeight(), canvas.getHeight()/2, paint);


// now translate the dx
        canvas.translate(0.0f-dx, 0.0f);

// draw our point -- we've rotated and translated this to the right spot already
        canvas.drawCircle(canvas.getWidth()/2, canvas.getHeight()/2, 8.0f, paint);
    }

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
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);

            String best = locationManager.getBestProvider(criteria, true);

            Log.v(TAG,"Best provider: " + best);

            locationManager.requestLocationUpdates(best, 50, 0, locationListener);
        }else{
            Toast.makeText(this, "provide permission and reload", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},124);
        }

    }


    private float curBearingToMW;
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            lastLocation = location;
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            altitude = location.getAltitude();

            Log.d(TAG, "onLocationChanged: Latitude :"+String.valueOf(latitude));
            Log.d(TAG, "onLocationChanged: Longitude :"+String.valueOf(longitude));
            Log.d(TAG, "onLocationChanged: Altitude :"+String.valueOf(altitude));

            latitudeValue.setText(String.valueOf(latitude));
            longitudeValue.setText(String.valueOf(longitude));
            altitudeValue.setText(String.valueOf(altitude));

             curBearingToMW = lastLocation.bearingTo(kakkanadLocation);
            Log.d(TAG, "onLocationChanged: bearing"+curBearingToMW);

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


    private float[] cameraRotation;
    private float[] rotation;
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
                orientation[0] = sensorEvent.values[0];
                orientation[1] = sensorEvent.values[1];
                orientation[2] = sensorEvent.values[2];

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
