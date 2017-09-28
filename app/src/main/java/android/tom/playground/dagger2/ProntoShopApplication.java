package android.tom.playground.dagger2;

import android.app.Application;
import android.tom.playground.dagger2.dagger.AppComponent;
import android.tom.playground.dagger2.dagger.AppModule;
import android.tom.playground.dagger2.dagger.DaggerAppComponent;

/**
 * Created by tom.saju on 9/28/2017.
 */

public class ProntoShopApplication extends Application {
    private static ProntoShopApplication instance  = new ProntoShopApplication();
    private static AppComponent appComponent;

    public static ProntoShopApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getAppComponent();
    }


   /* Dagger creates the concrete implementation using the @ annotations that we supplied and adds
    the prefix to Dagger to them so our AppComponent which is an interface will now have a concrete
            implementation called DaggerAppComponent.*/
    
    public AppComponent getAppComponent() {
        if (appComponent == null){
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
        return appComponent;
    }
}
