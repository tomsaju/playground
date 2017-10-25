package android.tom.playground.rxjava;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.tom.playground.MainActivity;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * Created by tom.saju on 10/12/2017.
 */
@RunWith(AndroidJUnit4.class)
public class RxjavaActivityTest {

    private Solo solo;

    @Rule
    public ActivityTestRule<RxjavaActivity> mActivityTestRule = new ActivityTestRule<>(RxjavaActivity.class);

    @Before
    public void setUp(){
        solo = new Solo(getInstrumentation(),mActivityTestRule.getActivity());
    }

    @Test
    public void testifActivityisDisplayed(){
        solo.assertCurrentActivity("Not Rx Activity",RxjavaActivity.class);
    }
}
