package android.tom.playground.parse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.tom.playground.R;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import static android.R.attr.password;

public class ParseTestActivity extends AppCompatActivity {
EditText input;
    Button push,read;
    TextView banner;
    String readData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_test);

        input = (EditText) findViewById(R.id.edit_input);
        push = (Button) findViewById(R.id.upload);
        read = (Button) findViewById(R.id.display);
        banner = (TextView) findViewById(R.id.banner);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("1MRzpKuk79BplPsed9dIzE5gnLFHeDDrfD3zlS91")
                .clientKey("JqIyejMSGHE3rtIsteFE4YDlxklQSoWsEurCl7JU")
                .server("https://parseapi.back4app.com/").build()
        );

        ParseObject gameScore = new ParseObject("GameScore");
        gameScore.put("score", 1337);
        gameScore.put("playerName", "Sean Plott");
        gameScore.put("cheatMode", false);
        gameScore.saveInBackground();

        push.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String inputText = input.getText().toString();
            if(!TextUtils.isEmpty(inputText)){
                ParseObject status = new ParseObject("status");
                status.put("user_id","18773");
                status.put("status_text",inputText);
                status.saveInBackground();
            }

            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readData();
            }
        });

    }

    private void readData() {
        readData = "";
        ParseQuery<ParseObject> query = ParseQuery.getQuery("status");
        query.whereEqualTo("user_id","18773");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> userList, ParseException e) {
                Log.d("klakla", "get: " + e+userList.size());
                if (e == null) {
                    if (userList.size()>0) {

                        for (int i = 0; i < userList.size(); i++) {
                            ParseObject p = userList.get(i);
                            readData = p.getString("status_text");

                        }
                        banner.setText(readData);}

                }
                else {
                    Log.d("score", "Error: " + e.getMessage());
                    // Alert.alertOneBtn(getActivity(),"Something went wrong!");
                }
            }
        });

    }

}
