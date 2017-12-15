package android.tom.playground;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.tom.playground.augmentedreality.AugmentedCameraActivity;
import android.tom.playground.augmentedreality.HorizonActivity;
import android.tom.playground.firebase.SignUpActivity;
import android.tom.playground.helper.DBHelper;
import android.tom.playground.parse.ParseTestActivity;
import android.tom.playground.rxjava.RxjavaActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

public class MainActivity extends AppCompatActivity {
    ListView mainList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);
        DBHelper dbHelper = new DBHelper(this,DBHelper.DB_NAME,null,DBHelper.DB_VERSION);
        mainList = (ListView) findViewById(R.id.homeList);
        String[] indexList = new String[]{"sensor fusion","butterknife","Firebase","Parse Buddy","Dagger 2","Accesibility tests","Rx Java","Augmented Reality"
        ,"Artificial Horizons (Might crash in 6.0 and above)"};

        ArrayAdapter listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1,indexList);
        mainList.setAdapter(listAdapter);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.CAMERA}, 123);

        }else {

        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){

        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},124);
        }
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 2:
                        Intent firebaseSignup = new Intent(MainActivity.this, SignUpActivity.class);
                        startActivity(firebaseSignup);
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, ParseTestActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(MainActivity.this, RxjavaActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(MainActivity.this, AugmentedCameraActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(MainActivity.this, HorizonActivity.class));
                        break;
                    default:
                }
               // Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
