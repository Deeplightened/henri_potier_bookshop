package fr.enlight.henripotierbookshop.presentation.dependencies.modules;

import java.util.List;

import dagger.Module;
import dagger.Provides;
import fr.enlight.henripotierbookshop.presentation.model.BookCartModel;
import fr.enlight.henripotierbookshop.presentation.presenter.BookCatalogPresenter;
import fr.enlight.hpdata.hpbooks.BookstoreModel;
import fr.enlight.hpdata.interactors.BookCatalogInteractor;
import fr.enlight.hpdata.interactors.CommercialOffersInteractor;

/**
 * A Dagger module that provides the Interactor used to interact with the bookstore model.
 */
@Module(includes = ApplicationModule.class)
public class BooksModule {

    private List<String> isbnBooks;

    public BooksModule(){
        // Default constructor
    }

    public BooksModule(List<String> isbnBooks){
        this.isbnBooks = isbnBooks;
    }

    @Provides
    BookCatalogInteractor provideBookCatalog(BookstoreModel bookstoreModel){
        return new BookCatalogInteractor(bookstoreModel);
    }

    @Provides
    CommercialOffersInteractor provideCommercialOffers(BookstoreModel bookstoreModel){
        return new CommercialOffersInteractor(bookstoreModel, isbnBooks);
    }

    @Provides
    BookCatalogPresenter provideBookCatalogPresenter(BookCatalogInteractor interactor, BookCartModel cartModel){
        return new BookCatalogPresenter(interactor, cartModel);
    }
}
