package android.tom.playground;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.tom.playground.firebase.SignUpActivity;
import android.tom.playground.parse.ParseTestActivity;
import android.tom.playground.rxjava.RxjavaActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ListView mainList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainList = (ListView) findViewById(R.id.homeList);
        String[] indexList = new String[]{"sensor fusion","butterknife","Firebase","Parse Buddy","Dagger 2","Accesibility tests","Rx Java"};

        ArrayAdapter listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1,indexList);
        mainList.setAdapter(listAdapter);

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
                    default:
                }
               // Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
