package fr.enlight.henripotierbookshop.presentation.model;

/**
 * An entity representing an offer to show in the user cart.
 */
public class BookOffer {

    public static final String PERCENTAGE_REDUCTION_TYPE = "PERCENTAGE_REDUCTION_TYPE";
    public static final String MINUS_REDUCTION_TYPE = "MINUS_REDUCTION_TYPE";

    private String offerMessage;
    private short reductionValue;
    private String reductionMessage;
    private String reductionType;

    public static BookOffer newInstance(String message, String reductionMessage, short reductionValue, String reductionType) {
        BookOffer result = new BookOffer();
        result.offerMessage = message;
        result.reductionValue = reductionValue;
        result.reductionMessage = reductionMessage;
        result.reductionType = reductionType;
        return result;
    }

    public String getOfferMessage() {
        return offerMessage;
    }

    public void setOfferMessage(String offerMessage) {
        this.offerMessage = offerMessage;
    }

    public short getReductionValue() {
        return reductionValue;
    }

    public void setReductionValue(short reductionValue) {
        this.reductionValue = reductionValue;
    }

    public String getReductionMessage() {
        return reductionMessage;
    }

    public void setReductionMessage(String reductionMessage) {
        this.reductionMessage = reductionMessage;
    }

    public String getReductionType() {
        return reductionType;
    }

    public void setReductionType(String reductionType) {
        this.reductionType = reductionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookOffer bookOffer = (BookOffer) o;

        if (reductionValue != bookOffer.reductionValue) return false;
        if (offerMessage != null ? !offerMessage.equals(bookOffer.offerMessage) : bookOffer.offerMessage != null)
            return false;
        return !(reductionMessage != null ? !reductionMessage.equals(bookOffer.reductionMessage) : bookOffer.reductionMessage != null);

    }

    @Override
    public int hashCode() {
        int result = offerMessage != null ? offerMessage.hashCode() : 0;
        result = 31 * result + (int) reductionValue;
        result = 31 * result + (reductionMessage != null ? reductionMessage.hashCode() : 0);
        return result;
    }
}
