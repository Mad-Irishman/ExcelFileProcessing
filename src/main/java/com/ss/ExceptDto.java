/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ss;

import java.util.Random;

/**
 * @author vlitenko
 */
public class ExceptDto {
    private static final Random random = new Random(System.currentTimeMillis());
    protected final String code;
    protected final String extendedMessage;
    protected final Long id;


    public ExceptDto(String errorCode, String message, String extendedMessage) {
        this.code = errorCode;
        this.extendedMessage = extendedMessage;
        this.id = Math.abs(random.nextLong());
    }

    public String getMessage4Support(Exception exception) {
        StringBuilder sCauseRes = new StringBuilder();
        boolean bFirst = true;
        Throwable xExCurrent = exception;
        while (xExCurrent.getCause() != null) {
            String sCause;
            if (xExCurrent.getCause() instanceof Except_I)
                sCause = ((Except_I) xExCurrent.getCause()).getMessage4Support();
            else
                sCause = xExCurrent.getCause().getMessage();
            if (bFirst)
                sCauseRes.append(sCause);
            else
                sCauseRes.append(", cause: ").append(sCause);
            bFirst = false;
            xExCurrent = xExCurrent.getCause();
        }
        if (sCauseRes.isEmpty())
            return String.format("Ed%d | %s | %s ", id, code, extendedMessage);

        return String.format("Ed%d | %s | %s | cause: [%s]", id, code, extendedMessage, sCauseRes.toString());
    }

    public String getMessage4User(Exception exception) {
        return String.format("Ed%d | %s", id, exception.getMessage());
    }

    public Long getId() {
        return id;
    }

    public String getCodeId() {
        return String.format("Ed%d", id);
    }

    public String getErrorCode() {
        return code;
    }

    public String getMessage4Monitor(Exception p_xEx) {
        return String.format("Ed%d | %s | %s", id, code, p_xEx.getMessage());
    }

}
