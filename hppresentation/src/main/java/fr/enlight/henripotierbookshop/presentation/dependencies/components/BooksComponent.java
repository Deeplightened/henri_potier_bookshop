package fr.enlight.henripotierbookshop.presentation.dependencies.components;

import dagger.Component;
import fr.enlight.henripotierbookshop.presentation.dependencies.modules.BooksModule;
import fr.enlight.henripotierbookshop.presentation.views.activities.BookCartActivity;
import fr.enlight.henripotierbookshop.presentation.views.activities.BookCatalogActivity;

/**
 * A Dagger component that injects dependencies linked to the bookstore.
 */
@Component(modules = BooksModule.class)
public interface BooksComponent {
    void inject(BookCatalogActivity activity);
    void inject(BookCartActivity activity);
}
