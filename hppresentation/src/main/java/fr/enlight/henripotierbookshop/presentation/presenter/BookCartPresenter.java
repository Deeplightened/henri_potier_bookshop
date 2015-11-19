package fr.enlight.henripotierbookshop.presentation.presenter;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.enlight.henripotierbookshop.R;
import fr.enlight.henripotierbookshop.presentation.model.Book;
import fr.enlight.henripotierbookshop.presentation.model.BookCartModel;
import fr.enlight.henripotierbookshop.presentation.model.BookOffer;
import fr.enlight.hpdata.hpbooks.entities.HPBookCommercialOffers;
import fr.enlight.hpdata.hpbooks.entities.HPBookOffer;
import fr.enlight.hpdata.interactors.CommercialOffersInteractor;
import rx.Subscriber;

/**
 * A Presenter used to manage cart presentation and interact with cart data.
 */
public class BookCartPresenter implements AbstractPresenter {

    private final CommercialOffersInteractor interactor;
    private final BookCartModel bookCartModel;

    private BookCartPresentableView presentableView;

    public BookCartPresenter(CommercialOffersInteractor interactor, BookCartModel bookCartModel){
        this.interactor = interactor;
        this.bookCartModel = bookCartModel;
    }

    public void setPresentableView(BookCartPresentableView presentableView) {
        this.presentableView = presentableView;
    }

    @Override
    public void resume() {
        // Nothing to do here
    }

    @Override
    public void pause() {
        // Nothing to do here
    }

    @Override
    public void create() {
        loadCartContent();
    }

    @Override
    public void destroy() {
        interactor.unsubscribeCurrentSubscription();
    }

    /**
     * Ask to load the list of books that has been added to the cart.
     * The PresentableView is then updated with the method updateCartContent.
     */
    private void loadCartContent() {
        presentableView.updateCartContent(bookCartModel.getListBooks());
    }

    /**
     * Ask to load commercial offers associated to the list of books presents in the cart.
     * The PresentableView is then updated with the method updateCommercialOffers and updateCartTotalPrice.
     */
    private void loadCommercialOffers() {
        presentableView.showLoadingView();

        // Prepare parameters
        List<Book> listBooks = bookCartModel.getListBooks();
        List<String> isbnList = new ArrayList<>();
        for (Book book : listBooks) {
            isbnList.add(book.getIsbn());
        }

        interactor.setIsbnParameters(isbnList);
        interactor.execute(new BookCartSubscriber());
    }

    /**
     * Update the BookCartModel with the received HPBookCommercialOffers.
     *
     * @param commercialOffers the received commercial offers
     */
    private void updateCartWithCommercialOffers(HPBookCommercialOffers commercialOffers){
        if(commercialOffers != null && commercialOffers.getOffers() != null){
            List<HPBookOffer> hpOffers = commercialOffers.getOffers();

            List<BookOffer> bookOffers = new ArrayList<>();
            BookOffer bookOffer;
            double rawTotal = bookCartModel.computeRawTotal();

            for (HPBookOffer hpOffer : hpOffers) {
                bookOffer = initBookOfferFromHPOffer(hpOffer, rawTotal);
                bookOffers.add(bookOffer);
            }

            bookCartModel.setOfferList(bookOffers);

            // Commercial offers notification
            presentableView.updateCommercialOffers(bookCartModel.getOfferList());

            // Cart total notification
            presentableView.updateCartTotalPrice(bookCartModel.computeTotalWithOffers());
        }
    }

    /**
     * Init a BookOffer with a HPBookCommercialOffers.
     *
     * @param bookOffer the concerned HPBookCommercialOffers
     * @param rawTotal the total of all book prices in cart
     * @return the concerned BookOffer
     */
    private BookOffer initBookOfferFromHPOffer(HPBookOffer bookOffer, double rawTotal) {
        Context context = presentableView.getContext();

        String offerMessage = null, reductionMessage = null, reductionType = null;

        switch (bookOffer.getType()){
            case HPBookOffer.PERCENTAGE_TYPE:
                offerMessage = context.getString(R.string.percentage_reduction_message);
                reductionMessage = context.getString(R.string.percentage_placeholder, bookOffer.getValue());
                reductionType = BookOffer.PERCENTAGE_REDUCTION_TYPE;
                break;
            case HPBookOffer.MINUS_TYPE:
                offerMessage = context.getString(R.string.minus_reduction_message);
                reductionMessage = context.getString(R.string.country_currency_placeholder, bookOffer.getValue());
                reductionType = BookOffer.MINUS_REDUCTION_TYPE;
                break;
            case HPBookOffer.SLICE_TYPE:
                if(rawTotal >= bookOffer.getSliceValue()) {
                    offerMessage = context.getString(R.string.slice_reduction_message_placeholder, bookOffer.getSliceValue());
                    reductionMessage = context.getString(R.string.country_currency_placeholder, bookOffer.getValue());
                    reductionType = BookOffer.MINUS_REDUCTION_TYPE;
                }
                break;
        }

        if(offerMessage == null){
            return null;
        }

        return BookOffer.newInstance(offerMessage, reductionMessage, bookOffer.getValue(), reductionType);
    }

    /**
     * A Subscriber used to retrieve the commercial offers associated to the cart list of books, using the CommercialOffersInteractor.
     */
    public final class BookCartSubscriber extends Subscriber<HPBookCommercialOffers> {

        @Override
        public void onCompleted() {
            // TODO Vérifier pourquoi onCompleted n'est pas appelé
        }

        @Override
        public void onError(Throwable exception) {
            // TODO Error management
            Context context = presentableView.getContext();
            presentableView.onLoadingFailed(context.getString(R.string.error_message_network_failed));
            Log.e(getClass().getSimpleName(), "Book commercial offers download failed", exception);
        }

        @Override
        public void onNext(HPBookCommercialOffers hpBookCommercialOffers) {
            updateCartWithCommercialOffers(hpBookCommercialOffers);
            presentableView.hideLoadingView();
        }
    }

    public interface BookCartPresentableView extends PresentableView{

        /**
         * Ask the PresentableView to update the presentation of the cart with the given books.
         *
         * @param cartBooks the concerned books
         */
        void updateCartContent(List<Book> cartBooks);

        /**
         * Ask the PresentableView to update the list of offers associated to the current cart
         *
         * @param bookOffers the concerned list of offers
         */
        void updateCommercialOffers(List<BookOffer> bookOffers);

        /**
         * Ask the PresentableView to update the total price of the cart
         *
         * @param total the total price of the cart
         */
        void updateCartTotalPrice(double total);
    }
}
