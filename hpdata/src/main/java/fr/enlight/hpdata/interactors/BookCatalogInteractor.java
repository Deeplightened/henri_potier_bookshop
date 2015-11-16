package fr.enlight.hpdata.interactors;

import java.util.List;

import fr.enlight.hpdata.hpbooks.BookstoreModel;
import fr.enlight.hpdata.hpbooks.entities.HPBook;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by yhuriez on 16/11/2015.
 */
public class BookCatalogInteractor extends Interactor<List<HPBook>> {

    private BookstoreModel bookstoreModel;

    public BookCatalogInteractor(BookstoreModel model) {
        super(Schedulers.io());
        bookstoreModel = model;
    }

    @Override
    public Observable<List<HPBook>> buildInteractorObservable() {
        return bookstoreModel.getBookCatalog();
    }
}
