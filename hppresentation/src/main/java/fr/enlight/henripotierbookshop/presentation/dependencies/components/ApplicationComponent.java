package fr.enlight.henripotierbookshop.presentation.dependencies.components;

import javax.inject.Singleton;

import dagger.Component;
import fr.enlight.henripotierbookshop.presentation.dependencies.modules.ApplicationModule;
import fr.enlight.henripotierbookshop.presentation.dependencies.modules.BooksModule;

/**
 * A Dagger Component providing injection of module ApplicationModule in every Activity of the application.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    BooksComponent addModule(BooksModule booksModule);
}
