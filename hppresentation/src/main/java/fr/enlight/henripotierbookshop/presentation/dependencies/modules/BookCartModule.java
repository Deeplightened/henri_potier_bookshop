package fr.enlight.henripotierbookshop.presentation.dependencies.modules;

import dagger.Module;
import dagger.Provides;
import fr.enlight.henripotierbookshop.presentation.dependencies.scope.ActivityScope;
import fr.enlight.henripotierbookshop.presentation.model.BookCartModel;
import fr.enlight.henripotierbookshop.presentation.presenter.BookCartPresenter;
import fr.enlight.hpdata.hpbooks.BookstoreModel;
import fr.enlight.hpdata.interactors.CommercialOffersInteractor;

/**
 * Created by enlight on 19/11/2015.
 */
@Module
public class BookCartModule {

    @Provides
    @ActivityScope
    CommercialOffersInteractor provideCommercialOffers(BookstoreModel bookstoreModel) {
        return new CommercialOffersInteractor(bookstoreModel);
    }

    @Provides
    @ActivityScope
    BookCartPresenter provideBookCartPresenter(CommercialOffersInteractor interactor, BookCartModel cartModel) {
        return new BookCartPresenter(interactor, cartModel);
    }
}
