package android.tom.playground.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.tom.playground.MainActivity;
import android.tom.playground.R;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import rx.functions.Action1;

/**
 * Based on a three part tutorial at:
 * https://code.tutsplus.com/tutorials/rxjava-for-android-apps-introducing-rxbinding-and-rxlifecycle--cms-28565
 * https://code.tutsplus.com/tutorials/reactive-programming-operators-in-rxjava-20--cms-28396
 * https://code.tutsplus.com/tutorials/getting-started-with-rxjava-20-for-android--cms-28345
 */

public class RxjavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);

        Button button = (Button) findViewById(R.id.button);
        final TextView textView = (TextView) findViewById(R.id.textView);
        EditText editText = (EditText) findViewById(R.id.editText);

        //Code for the bindings goes here//
        //...//
      RxView.clicks(button).subscribe(new Action1<Void>() {
          @Override
          public void call(Void aVoid) {
              Toast.makeText(RxjavaActivity.this, "Rx button click", Toast.LENGTH_SHORT).show();
          }
      });


       /* //if using lambdas
        RxView.clicks(button)
                .subscribe(aVoid -> *//* do something *//*);
        */

        RxTextView.textChanges(editText).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                textView.setText(charSequence);
            }
        });


    }
}
