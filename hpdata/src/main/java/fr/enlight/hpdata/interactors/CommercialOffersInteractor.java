package fr.enlight.hpdata.interactors;

import java.util.List;

import fr.enlight.hpdata.hpbooks.BookstoreModel;
import fr.enlight.hpdata.hpbooks.entities.HPBookCommercialOffers;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * An Interactor used to ask for a list of commercial offers.
 */
public class CommercialOffersInteractor extends Interactor<HPBookCommercialOffers> {

    private List<String> isbnParameters;
    private BookstoreModel bookstoreModel;

    public CommercialOffersInteractor(BookstoreModel model) {
        super(Schedulers.io());
        bookstoreModel = model;
    }

    public void setIsbnParameters(List<String> isbnParameters) {
        this.isbnParameters = isbnParameters;
    }

    @Override
    public Observable<HPBookCommercialOffers> buildInteractorObservable() {
        return bookstoreModel.getCommercialOffers(isbnParameters);
    }
}
