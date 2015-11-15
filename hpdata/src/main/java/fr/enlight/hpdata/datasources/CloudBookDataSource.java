package fr.enlight.hpdata.datasources;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

import fr.enlight.hpdata.entities.HPBook;
import fr.enlight.hpdata.entities.HPBookCommercialOffers;
import fr.enlight.hpdata.exceptions.InternalException;
import fr.enlight.hpdata.exceptions.NetworkConnectivityException;
import fr.enlight.hpdata.network.HPBookstoreDownloader;
import fr.enlight.hpdata.network.IHPBookstoreService;
import retrofit.Call;
import retrofit.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by enlight on 15/11/2015.
 */
public class CloudBookDataSource implements IBookDataSource {

    private final Context context;
    private final HPBookstoreDownloader hpBookstoreDownloader;

    public CloudBookDataSource(Context context) {
        this.context = context;
        hpBookstoreDownloader = new HPBookstoreDownloader();
    }

    @Override
    public Observable<List<HPBook>> getBookCatalog() {
        return Observable.create(new Observable.OnSubscribe<List<HPBook>>() {
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
