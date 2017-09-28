package android.tom.playground.dagger2.dagger;

/**
 * Created by tom.saju on 9/28/2017.
 */

import android.content.Context;
import android.tom.playground.dagger2.ProntoShopApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 *  Module is another one of the three leg that Dagger 2 stands upon.
 *  This is a standard Java class that will be decorated with Dagger 2 specific annotations and it is the methods
 *  in this class that provide the dependencies.This particular module provides the Context.
 */

/**
 * Notice the @Module annotation at the top of the class.
 * That is what signifies to Dagger 2 that this is a Module.
 * Modules in Dagger 2 contain methods which are essentially advanced switch statements.
 * So here we are saying, case Context: return the Application class.
 * It is best practices to name those method names that begin with provide
 */

@Module
public class AppModule {
    private final ProntoShopApplication app;

    public AppModule(ProntoShopApplication app){
        this.app = app;
    }

    @Provides @Singleton
    public Context provideContext(){
        return app;
    }
}
