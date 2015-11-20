package fr.enlight.henripotierbookshop.presentation.presenter;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;

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

    private float tvaPercentage = -1;

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
     * Ask to load the cart content (list of books and commercial offers associated to this list).
     * The PresentableView is then updated with the method updateCartContent and updateCartTotalPrice.
     */
    public void loadCartContent() {
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
                if(bookOffer != null){
                    bookOffers.add(bookOffer);
                }
            }

            bookCartModel.setOfferList(bookOffers);
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
                reductionMessage = Short.toString(bookOffer.getValue()) + " %";
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
     * Notifies the PresentableView that the cart has been updated
     */
    private void notifyCartUpdated() {
        double total = bookCartModel.computeTotalWithOffers();

        double tva = total * getTvaPercentage() / 100;

        presentableView.updateCartContent(bookCartModel.getListBooks(), bookCartModel.getOfferList());
        presentableView.updateCartTotalPrice(total, tva);
    }

    public float getTvaPercentage() {
        if(tvaPercentage < 0){
            Context context = presentableView.getContext();
            TypedValue typedValue = new TypedValue();
            context.getResources().getValue(R.dimen.tva_percentage, typedValue, true);
            tvaPercentage = typedValue.getFloat();
        }
        return tvaPercentage;
    }

    /**
     * Delete a book item from the cart, then update the PresentableView
     * @param bookItem the book item to delete
     */
    public void deleteBookItem(Book bookItem) {
        bookCartModel.removeBook(bookItem);
        notifyCartUpdated();
    }

    /**
     * Clear the cart from all books and offers.
     */
    public void clearCart() {
        bookCartModel.clearBookList();
        bookCartModel.setOfferList(null);
    }

    public int getCartBookCount() {
        return bookCartModel.bookListSize();
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
            Context context = presentableView.getContext();
            presentableView.onLoadingFailed(context.getString(R.string.error_message_network_failed));
            Log.e(getClass().getSimpleName(), "Book commercial offers download failed", exception);
        }

        @Override
        public void onNext(HPBookCommercialOffers hpBookCommercialOffers) {
            updateCartWithCommercialOffers(hpBookCommercialOffers);
            notifyCartUpdated();
            presentableView.hideLoadingView();
        }
    }

    public interface BookCartPresentableView extends PresentableView{

        /**
         * Ask the PresentableView to update the presentation of the cart with
         * the given books and list of offers associated.
         *
         * @param cartBooks the concerned books
         * @param bookOffers the concerned list of offers
         */
        void updateCartContent(List<Book> cartBooks, List<BookOffer> bookOffers);

        /**
         * Ask the PresentableView to update the total price of the cart
         *
         * @param total the total price of the cart
         */
        void updateCartTotalPrice(double total, double tva);
    }
}
