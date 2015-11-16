package fr.enlight.hpdata.hpbooks.network;

import java.util.List;

import fr.enlight.hpdata.hpbooks.entities.HPBookCommercialOffers;
import fr.enlight.hpdata.hpbooks.entities.HPBook;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * An interface describing
 */
public interface IHPBookstoreService {

    String BOOKSHOP_ENDPOINT = "http://henri-potier.xebia.fr";
    String ISBN_SEPARATOR = ",";

    @GET("/books")
    Call<List<HPBook>> getBookCatalog ();

    @GET("/books/{isbn}/commercialOffers")
    Call<HPBookCommercialOffers> getCommercialOffers (@Path("isbn") String isbn);
}
