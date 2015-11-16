package fr.enlight.hpdata.interactors;

import java.util.List;

import fr.enlight.hpdata.hpbooks.BookstoreModel;
import fr.enlight.hpdata.hpbooks.entities.HPBookCommercialOffers;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * An Interactor used to
 */
public class CommercialOffersInteractor extends Interactor<HPBookCommercialOffers> {

    private final List<String> isbnParameters;
    private BookstoreModel bookstoreModel;

    public CommercialOffersInteractor(BookstoreModel model, List<String> isbnParameters) {
        super(Schedulers.io());
        bookstoreModel = model;
        this.isbnParameters = isbnParameters;
    }

    @Override
    public Observable<HPBookCommercialOffers> buildInteractorObservable() {
        return bookstoreModel.getCommercialOffers(isbnParameters);
    }
}
