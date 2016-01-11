package cmu.exception;

/**
 * Created by Pedro on 27/11/2015.
 * Record exception handler. Handles exception in the Business tier.
 */
public class ModelException extends Exception {
    private int errno;

    public ModelException(int errno) {
        this.errno = errno;
    }

    /* Try to fix the error if possible with the error number */
    public void fix() {
        FixException fix = new FixException();
        switch(errno) {
            case 4:
                fix.fix4(errno);
                break;
            case 5:
                fix.fix5(errno);
                break;
            case 6:
                fix.fix6(errno);
                break;
        }
    }
}
