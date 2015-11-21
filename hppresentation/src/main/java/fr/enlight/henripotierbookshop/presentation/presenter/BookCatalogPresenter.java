package fr.enlight.henripotierbookshop.presentation.presenter;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.enlight.henripotierbookshop.R;
import fr.enlight.henripotierbookshop.presentation.model.Book;
import fr.enlight.henripotierbookshop.presentation.model.BookCartModel;
import fr.enlight.hpdata.hpbooks.entities.HPBook;
import fr.enlight.hpdata.interactors.BookCatalogInteractor;
import rx.Subscriber;

/**
 * This presenter aims to manage the model associated to the book catalogs views.
 */
public class BookCatalogPresenter implements AbstractPresenter {

    private final BookCatalogInteractor interactor;
    private final BookCartModel bookCartModel;

    private List<Book> bookModel;

    private BookCatalogPresentableView presentableView;

    public BookCatalogPresenter(BookCatalogInteractor interactor, BookCartModel bookCartModel){
        this.interactor = interactor;
        this.bookCartModel = bookCartModel;
    }

    public void setPresentableView(BookCatalogPresentableView presentableView) {
        this.presentableView = presentableView;
    }

    @Override
    public void resume() {
        // We update the book list in function of there presence in the cart
        updateBookInCartStatus();
    }

    @Override
    public void pause() {
        // Nothing to do
    }

    @Override
    public void create() {
        updateBookCatalog();
    }

    @Override
    public void destroy() {
        interactor.unsubscribeCurrentSubscription();
    }

    /**
     * Ask for the book catalog to be loaded in the presentable view
     */
    public void updateBookCatalog(){
        presentableView.showLoadingView();

        interactor.execute(new BookCatalogSubscriber());
    }

    private void updateBookInCartStatus() {
        if(bookModel != null) {
            for (Book book : bookModel) {
                book.setInCart(bookCartModel.containsBook(book));
            }
            presentableView.updateBookCatalog(bookModel);
        }
    }

    /**
     * Convert received HPBook list to Book entity list managed by the presentation layer
     * @param hpBooks the received HPBook list
     * @return the converter Book list
     */
    private List<Book> convertHPBooks(List<HPBook> hpBooks) {
        List<Book> result = new ArrayList<>();
        Book book;
        for (HPBook hpBook : hpBooks) {
            book = Book.newInstance(hpBook.getTitle(), hpBook.getCover(), hpBook.getPrice(), hpBook.getIsbn());
            result.add(book);
        }
        return result;
    }

    /**
     * Add a book to the user cart.
     *
     * @param book the book to add
     */
    public void addToCart(Book book) {
        if(book != null && !bookCartModel.containsBook(book)){
            bookCartModel.addBook(book);
        }
    }

    /**
     * Notify the PresentableView of the update of the book catalog
     */
    public void notifyUpdateBookCatalog(){
        updateBookInCartStatus();
        presentableView.updateBookCatalog(bookModel);
    }

    /**
     * @return the number of books in cart
     */
    public int getBookCartSize() {
        return bookCartModel.bookListSize();
    }

    /**
     * A Subscriber used to retrieve the list of HPBook from data, using the BookCatalogInteractor.
     */
    public final class BookCatalogSubscriber extends Subscriber<List<HPBook>>{
        @Override
        public void onCompleted() {
            // TODO Vérifier pourquoi le onComplete n'est pas appelé
        }

        @Override
        public void onError(Throwable exception) {
            // TODO Error management
            Context context = presentableView.getContext();
            presentableView.onLoadingFailed(context.getString(R.string.error_message_network_failed));
            Log.e(getClass().getSimpleName(), "Book catalog download failed", exception);
        }

        @Override
        public void onNext(List<HPBook> hpBooks) {
            bookModel = convertHPBooks(hpBooks);
            notifyUpdateBookCatalog();
            presentableView.hideLoadingView();
            Log.i(getClass().getSimpleName(), "Book received : " + hpBooks.toString());
        }
    }

    public interface BookCatalogPresentableView extends PresentableView {

        /**
         * Update the book catalog data presented by the listener.
         *
         * @param bookList the updated list of books.
         */
        void updateBookCatalog(List<Book> bookList);
    }
}
