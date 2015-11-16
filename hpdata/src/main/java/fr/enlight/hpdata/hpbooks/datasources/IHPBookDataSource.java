package fr.enlight.hpdata.hpbooks.datasources;

import java.util.List;

import fr.enlight.hpdata.definition.IDataSource;
import fr.enlight.hpdata.hpbooks.entities.HPBook;
import fr.enlight.hpdata.hpbooks.entities.HPBookCommercialOffers;
import rx.Observable;


/**
 * Created by enlight on 15/11/2015.
 */
public interface IHPBookDataSource extends IDataSource{

    Observable<List<HPBook>> getBookCatalog();

    Observable<HPBookCommercialOffers> getCommercialOffers(List<String> commercialOffers);
}
