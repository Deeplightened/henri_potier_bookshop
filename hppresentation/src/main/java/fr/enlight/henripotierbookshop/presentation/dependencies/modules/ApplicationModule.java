package fr.enlight.henripotierbookshop.presentation.dependencies.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fr.enlight.henripotierbookshop.presentation.views.base.NavigationManager;

/**
 * A Dagger Module providing application lifetime dependencies
 */
@Module
public class ApplicationModule {

    private final Context applicationContext;

    public ApplicationModule(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return applicationContext;
    }

    @Provides
    @Singleton
    NavigationManager provideNavigationManager() {
        return new NavigationManager();
    }
}
