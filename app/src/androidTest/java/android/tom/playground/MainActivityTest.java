package android.tom.playground;

import android.test.ActivityInstrumentationTestCase2;
import android.tom.playground.firebase.LoginActivity;
import android.tom.playground.firebase.SignUpActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.robotium.solo.Solo;

import org.junit.Test;

/**
 * Created by tom.saju on 10/12/2017.
 */

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;
    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testActivityisMain(){
        solo.assertCurrentActivity("Not Main Activity",MainActivity.class);
    }

    public void testclickOnList(){
        solo.clickInList(3);
        solo.assertCurrentActivity("Not Sign up Activity",SignUpActivity.class);
    }

    public void testRxJava(){
        solo.clickInList(7);
        solo.waitForText("Button");
        solo.clearEditText((EditText)solo.getView(R.id.editText));
        solo.enterText((EditText)solo.getView(R.id.editText),"This is a sample");
        TextView tv = (TextView) solo.getView(R.id.textView);
        String text = tv.getText().toString();
        assertEquals(text,"This is a sample");

    }

    public void testRxJavaButton(){
        solo.clickInList(7);
        solo.waitForText("Button");
        solo.clickOnButton("Button");
        solo.searchText("Rx button click");
    }

    public void testFirstItemofList(){
        ListView list = (ListView) solo.getView(R.id.homeList);
        View view=list.getChildAt(0);
        TextView title=(TextView) view.findViewById(android.R.id.text1);
        String text = title.getText().toString();
        assertEquals("sensor fusion",text);
    }
}
