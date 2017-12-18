package android.tom.playground.recommendation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.tom.playground.R;

public class RecommendedRead extends AppCompatActivity implements IRecomendedReadView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_read);
    }
}
