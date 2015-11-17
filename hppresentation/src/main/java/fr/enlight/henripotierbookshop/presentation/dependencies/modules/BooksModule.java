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

    private BookstoreModel bookstoreModel;
    private List<String> isbnBooks;

    public BooksModule(){
        // Default constructor
    }

    public BooksModule(BookstoreModel bookstoreModel, List<String> isbnBooks){
        this.bookstoreModel = bookstoreModel;
        this.isbnBooks = isbnBooks;
    }

    @Provides @ActivityScope @Named("bookCatalog")
    Interactor<List<HPBook>> provideBookCatalog( ){
        return new BookCatalogInteractor(bookstoreModel);
    }

    @Provides @ActivityScope @Named("commercialOffer")
    Interactor<HPBookCommercialOffers> provideCommercialOffers(){
        return new CommercialOffersInteractor(bookstoreModel, isbnBooks);
    }
}
