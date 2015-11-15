package fr.enlight.hpdata.datasources;

import android.content.Context;

import java.util.List;

import fr.enlight.hpdata.entities.HPBook;
import fr.enlight.hpdata.entities.HPBookCommercialOffers;
import rx.Observable;

/**
 * Created by enlight on 15/11/2015.
 */
public class DiskBookDataSource implements IBookDataSource {

    public DiskBookDataSource(Context context) {

    }

    @Override
    public Observable<List<HPBook>> getBookCatalog() {
        return null;
    }

    @Override
    public Observable<HPBookCommercialOffers> getCommercialOffers(List<String> commercialOffers) {
        return null;
    }
}
