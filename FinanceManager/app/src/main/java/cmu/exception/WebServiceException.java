package cmu.exception;

/**
 * Created by Pedro on 27/11/2015.
 * Web Service exception handler. Handles exception in the Services tier.
 */
public class WebServiceException {
    private int errno;

    public WebServiceException(int errno){
        this.errno = errno;
    }

    /* Try to fix the error if possible with the error number */
    public void fix() {
        FixException fix = new FixException();
        switch(errno) {
            case 9:
                fix.fix9(errno);
                break;
        }
    }
}
