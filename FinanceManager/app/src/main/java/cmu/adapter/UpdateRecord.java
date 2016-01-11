package cmu.adapter;

import android.content.Context;

/**
 * Created by Yuanyuan on 11/10/15.
 * Interface used to create a record object, implemented by BuildRecord.
 * no modifier in order to avoid warning
 */
public interface UpdateRecord {
     void updateAccountName(String newName, String oldName, Context context);
     void updateExpenseTypeName(String newName, String oldName, Context context);
}
