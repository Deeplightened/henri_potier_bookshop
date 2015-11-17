package fr.enlight.henripotierbookshop.presentation.model;

import java.util.ArrayList;
import java.util.List;

import fr.enlight.hpdata.hpbooks.entities.HPBook;
import fr.enlight.hpdata.hpbooks.entities.HPBookCommercialOffers;

/**
 * A class defining the cart data used by the application. Contains all books reserved by the user and the associated commercial offer.
 */
public class BookCart {

    private final List<HPBook> listHPBooks = new ArrayList<>();
    private HPBookCommercialOffers commercialOffer;

    public boolean addBook(HPBook object) {
        return listHPBooks.add(object);
    }

    public void clearBooks() {
        listHPBooks.clear();
    }

    public HPBook removeBook(int location) {
        return listHPBooks.remove(location);
    }

    public boolean removeBook(HPBook object) {
        return listHPBooks.remove(object);
    }

    public HPBookCommercialOffers getCommercialOffer() {
        return commercialOffer;
    }

    public void setCommercialOffer(HPBookCommercialOffers commercialOffer) {
        this.commercialOffer = commercialOffer;
    }
}
