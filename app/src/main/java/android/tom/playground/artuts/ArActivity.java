package android.tom.playground.artuts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.tom.playground.R;
import android.widget.FrameLayout;

public class ArActivity extends AppCompatActivity {
    /**
     * https://code.tutsplus.com/tutorials/android-sdk-augmented-reality-camera-sensor-setup--mobile-7873
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);

        FrameLayout arViewPane = (FrameLayout) findViewById(R.id.ar_view_pane);

        ArDisplayView arDisplay = new ArDisplayView(this,this);
        arViewPane.addView(arDisplay);

        OverlayView arContent = new OverlayView(getApplicationContext());
        arViewPane.addView(arContent);
    }
}
