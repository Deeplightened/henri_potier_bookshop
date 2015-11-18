package fr.enlight.henripotierbookshop.presentation.presenter;

import java.util.Arrays;
import java.util.List;

import fr.enlight.henripotierbookshop.presentation.model.Book;
import fr.enlight.henripotierbookshop.presentation.model.BookCartModel;
import fr.enlight.hpdata.interactors.BookCatalogInteractor;

/**
 * Created by enlight on 12/11/2015.
 */
public class BookCatalogPresenter implements AbstractPresenter {

    private final BookCatalogInteractor interactor;
    private final BookCartModel bookCartModel;

    private BookCatalogPresenterView presenterView;

    public BookCatalogPresenter(BookCatalogInteractor interactor, BookCartModel bookCartModel){
        this.interactor = interactor;
        this.bookCartModel = bookCartModel;
    }

    public void registerPresenterView(BookCatalogPresenterView presenterView) {
        this.presenterView = presenterView;
    }

    public void updateData(){
        // TODO replace with real code
        List<Book> bookList = Arrays.asList(
            createBook("Henri Potier à l'école des sorciers", 35, "http://henri-potier.xebia.fr/hp0.jpg"),
            createBook("Henri Potier et la Chambre des secrets", 30, "http://henri-potier.xebia.fr/hp1.jpg"),
            createBook("Henri Potier et le Prisonnier d'Azkaban", 30, "http://henri-potier.xebia.fr/hp2.jpg"),
            createBook("Henri Potier et la Coupe de feu", 29, "http://henri-potier.xebia.fr/hp3.jpg"),
            createBook("Henri Potier et l'Ordre du phénix", 28, "http://henri-potier.xebia.fr/hp4.jpg"),
            createBook("Henri Potier et le Prince de sang-mêlé",30, "http://henri-potier.xebia.fr/hp5.jpg"),
            createBook( "Henri Potier et les Reliques de la Mort", 35, "http://henri-potier.xebia.fr/hp6.jpg")
        );

        presenterView.updateBookCatalog(bookList);
    }

    // TODO To remove
    private Book createBook(String title, int price, String cover){
        Book book = new Book();
        book.setTitle(title);
        book.setCoverImageUrl(cover);
        book.setPrice((short) price);
        return book;
    }

    public interface BookCatalogPresenterView extends PresenterView {

        /**
         * Update the book catalog data presented by the listener.
         *
         * @param bookList the updated list of books.
         */
        void updateBookCatalog(List<Book> bookList);
    }
}
