package fr.enlight.henripotierbookshop.presentation.dependencies.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import fr.enlight.henripotierbookshop.presentation.views.activities.NavigationManager;

/**
 * A Dagger Module providing application lifetime dependencies
 */
@Module
public class ApplicationModule {

    private final Context applicationContext;

    public ApplicationModule(Context applicationContext){
        this.applicationContext = applicationContext;
    }

    @Provides
    Context provideApplicationContext(){
        return applicationContext;
    }

    @Provides
    NavigationManager provideNavigationManager(){
        return new NavigationManager();
    }
}
