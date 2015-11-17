package fr.enlight.henripotierbookshop.presentation.model;

import fr.enlight.hpdata.hpbooks.entities.HPBookOffer;

/**
 * An entity representing an offer to show in the user cart.
 */
public class BookOffer {

    private String offerMessage;
    private String offerType;
    private short reductionValue;
    private short sliceValue;

    public static BookOffer newInstance(String message, HPBookOffer offer) {
        BookOffer result = new BookOffer();
        result.setOfferMessage(message);
        result.setOfferType(offer.getType());
        result.setReductionValue(offer.getValue());
        result.setSliceValue(offer.getSliceValue());
        return result;
    }

    public String getOfferMessage() {
        return offerMessage;
    }

    public void setOfferMessage(String offerMessage) {
        this.offerMessage = offerMessage;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public short getReductionValue() {
        return reductionValue;
    }

    public void setReductionValue(short reductionValue) {
        this.reductionValue = reductionValue;
    }

    public short getSliceValue() {
        return sliceValue;
    }

    public void setSliceValue(short sliceValue) {
        this.sliceValue = sliceValue;
    }
}
