package fr.enlight.henripotierbookshop.presentation.dependencies.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fr.enlight.henripotierbookshop.presentation.views.activities.NavigationManager;

/**
 * A Dagger Module providing application lifetime dependencies
 */
@Module
public class ApplicationModule {

    @Provides @Singleton
    NavigationManager providesNavigationManager(){
        return new NavigationManager();
    }
}
