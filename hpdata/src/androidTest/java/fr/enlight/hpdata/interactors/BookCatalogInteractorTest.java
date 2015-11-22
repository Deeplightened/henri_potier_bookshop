package fr.enlight.hpdata.interactors;

import android.test.AndroidTestCase;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import fr.enlight.hpdata.hpbooks.BookstoreModel;
import fr.enlight.hpdata.hpbooks.entities.HPBook;
import rx.Subscriber;

/**
 * Test class that will check if the BookCatalogInteractor correctly send his informations.
 */
public class BookCatalogInteractorTest extends AndroidTestCase {

    BookCatalogInteractor interactor;
    final CountDownLatch signal = new CountDownLatch(1);


    @Override
    protected void setUp() throws Exception {
        super.setUp();

        interactor = new BookCatalogInteractor(new BookstoreModel(getContext()));
    }

    /**
     * Test the normal case, with network working correctly.
     */
    public void testNormalCase() throws InterruptedException {
        interactor.execute(new Subscriber<List<HPBook>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                fail("An error has been thrown : " + e.getMessage());
                signal.countDown();
            }

            @Override
            public void onNext(List<HPBook> hpBooks) {
                assertNotNull("Received book list is null", hpBooks);
                assertFalse("Received book list is empty", hpBooks.isEmpty());
                signal.countDown();
            }
        });

        signal.await(10, TimeUnit.SECONDS);
    }
}