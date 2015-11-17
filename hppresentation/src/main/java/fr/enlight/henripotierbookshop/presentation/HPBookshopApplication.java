package fr.enlight.henripotierbookshop.presentation;

import android.app.Application;

import fr.enlight.henripotierbookshop.presentation.dependencies.components.ApplicationComponent;
import fr.enlight.henripotierbookshop.presentation.dependencies.components.DaggerApplicationComponent;
import fr.enlight.henripotierbookshop.presentation.dependencies.modules.ApplicationModule;

/**
 * The Application class. Extended to allows dagger injection.
 */
public class HPBookshopApplication extends Application {

    private static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initInjection();
    }

    private void initInjection() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
