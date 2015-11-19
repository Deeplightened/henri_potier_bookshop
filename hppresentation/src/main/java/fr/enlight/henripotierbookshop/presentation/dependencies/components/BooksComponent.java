package fr.enlight.henripotierbookshop.presentation.dependencies.components;

import dagger.Subcomponent;
import fr.enlight.henripotierbookshop.presentation.dependencies.modules.BookCartModule;
import fr.enlight.henripotierbookshop.presentation.dependencies.modules.BookCatalogModule;
import fr.enlight.henripotierbookshop.presentation.dependencies.modules.BooksModule;
import fr.enlight.henripotierbookshop.presentation.dependencies.scope.BookScope;

/**
 * A Dagger component that injects dependencies linked to the bookstore.
 */
@BookScope
@Subcomponent(modules = BooksModule.class)
public interface BooksComponent {

    BookCatalogComponent addModule(BookCatalogModule bookCatalogModule);

    BookCartComponent addModule(BookCartModule bookCartModule);
}
