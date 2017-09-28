package android.tom.playground.dagger2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.tom.playground.R;

/**
 * Based on tutorial at http://valokafor.com/learn-android-dagger-2/
 */
public class DaggerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);
    }
}
