package cmu.exception;

import android.content.Context;

/**
 * Created by Pedro on 27/11/2015.
 * UI exception handler. Handles exception in the Presentation tier.
 */
public class UiException extends Exception{
    private int errno;
    private String errmsg;
    Context context;

    public UiException(int errno, String errmsg, Context context) {
        this.errno = errno;
        this.errmsg = errmsg;
        this.context = context;
    }
    public UiException(int errno) {
        this.errno = errno;
        this.errmsg = "";
    }

    /* Try to fix the error if possible with the error number */
    public void fix() {
        FixException fix = new FixException();
        switch(errno) {
            case 7:
                fix.fix7(errno);
                break;
            case 8:
                fix.fix8(errno, errmsg, context);
                break;
        }
    }
}
