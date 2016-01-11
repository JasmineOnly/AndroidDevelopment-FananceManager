package cmu.exception;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Pedro on 27/11/2015.
 * This class will attempt to fix any errors, prompt any warning message or ignore error when
 * it is not fatal.
 */
public class FixException {

    // Database creation and open error
    public void fix0(int errno) {
        System.out.println(errno);
    }

    // Database close error
    public void fix1(int errno) {
        System.out.println(errno);
    }

    // Database insert, update, delete error
    public void fix2(int errno) {
        System.out.println(errno);
    }

    // Database select error
    public void fix3(int errno) {
        System.out.println(errno);
    }

    // Adding and removing an account
    public void fix4(int errno) {
        System.out.println(errno);
    }

    // Adding and removing an expense type
    public void fix5(int errno) {
        System.out.println(errno);
    }

    // Adding and removing a record
    public void fix6(int errno) {
        System.out.println(errno);
    }

    // UI general error
    public void fix7(int errno) {
        System.out.println(errno);
    }

    // UI message error
    public void fix8(int errno, String errmsg, Context context) {
        System.out.println(errno);
        Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show();
    }

    // Web Service error
    public void fix9(int errno) {
        System.out.println(errno);
    }
}
