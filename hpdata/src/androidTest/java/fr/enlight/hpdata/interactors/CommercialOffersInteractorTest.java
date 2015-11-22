package fr.enlight.hpdata.interactors;


import android.test.AndroidTestCase;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import fr.enlight.hpdata.exceptions.IncorrectParametersException;
import fr.enlight.hpdata.hpbooks.BookstoreModel;
import fr.enlight.hpdata.hpbooks.entities.HPBookCommercialOffers;
import rx.Subscriber;

/**
 * Test class that will check if the CommercialOffersInteractor correctly send his informations.
 *
 */
public class CommercialOffersInteractorTest extends AndroidTestCase {

    private final String BOOK_ISBN_1 = "c8fabf68-8374-48fe-a7ea-a00ccd07afff";
    private final String BOOK_ISBN_2 = "a460afed-e5e7-4e39-a39d-c885c05db861";

    CommercialOffersInteractor interactor;
    final CountDownLatch signal = new CountDownLatch(1);

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        interactor = new CommercialOffersInteractor(new BookstoreModel(getContext()));
    }

    /**
     * Test the case with a single ISBN number, with network working correctly.
     */
    public void testSingleISBNCase() throws InterruptedException {
        interactor.setIsbnParameters(Collections.singletonList(BOOK_ISBN_1));
        interactor.execute(new Subscriber<HPBookCommercialOffers>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                fail("An error has been thrown : " + e.getMessage());
                signal.countDown();
            }

            @Override
            public void onNext(HPBookCommercialOffers commercialOffers) {
                assertNotNull("Received commercial offers is null", commercialOffers);
                assertNotNull("Received commercial offers list is null", commercialOffers.getOffers());
                assertNotNull("Received commercial offers list is empty", commercialOffers.getOffers().isEmpty());
                signal.countDown();
            }
        });

        signal.await(10, TimeUnit.SECONDS);
    }

    /**
     * Test the case with a single ISBN number, with network working correctly.
     */
    public void testMultipleISBNCase() throws InterruptedException {
        interactor.setIsbnParameters(Arrays.asList(BOOK_ISBN_1, BOOK_ISBN_2));
        interactor.execute(new Subscriber<HPBookCommercialOffers>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                fail("An error has been thrown : " + e.getMessage());
                signal.countDown();
            }

            @Override
            public void onNext(HPBookCommercialOffers commercialOffers) {
                assertNotNull("Received commercial offers is null", commercialOffers);
                assertNotNull("Received commercial offers list is null", commercialOffers.getOffers());
                assertNotNull("Received commercial offers list is empty", commercialOffers.getOffers().isEmpty());
                signal.countDown();
            }
        });

        signal.await(10, TimeUnit.SECONDS);
    }

    /**
     * Test the case with no ISBN number, should triggers an error.
     */
    public void testNoISBNErrorCase() throws InterruptedException {
        interactor.setIsbnParameters(null);
        interactor.execute(new Subscriber<HPBookCommercialOffers>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                assertTrue("The interactor should triggers a IncorrectParametersException but was " + e.getClass(), (e instanceof IncorrectParametersException));
                signal.countDown();
            }

            @Override
            public void onNext(HPBookCommercialOffers commercialOffers) {
                fail("There should no be any result here");
                signal.countDown();
            }
        });

        signal.await(10, TimeUnit.SECONDS);
    }
}