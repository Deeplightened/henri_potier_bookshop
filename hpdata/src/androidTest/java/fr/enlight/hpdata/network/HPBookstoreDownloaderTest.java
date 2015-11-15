package fr.enlight.hpdata.network;

import com.squareup.okhttp.ResponseBody;

import junit.framework.TestCase;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import fr.enlight.hpdata.entities.HPBook;
import fr.enlight.hpdata.entities.HPBookCommercialOffers;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by enlight on 15/11/2015.
 */
public class HPBookstoreDownloaderTest extends TestCase {

    private HPBookstoreDownloader bookstoreDownloader;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        bookstoreDownloader = new HPBookstoreDownloader();
    }

    /**
     * Test of the webservice sending the list of books to display.
     */
    public void testDownloadBookCatalog() throws Exception {
        try {
            List<HPBook> hpBookList = bookstoreDownloader.downloadBookCatalog();
            assertNotNull("No book entity generated from webservice call", hpBookList);
            assertFalse("Should have at least one element", hpBookList.isEmpty());

        } catch (IOException e) {
            fail("Cannot read stream from catalog webservice  : " + e.getMessage());
        }
    }

    /**
     * Test of the webservice calculating the commercial offers from a list of books.
     */
    public void testDownloadCommercialOffer() throws Exception {
        final String BOOK_ISBN_1 = "c8fabf68-8374-48fe-a7ea-a00ccd07afff";
        final String BOOK_ISBN_2 = "a460afed-e5e7-4e39-a39d-c885c05db861";

        List<String> singleISBNList = Collections.singletonList(BOOK_ISBN_1);
        List<String> multipleISBNList = Arrays.asList(BOOK_ISBN_1, BOOK_ISBN_2);

        try {
            HPBookCommercialOffers commercialOffers = bookstoreDownloader.downloadCommercialOffer(singleISBNList);
            assertNotNull("No commercial offers generated from webservice call", commercialOffers);

            commercialOffers = bookstoreDownloader.downloadCommercialOffer(multipleISBNList);
            assertNotNull("No commercial offers generated from webservice call", commercialOffers);

        } catch (IOException e) {
            fail("Cannot read stream from catalog webservice  : " + e.getMessage());
        }
    }
}