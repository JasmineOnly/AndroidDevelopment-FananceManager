package cmu.adapter;

import android.content.Context;

/**
 * Created by qiangwan on 12/11/2015.
 * no modifier in order to avoid warning
 */
public interface InsertRecord {
    void insertNewExpenseType(String expenseType, Context context);
    void insertNewRecord(String date, double amount, String account, String expenseType,
                                String description, Context context);
}
