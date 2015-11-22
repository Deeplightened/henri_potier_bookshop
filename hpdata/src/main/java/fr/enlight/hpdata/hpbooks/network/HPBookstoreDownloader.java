package fr.enlight.hpdata.hpbooks.network;

import java.util.List;

import fr.enlight.hpdata.exceptions.IncorrectParametersException;
import fr.enlight.hpdata.exceptions.NetworkConnectivityException;
import fr.enlight.hpdata.hpbooks.entities.HPBook;
import fr.enlight.hpdata.hpbooks.entities.HPBookCommercialOffers;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * This class purpose is to provides an Observable object to subscribe for, which do download the wanted data form the bookstore REST API described in the HPBookstoreService.
 */
public class HPBookstoreDownloader {

    private final IHPBookstoreService hpBookstoreService;

    public HPBookstoreDownloader() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IHPBookstoreService.BOOKSHOP_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        hpBookstoreService = retrofit.create(IHPBookstoreService.class);
    }

    /**
     * Download the book catalog using the HPBookstoreService.
     *
     * @return the list of books in the downloaded catalog
     */
    public List<HPBook> downloadBookCatalog() throws Exception {
        List<HPBook> result = null;

        Call<List<HPBook>> bookCatalogCall = hpBookstoreService.getBookCatalog();
        if (bookCatalogCall != null) {
            Response<List<HPBook>> bookCatalogResponse = bookCatalogCall.execute();

            if (bookCatalogResponse.isSuccess()) {
                result = bookCatalogResponse.body();
            } else {
                throw new NetworkConnectivityException(bookCatalogResponse.message());
            }
        }
        return result;
    }

    /**
     * Download a commercial offer for a list of books using the HPBookstoreService.
     *
     * @param isbnBookList the concerned book's ISBN list
     * @return the commercial offers for the list of books
     */
    public HPBookCommercialOffers downloadCommercialOffer(final List<String> isbnBookList) throws Exception{
        HPBookCommercialOffers result = null;

        if (isbnBookList == null || isbnBookList.isEmpty()) {
            throw new IncorrectParametersException("No ISBN parameter for commercial offers downloading");
        }

        String isbnParameter = "";
        boolean first = true;
        for (String isbn : isbnBookList) {
            if (first) {
                isbnParameter = isbn;
                first = false;
            } else {
                isbnParameter += IHPBookstoreService.ISBN_SEPARATOR + isbn;
            }
        }

        Call<HPBookCommercialOffers> commercialOffersCall = hpBookstoreService.getCommercialOffers(isbnParameter);
        if (commercialOffersCall != null) {
            Response<HPBookCommercialOffers> commercialOffersResponse = commercialOffersCall.execute();

            if (commercialOffersResponse.isSuccess()) {
                result = commercialOffersResponse.body();
            } else {
                throw new NetworkConnectivityException(commercialOffersResponse.message());
            }
        }

        return result;
    }
}
