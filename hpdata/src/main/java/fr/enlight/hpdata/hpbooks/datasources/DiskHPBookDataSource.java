package fr.enlight.hpdata.hpbooks.datasources;

import java.util.List;

import fr.enlight.hpdata.exceptions.InternalException;
import fr.enlight.hpdata.hpbooks.entities.HPBook;
import fr.enlight.hpdata.hpbooks.entities.HPBookCommercialOffers;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by enlight on 15/11/2015.
 */
public class DiskHPBookDataSource implements IHPBookDataSource {

    private final HPBooksCache booksCache;

    public DiskHPBookDataSource(HPBooksCache booksCache) {
        this.booksCache = booksCache;
    }

    @Override
    public Observable<List<HPBook>> getBookCatalog() {
        return Observable.create(new Observable.OnSubscribe<List<HPBook>>() {
            @Override
            public void call(Subscriber<? super List<HPBook>> subscriber) {
                List<HPBook> hpBooks = booksCache.loadDataFromCache();
                if(hpBooks == null){
                    subscriber.onError(new InternalException("Book catalog should be stored in cache here"));
                } else {
                    subscriber.onNext(hpBooks);
                }
            }
        });
    }

    @Override
    public Observable<HPBookCommercialOffers> getCommercialOffers(List<String> commercialOffers) {
        throw new UnsupportedOperationException("Method getCommercialOffers is not available for disk storage");
    }
}
