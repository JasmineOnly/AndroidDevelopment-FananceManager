package cmu.exception;

/**
 * Created by Pedro on 27/11/2015.
 * Database exception handler. Handles exception in the Database tier.
 */
public class DatabaseException extends Exception{
    private int errno;

    public DatabaseException(int errno) {
        this.errno = errno;
    }

    /* Try to fix the error if possible with the error number */
    public void fix() {
        FixException fix = new FixException();
        switch(errno) {
            case 9:
                fix.fix0(errno);
                break;
            case 1:
                fix.fix1(errno);
                break;
            case 2:
                fix.fix2(errno);
                break;
            case 3:
                fix.fix3(errno);
                break;
        }
    }
}
