package fr.enlight.hpdata.entities;

import java.util.List;

/**
 * A class defining the commercial offers made for a cart list of HP books.
 */
public class HPBookCommercialOffers {

    private List<HPBookOffer> offers;

    public List<HPBookOffer> getOffers() {
        return offers;
    }

    public void setOffers(List<HPBookOffer> offers) {
        this.offers = offers;
    }
}
