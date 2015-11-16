package fr.enlight.hpdata.hpbooks;

import android.content.Context;

import java.util.List;

import fr.enlight.hpdata.hpbooks.datasources.HPBookDataSourceFactory;
import fr.enlight.hpdata.hpbooks.datasources.HPBooksCache;
import fr.enlight.hpdata.hpbooks.datasources.IHPBookDataSource;
import fr.enlight.hpdata.hpbooks.entities.HPBook;
import fr.enlight.hpdata.hpbooks.entities.HPBookCommercialOffers;
import rx.Observable;

/**
 * Describe the model configuration for all book services.
 */
public class BookstoreModel {

    public static final long CACHE_DURATION = 1000 * 60 * 60 * 24;

    private HPBookDataSourceFactory hpDataSourceFactory;

    public BookstoreModel(Context context) {
        hpDataSourceFactory = new HPBookDataSourceFactory(context, new HPBooksCache(context, CACHE_DURATION));
    }

    /**
     * Create the Observable used for downloading the books catalog.
     *
     * @return the concerned Observable
     */
    public Observable<List<HPBook>> getBookCatalog(){
        IHPBookDataSource dataSource = hpDataSourceFactory.createDataSource();
        return dataSource.getBookCatalog();
    }

    public Observable<HPBookCommercialOffers> getCommercialOffers(List<String> isbnBooks){
        IHPBookDataSource dataSource = hpDataSourceFactory.createCloudBookDataSource();
        return dataSource.getCommercialOffers(isbnBooks);
    }
}
