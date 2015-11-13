package fr.enlight.henripotierbookshop.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * A class defining the cart data used by the application. Contains all books reserved by the user and the associated commercial offer.
 */
public class HPBookCart {

    private final List<BookEntity> listHPBooks = new ArrayList<>();
    private BookCommercialOffers commercialOffer;

    public boolean addHPBook(BookEntity object) {
        return listHPBooks.add(object);
    }

    public void clearHPBooks() {
        listHPBooks.clear();
    }

    public BookEntity removeHPBook(int location) {
        return listHPBooks.remove(location);
    }

    public boolean removeHPBook(BookEntity object) {
        return listHPBooks.remove(object);
    }

    public BookCommercialOffers getCommercialOffer() {
        return commercialOffer;
    }

    public void setCommercialOffer(BookCommercialOffers commercialOffer) {
        this.commercialOffer = commercialOffer;
    }
}
