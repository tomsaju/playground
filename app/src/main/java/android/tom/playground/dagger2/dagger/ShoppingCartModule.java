package android.tom.playground.dagger2.dagger;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.tom.playground.dagger2.ShoppingCart;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tom.saju on 9/28/2017.
 */

/**
 * Same with the AppModule.java we are annotating this class with @Module annotation.
 * We are saying wherever in our application that we need the ShoppingCart, create a new instance of the ShoppingCart
 * and since the ShoppingCart has a dependency on SharedPreference go ahead and satisfy that dependency by asking the PreferenceManager.
 * And since the PreferenceManager has a dependency on Context before it can fulfill the request for a SharedPreference,
 * Dagger 2 now knows to go to our AppModule and satisfy that dependency by returning our Application class.
 */


@Module
public class ShoppingCartModule {

    @Provides @Singleton
    ShoppingCart provideShoppingCart(SharedPreferences sharedPreferences){
        return  new ShoppingCart(sharedPreferences);
    }

    @Provides @Singleton
    SharedPreferences provideSharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

}
