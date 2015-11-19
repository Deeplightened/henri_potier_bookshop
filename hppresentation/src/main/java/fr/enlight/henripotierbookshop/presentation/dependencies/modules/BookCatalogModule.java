package fr.enlight.henripotierbookshop.presentation.dependencies.modules;

import dagger.Module;
import dagger.Provides;
import fr.enlight.henripotierbookshop.presentation.dependencies.scope.ActivityScope;
import fr.enlight.henripotierbookshop.presentation.model.BookCartModel;
import fr.enlight.henripotierbookshop.presentation.presenter.BookCatalogPresenter;
import fr.enlight.hpdata.hpbooks.BookstoreModel;
import fr.enlight.hpdata.interactors.BookCatalogInteractor;

/**
 * Created by enlight on 19/11/2015.
 */
@Module
public class BookCatalogModule {

    @Provides
    @ActivityScope
    BookCatalogInteractor provideBookCatalog(BookstoreModel bookstoreModel) {
        return new BookCatalogInteractor(bookstoreModel);
    }

    @Provides
    @ActivityScope
    BookCatalogPresenter provideBookCatalogPresenter(BookCatalogInteractor interactor, BookCartModel cartModel) {
        return new BookCatalogPresenter(interactor, cartModel);
    }
}
