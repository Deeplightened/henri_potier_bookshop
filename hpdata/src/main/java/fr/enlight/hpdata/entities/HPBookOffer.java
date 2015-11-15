package fr.enlight.hpdata.entities;

/**
 * A class defining an offer element calculated from the bookstore commercial offers.
 */
public class HPBookOffer {

    public static final String PERCENTAGE_TYPE = "percentage";
    public static final String MINUS_TYPE = "minus";
    public static final String SLICE_TYPE = "slice";

    private String type;
    private short value;
    private short sliceValue;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public short getValue() {
        return value;
    }

    public void setValue(short value) {
        this.value = value;
    }

    public short getSliceValue() {
        return sliceValue;
    }

    public void setSliceValue(short sliceValue) {
        this.sliceValue = sliceValue;
    }
}
