package fr.enlight.henripotierbookshop.network;

import java.util.List;

import fr.enlight.henripotierbookshop.entities.HPBookCommercialOffers;
import fr.enlight.henripotierbookshop.entities.HPBook;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * An interface describing
 */
public interface HPBookstoreService {

    public static final String BOOKSHOP_ENDPOINT = "http://henri-potier.xebia.fr";

    @GET("/books")
    Call<List<HPBook>> getBookCatalog ();

    @GET("/books/{isbn}/commercialOffers")
    Call<HPBookCommercialOffers> getCommercialOffers (@Path("isbn") String isbn);
}
