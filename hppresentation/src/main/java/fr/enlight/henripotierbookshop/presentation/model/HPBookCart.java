package fr.enlight.henripotierbookshop.presentation.model;

import java.util.ArrayList;
import java.util.List;

import fr.enlight.hpdata.entities.HPBook;
import fr.enlight.hpdata.entities.HPBookCommercialOffers;

/**
 * A class defining the cart data used by the application. Contains all books reserved by the user and the associated commercial offer.
 */
public class HPBookCart {

    private final List<HPBook> listHPBooks = new ArrayList<>();
    private HPBookCommercialOffers commercialOffer;

    public boolean addHPBook(HPBook object) {
        return listHPBooks.add(object);
    }

    public void clearHPBooks() {
        listHPBooks.clear();
    }

    public HPBook removeHPBook(int location) {
        return listHPBooks.remove(location);
    }

    public boolean removeHPBook(HPBook object) {
        return listHPBooks.remove(object);
    }

    public HPBookCommercialOffers getCommercialOffer() {
        return commercialOffer;
    }

    public void setCommercialOffer(HPBookCommercialOffers commercialOffer) {
        this.commercialOffer = commercialOffer;
    }
}
