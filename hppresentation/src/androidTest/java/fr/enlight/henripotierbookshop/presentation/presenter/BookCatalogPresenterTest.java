package fr.enlight.henripotierbookshop.presentation.presenter;

import android.content.Context;
import android.test.AndroidTestCase;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import fr.enlight.henripotierbookshop.presentation.model.Book;
import fr.enlight.henripotierbookshop.presentation.model.BookCartModel;
import fr.enlight.hpdata.hpbooks.BookstoreModel;
import fr.enlight.hpdata.interactors.BookCatalogInteractor;

/**
 * Test class that checks the BookCatalogPresenter behavior.
 */
public class BookCatalogPresenterTest extends AndroidTestCase {

    BookCatalogPresenter presenter;
    BookCartModel cartModel;

    final Book testBook = Book.newInstance("Titre", "url image", (short) 1, "ISBN");

    final CountDownLatch signalData = new CountDownLatch(1);


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        BookCatalogInteractor interactor = new BookCatalogInteractor(new BookstoreModel(getContext()));
        cartModel = new BookCartModel();
        presenter = new BookCatalogPresenter(interactor, cartModel);
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
     * Test that, on method 'create' call, the presentable view receive the list of books.
     */
    public void testCreate(){
        presenter.setPresentableView(new PresentableViewTest());

        presenter.create();

        awaitSignalData();
    }

    /**
     * Test if the method addToCart correctly add the given book to the cart.
     */
    public void testAddToCart(){
        presenter.setPresentableView(new PresentableViewTest());

        presenter.addToCart(testBook);

        assertTrue("Cart is empty", presenter.getBookCartSize() > 0);
        assertTrue("Cart does not contains book", cartModel.containsBook(testBook));
    }

    /**
     * Test if the 'resume' method correctly update the 'inCart' status of the book catalog
     */
    public void testResume(){
        presenter.setPresentableView(new PresentableViewTest() {
            @Override
            public void updateBookCatalog(List<Book> bookList) {
                bookList.get(0).setInCart(true);
                super.updateBookCatalog(bookList);
            }
        });

        presenter.create();

        awaitSignalData();

        // After book list obtained
        presenter.setPresentableView(new PresentableViewTest() {
            @Override
            public void updateBookCatalog(List<Book> bookList) {
                assertFalse("Book must not be noticed as in the cart", bookList.get(0).isInCart());
                super.updateBookCatalog(bookList);
            }
        });

        presenter.resume();

        awaitSignalData();
    }

    /**
     * Test if the 'destroy' method correctly detach this presenter from any background task
     */
    public void testDestroy(){
        presenter.setPresentableView(new PresentableViewTest(){
            @Override
            public void updateBookCatalog(List<Book> bookList) {
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
    private class PresentableViewTest implements BookCatalogPresenter.BookCatalogPresentableView{

        final CountDownLatch signalView = new CountDownLatch(1);

        @Override
        public void updateBookCatalog(List<Book> bookList) {
            assertNotNull("Received book list is null", bookList);
            assertFalse("Received book list is empty", bookList.isEmpty());

            signalData.countDown();
        }

        @Override
        public Context getContext() {
            return BookCatalogPresenterTest.this.getContext();
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