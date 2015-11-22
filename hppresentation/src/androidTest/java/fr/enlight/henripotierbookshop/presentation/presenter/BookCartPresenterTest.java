package fr.enlight.henripotierbookshop.presentation.presenter;

import android.content.Context;
import android.test.AndroidTestCase;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import fr.enlight.henripotierbookshop.presentation.model.Book;
import fr.enlight.henripotierbookshop.presentation.model.BookCartModel;
import fr.enlight.henripotierbookshop.presentation.model.BookOffer;
import fr.enlight.hpdata.hpbooks.BookstoreModel;
import fr.enlight.hpdata.interactors.CommercialOffersInteractor;

/**
 * Test class that checks the BookCartPresenter behavior.
 */
public class BookCartPresenterTest extends AndroidTestCase {

    private final String BOOK_ISBN_1 = "c8fabf68-8374-48fe-a7ea-a00ccd07afff";
    private final String BOOK_ISBN_2 = "a460afed-e5e7-4e39-a39d-c885c05db861";

    BookCartPresenter presenter;
    BookCartModel cartModel;

    final Book testBook1 = Book.newInstance("Titre", "url image", (short) 20, BOOK_ISBN_1);
    final Book testBook2 = Book.newInstance("Titre 2", "url2 image", (short) 25, BOOK_ISBN_2);

    final CountDownLatch signalData = new CountDownLatch(2);

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        CommercialOffersInteractor interactor = new CommercialOffersInteractor(new BookstoreModel(getContext()));
        cartModel = new BookCartModel();

        // Init cart model
        cartModel.addBook(testBook1);
        cartModel.addBook(testBook2);

        presenter = new BookCartPresenter(interactor, cartModel);
    }

    /**
     * Start the data waiter.
     */
    private void awaitSignalData() {
        try {
            signalData.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            fail("Data is never received : " + e.getMessage());
        }
    }

    /**
     * Test if the presenter has the right number of books in the cart.
     */
    public void testPresenterBookCartCount(){
        assertEquals("Incorrect number of books in cart", 2, presenter.getCartBookCount());
    }

    /**
     * Test if the presenter can correctly clear the cart.
     */
    public void testClearCart(){
        presenter.clearCart();

        assertEquals("Cart should be empty", 0, presenter.getCartBookCount());
    }

    /**
     * Test that, on method 'create' call, the presentable view receive the list of books
     * in cart and the total.
     */
    public void testCreate(){
        presenter.setPresentableView(new PresentableViewTest());

        presenter.create();

        awaitSignalData();
    }

    /**
     * Test the 'create' method with a wrong book in the cart, should triggers an error
     */
    public void testCreateWithWrongCart(){

        cartModel.addBook(Book.newInstance("title", "url", (short) 1, "N'importe quel ISBN"));

        presenter.setPresentableView(new PresentableViewTest() {
            @Override
            public void updateCartContent(List<Book> cartBooks, List<BookOffer> bookOffers) {
                fail("Result method should not be called in that case");
            }

            @Override
            public void updateCartTotalPrice(double total, double tva) {
                fail("Result method should not be called in that case");
            }

            @Override
            public void onLoadingFailed(String errorMessage) {
                assertNotNull("Error message should not be empty", errorMessage);
                signalData.countDown();
                signalData.countDown();
            }
        });

        presenter.create();

        awaitSignalData();
    }

    /**
     * Test if the 'destroy' method correctly detach this presenter from any background task
     */
    public void testDestroy(){
        presenter.setPresentableView(new PresentableViewTest(){
            @Override
            public void updateCartContent(List<Book> cartBooks, List<BookOffer> bookOffers) {
                fail("Result method should not be called in that case");
            }

            @Override
            public void updateCartTotalPrice(double total, double tva) {
                fail("Result method should not be called in that case");
            }
        });

        presenter.create();
        presenter.destroy();

        try {
            signalData.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // If timeout triggered, that mean the presenter has been correctly detached
            assertTrue(true);
        }
    }

    /**
     * Custom PresentableView
     */
    private class PresentableViewTest implements BookCartPresenter.BookCartPresentableView{

        final CountDownLatch signalView = new CountDownLatch(1);

        @Override
        public void updateCartContent(List<Book> cartBooks, List<BookOffer> bookOffers) {
            assertNotNull("Received cart book list is null", cartBooks);
            assertFalse("Received cart book list is empty", cartBooks.isEmpty());

            assertNotNull("Received book offers is null", bookOffers);
            assertFalse("Received book offers is empty", bookOffers.isEmpty());

            signalData.countDown();
        }

        @Override
        public void updateCartTotalPrice(double total, double tva) {
            assertTrue("Received total is not positive", total > 0);
            assertTrue("Received tva is not positive", tva > 0);

            signalData.countDown();
        }

        @Override
        public Context getContext() {
            return BookCartPresenterTest.this.getContext();
        }

        @Override
        public void showLoadingView() {
            try {
                signalView.await(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                fail("Loading view is never hidden : " + e.getMessage());
            }
        }

        @Override
        public void hideLoadingView() {
            signalView.countDown();
        }

        @Override
        public void onLoadingFailed(String errorMessage) {
            fail("Error received");
        }


    }
}
