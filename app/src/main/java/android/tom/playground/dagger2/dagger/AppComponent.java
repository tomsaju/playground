package android.tom.playground.dagger2.dagger;

import android.tom.playground.dagger2.DaggerActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tom.saju on 9/28/2017.
 */

/**
 * Modules are potential dependency resolvers, however before they can be deployed Dagger 2 needs to know
 * which objects are inject-able targets and it need to get a list of Modules that are providing dependency solutions.
 * We provide these two information in the AppComponent.java class like so: Notice that it has the list of
 * our two modules and the classes that we may need a dependency to be injected.
 *
 */

@Singleton
@Component(
        modules = {AppModule.class, ShoppingCartModule.class}
)
public interface AppComponent {
    void inject(ProductListener presenter);
    void inject(DaggerActivity activity);
}
