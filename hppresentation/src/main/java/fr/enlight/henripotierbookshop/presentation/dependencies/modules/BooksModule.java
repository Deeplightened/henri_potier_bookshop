package fr.enlight.henripotierbookshop.presentation.dependencies.modules;

import java.util.List;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import fr.enlight.henripotierbookshop.presentation.dependencies.scope.ActivityScope;
import fr.enlight.hpdata.hpbooks.BookstoreModel;
import fr.enlight.hpdata.hpbooks.entities.HPBook;
import fr.enlight.hpdata.hpbooks.entities.HPBookCommercialOffers;
import fr.enlight.hpdata.interactors.BookCatalogInteractor;
import fr.enlight.hpdata.interactors.CommercialOffersInteractor;
import fr.enlight.hpdata.interactors.Interactor;

/**
 * A Dagger module that provides the Interactor used to interact with the bookstore model.
 */
@Module
public class BooksModule {

    public static final String BOOK_CATALOG_INTERACTOR = "bookCatalog";
    public static final String COMMERCIAL_OFFER_INTERACTOR = "commercialOffer";

    private BookstoreModel bookstoreModel;
    private List<String> isbnBooks;

    public BooksModule(){
        // Default constructor
    }

    public BooksModule(BookstoreModel bookstoreModel, List<String> isbnBooks){
        this.bookstoreModel = bookstoreModel;
        this.isbnBooks = isbnBooks;
    }

    @Provides @ActivityScope @Named(BOOK_CATALOG_INTERACTOR)
    Interactor<List<HPBook>> provideBookCatalog( ){
        return new BookCatalogInteractor(bookstoreModel);
    }

    @Provides @ActivityScope @Named(COMMERCIAL_OFFER_INTERACTOR)
    Interactor<HPBookCommercialOffers> provideCommercialOffers(){
        return new CommercialOffersInteractor(bookstoreModel, isbnBooks);
    }
}
