package fr.enlight.hpdata.datasources;

import java.util.List;

import fr.enlight.hpdata.entities.HPBook;
import fr.enlight.hpdata.entities.HPBookCommercialOffers;
import rx.Observable;

/**
 * Created by enlight on 15/11/2015.
 */
public interface IBookDataSource {

    Observable<List<HPBook>> getBookCatalog();

    Observable<HPBookCommercialOffers> getCommercialOffers(List<String> commercialOffers);
}
