package com.ss;

/**
 * @author Alexey S.
 */
public class Except4SupportDocumented extends Except {
    private static final String MEG_INTERNAL_ERROR = "Внутренняя ошибка";

    public Except4SupportDocumented(String errorCode, String message, String extendedMessage, Throwable cause) {
        super(errorCode, message, extendedMessage, cause);
    }

    public Except4SupportDocumented(String errorCode, String message, String extendedMessage) {
        super(errorCode, message, extendedMessage, null);
    }

    public Except4SupportDocumented(String errorCode, String extendedMessage, Throwable cause) {
        super(errorCode, MEG_INTERNAL_ERROR, extendedMessage, cause);
    }

    public Except4SupportDocumented(String errorCode, String extendedMessage) {
        super(errorCode, MEG_INTERNAL_ERROR, extendedMessage, null);
    }

}
