package fr.enlight.henripotierbookshop.network;

import com.squareup.okhttp.ResponseBody;

import junit.framework.TestCase;

import java.io.IOException;
import java.util.List;

import fr.enlight.henripotierbookshop.entities.HPBookCommercialOffers;
import fr.enlight.henripotierbookshop.entities.HPBook;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * This test class aims at testing the webservices provided and if they are still conforms to
 * the first given contract interface.<br>
 * Using this class we can eliminate the case of a webservice server modification when a bug occur in the application.<br>
 * Theses test are voluntary simple to avoid another source of error.
 */
public class HPBookstoreServiceTest extends TestCase {

    private HPBookstoreService hpBookstoreService;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HPBookstoreService.BOOKSHOP_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        hpBookstoreService = retrofit.create(HPBookstoreService.class);
    }

    /**
     * Test of the webservice sending the list of books to display.
     */
    public void testGetBookstoreCatalog(){
        Call<List<HPBook>> bookCatalog = hpBookstoreService.getBookCatalog();
        try {
            Response<List<HPBook>> response = bookCatalog.execute();

            ResponseBody errorBody = response.errorBody();
            String errorMessage = "Error on webservice call" + ((errorBody != null) ? " : " + errorBody.string() : "");
            assertTrue(errorMessage, response.isSuccess());

            List<HPBook> hpBookList = response.body();
            assertNotNull("No book entity generated from webservice call", hpBookList);
            assertFalse("Should have at least one element", hpBookList.isEmpty());

        } catch (IOException e) {
            fail("Cannot read stream from catalog webservice  : " + e.getMessage());
        }
    }

    /**
     * Test of the webservice calculating the commercial offers from a list of books.
     */
    public void testGetCommercialOffers(){
        final String BOOK_ISBN_1 = "c8fabf68-8374-48fe-a7ea-a00ccd07afff";
        final String BOOK_ISBN_2 = "a460afed-e5e7-4e39-a39d-c885c05db861";

        Call<HPBookCommercialOffers> singleBookOffer = hpBookstoreService.getCommercialOffers(BOOK_ISBN_1);
        Call<HPBookCommercialOffers> multipleBookOffer = hpBookstoreService.getCommercialOffers(BOOK_ISBN_1 + "," + BOOK_ISBN_2);

        try {
            Response<HPBookCommercialOffers> responseSingle = singleBookOffer.execute();
            Response<HPBookCommercialOffers> responseMultiple = multipleBookOffer.execute();

            ResponseBody errorBody = responseSingle.errorBody();
            String errorMessage = "Error on webservice call" + ((errorBody != null) ? " : " + errorBody.string() : "");
            assertTrue(errorMessage, responseSingle.isSuccess());

            errorBody = responseMultiple.errorBody();
            errorMessage = "Error on webservice call" + ((errorBody != null) ? " : " + errorBody.string() : "");
            assertTrue(errorMessage, responseMultiple.isSuccess());

            HPBookCommercialOffers commercialOffers = responseSingle.body();
            assertNotNull("No commercial offers generated from webservice call", commercialOffers);

            commercialOffers = responseMultiple.body();
            assertNotNull("No commercial offers generated from webservice call", commercialOffers);

        } catch (IOException e) {
            fail("Cannot read stream from catalog webservice  : " + e.getMessage());
        }
    }
}