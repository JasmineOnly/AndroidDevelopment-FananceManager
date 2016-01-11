package cmu.adapter;

import android.content.Context;

/**
 * Created by Yuanyuan on 11/10/15.
 * Abstract class which is extended by BuildRecord.
 * This abstract class encapsulates the interfaces and methods are implemented here.
 * This is the abstract API they should use.
 */
public abstract class ProxyRecord {

    public void insertNewAccount(String account, Context context){
        DatabaseAdapter da = new DatabaseAdapter(context);
        da.insertNewAccount(account);

    }

    public void insertNewExpenseType(String expenseType, Context context){
        DatabaseAdapter da = new DatabaseAdapter(context);
        da.insertNewExpenseType(expenseType);
    }

    public void insertNewRecord(String date, double amount, String account, String expenseType,
                                String description, Context context){
        DatabaseAdapter da = new DatabaseAdapter(context);
        da.insertNewRecord(date, amount, account, expenseType, description);

    }


    public void updateAccountName(String newName, String oldName, Context context){
        DatabaseAdapter da = new DatabaseAdapter(context);
        da.updateAccountName(newName, oldName);

    }

    public void updateExpenseTypeName(String newName, String oldName, Context context){
        DatabaseAdapter da = new DatabaseAdapter(context);
        da.updateExpenseTypeName(newName, oldName);
    }

    public void deleteAccount(String account, Context context){
        DatabaseAdapter da = new DatabaseAdapter(context);
        da.deleteAccount(account);
    }

    public void deleteExpenseType(String expenseType, Context context){
        DatabaseAdapter da = new DatabaseAdapter(context);
        da.deleteExpenseType(expenseType);
    }

}
