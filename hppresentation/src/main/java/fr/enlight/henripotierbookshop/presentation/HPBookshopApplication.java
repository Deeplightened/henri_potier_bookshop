package fr.enlight.henripotierbookshop.presentation;

import android.app.Application;

import fr.enlight.henripotierbookshop.presentation.dependencies.components.ApplicationComponent;
import fr.enlight.henripotierbookshop.presentation.dependencies.components.BooksComponent;
import fr.enlight.henripotierbookshop.presentation.dependencies.components.DaggerApplicationComponent;
import fr.enlight.henripotierbookshop.presentation.dependencies.modules.ApplicationModule;
import fr.enlight.henripotierbookshop.presentation.dependencies.modules.BooksModule;

/**
 * The Application class. Extended to allows dagger injection.
 */
public class HPBookshopApplication extends Application {

    private ApplicationComponent applicationComponent;
    private BooksComponent booksComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initInjection();
    }

    private void initInjection() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public BooksComponent getBooksComponent(){
        if (booksComponent == null) {
            booksComponent = applicationComponent.addModule(new BooksModule());
        }
        return booksComponent;
    }
}
