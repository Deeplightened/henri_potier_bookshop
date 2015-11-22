package fr.enlight.hpdata.exceptions;

/**
 * Created by enlight on 22/11/2015.
 */
public class IncorrectParametersException extends Exception {
    private static final long serialVersionUID = 8700219655605711191L;

    public IncorrectParametersException() {
        super();
    }

    public IncorrectParametersException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectParametersException(String message) {
        super(message);
    }
}
