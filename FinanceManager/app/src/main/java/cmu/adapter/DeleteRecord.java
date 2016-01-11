package cmu.adapter;

import android.content.Context;

/**
 * Created by qiangwan on 12/11/2015.
 * no modifier in order to avoid warning
 */
public interface DeleteRecord {
    void deleteAccount(String account, Context context);
    void deleteExpenseType(String expenseType, Context context);
}
