package android.tom.playground.unicodetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.tom.playground.R;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SimActivity extends AppCompatActivity {

    private List<String> currencyList;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim);
        textView = (TextView) findViewById(R.id.textdummy);
        textView.setText(Html.fromHtml("\u20A0"));
        currencyList = new ArrayList<>();
        currencyList.add("20A0");
    }
}
