package fr.enlight.henripotierbookshop.network;

import java.util.List;

import fr.enlight.henripotierbookshop.entities.BookEntity;
import retrofit.http.GET;

/**
 *
 */
public interface BookshopService {

    public static final String ENDPOINT = "http://henri-potier.xebia.fr/books";

    @GET("/{isbn}/commercialOffers")
    List<BookEntity> bookCatalog
}
