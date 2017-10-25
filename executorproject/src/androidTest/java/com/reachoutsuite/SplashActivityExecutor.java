package com.reachoutsuite;

import android.app.Activity;
import com.robotium.recorder.executor.Executor;

@SuppressWarnings("rawtypes")
public class SplashActivityExecutor extends Executor {

	@SuppressWarnings("unchecked")
	public SplashActivityExecutor() throws Exception {
		super((Class<? extends Activity>) Class.forName("com.reachoutsuite.views.splash.SplashActivity"),  "com.reachoutsuite.R.id.", new android.R.id(), false, false, "1507787982274");
	}

	public void setUp() throws Exception { 
		super.setUp();
	}
}
