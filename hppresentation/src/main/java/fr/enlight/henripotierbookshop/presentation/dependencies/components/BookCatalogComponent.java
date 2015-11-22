package fr.enlight.henripotierbookshop.presentation.dependencies.components;

import dagger.Subcomponent;
import fr.enlight.henripotierbookshop.presentation.dependencies.modules.BookCatalogModule;
import fr.enlight.henripotierbookshop.presentation.dependencies.scope.ActivityScope;
import fr.enlight.henripotierbookshop.presentation.views.catalog.BookCatalogActivity;

/**
 * Created by enlight on 19/11/2015.
 */
@ActivityScope
@Subcomponent(modules = BookCatalogModule.class)
public interface BookCatalogComponent {

    void inject(BookCatalogActivity activity);
}
