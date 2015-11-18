package fr.enlight.henripotierbookshop.presentation.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yhuriez on 17/11/2015.
 */
public class BookCartModel {

    private final Set<Book> listBooks = new HashSet<>();
    private List<BookOffer> offerList;

    public int computeCartTotalPrice(){

        // TODO Faire la méthode de calcul

        return -1;
    }

    // region getters and delegate methods

    public boolean addBook(Book object) {
        return listBooks.add(object);
    }

    public boolean containsBook(Book object) {
        return listBooks.contains(object);
    }

    public boolean removeBook(Book object) {
        return listBooks.remove(object);
    }

    public void clearBookList() {
        listBooks.clear();
    }

    public boolean isEmpty() {
        return listBooks.isEmpty();
    }

    public Set<Book> getListBooks() {
        return listBooks;
    }

    public List<BookOffer> getOfferList() {
        return offerList;
    }

    public void setOfferList(List<BookOffer> offerList) {
        this.offerList = offerList;
    }

    // endregion
}
