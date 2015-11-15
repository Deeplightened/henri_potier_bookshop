package fr.enlight.hpdata.network;

import java.util.List;

import fr.enlight.hpdata.entities.HPBookCommercialOffers;
import fr.enlight.hpdata.entities.HPBook;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * An interface describing
 */
public interface HPBookstoreService {

    String BOOKSHOP_ENDPOINT = "http://henri-potier.xebia.fr";

    @GET("/books")
    Call<List<HPBook>> getBookCatalog ();

    @GET("/books/{isbn}/commercialOffers")
    Call<HPBookCommercialOffers> getCommercialOffers (@Path("isbn") String isbn);
}
