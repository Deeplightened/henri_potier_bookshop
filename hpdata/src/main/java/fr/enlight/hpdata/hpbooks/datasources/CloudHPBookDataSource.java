package fr.enlight.hpdata.hpbooks.datasources;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

import fr.enlight.hpdata.exceptions.NetworkConnectivityException;
import fr.enlight.hpdata.hpbooks.entities.HPBook;
import fr.enlight.hpdata.hpbooks.entities.HPBookCommercialOffers;
import fr.enlight.hpdata.hpbooks.network.HPBookstoreDownloader;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by enlight on 15/11/2015.
 */
public class CloudHPBookDataSource implements IHPBookDataSource {

    private final Context context;
    private final HPBooksCache booksCache;
    private final HPBookstoreDownloader hpBookstoreDownloader;

    private final Action1<List<HPBook>> saveCatalogCacheAction;

    public CloudHPBookDataSource(Context context, HPBooksCache booksCache) {
        this.context = context;
        this.booksCache = booksCache;
        hpBookstoreDownloader = new HPBookstoreDownloader();

        saveCatalogCacheAction = new Action1<List<HPBook>>() {
            @Override
            public void call(List<HPBook> hpBooks) {
                CloudHPBookDataSource.this.booksCache.saveDataInCache(hpBooks);
            }
        };
    }

    @Override
    public Observable<List<HPBook>> getBookCatalog() {
        Observable<List<HPBook>> observableResult = Observable.create(new Observable.OnSubscribe<List<HPBook>>() {
            @Override
            public void call(Subscriber<? super List<HPBook>> subscriber) {
                if (!isInternetConnected()) {
                    subscriber.onError(new NetworkConnectivityException());
                    return;
                }

                try {
                    List<HPBook> hpBooks = hpBookstoreDownloader.downloadBookCatalog();
                    if (hpBooks != null) {
                        subscriber.onNext(hpBooks);
                    } else {
                        subscriber.onError(new NetworkConnectivityException());
                    }
                } catch (Exception exp) {
                    subscriber.onError(new NetworkConnectivityException(exp.getMessage(), exp.getCause()));
                }
            }
        });

        observableResult = observableResult.doOnNext(saveCatalogCacheAction);

        return observableResult;
    }

    @Override
    public Observable<HPBookCommercialOffers> getCommercialOffers(final List<String> isbnBookList) {
        return Observable.create(new Observable.OnSubscribe<HPBookCommercialOffers>() {
            @Override
            public void call(Subscriber<? super HPBookCommercialOffers> subscriber) {
                if (!isInternetConnected()) {
                    subscriber.onError(new NetworkConnectivityException());
                    return;
                }

                try {
                    HPBookCommercialOffers hpBookCommercialOffers = hpBookstoreDownloader.downloadCommercialOffer(isbnBookList);
                    if (hpBookCommercialOffers != null) {
                        subscriber.onNext(hpBookCommercialOffers);
                    } else {
                        subscriber.onError(new NetworkConnectivityException());
                    }
                } catch (Exception exp) {
                    subscriber.onError(new NetworkConnectivityException(exp.getMessage(), exp.getCause()));
                }
            }
        });
    }

    /**
     * @return true if the device's network is connected, false otherwise.
     */
    private boolean isInternetConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }
}
