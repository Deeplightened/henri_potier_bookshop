package fr.enlight.henripotierbookshop.presentation.dependencies.components;

import javax.inject.Singleton;

import dagger.Component;
import fr.enlight.henripotierbookshop.presentation.dependencies.modules.ApplicationModule;
import fr.enlight.henripotierbookshop.presentation.views.activities.AbstractHPActivity;

/**
 * A Dagger Component providing injection of module ApplicationModule in every Activity of the application.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(AbstractHPActivity application);
}
