package cmu.adapter;

import java.io.Serializable;
import java.util.ArrayList;

import cmu.model.Account;
import cmu.model.ExpenseType;
import cmu.model.Record;

/**
 * Created by Qiang on 11/21/2015.
 * Wrapper class. This class wraps all the objects into a single one and enables sending the
 * object using a single object transaction.
 */
public class Wrapper implements Serializable{
    private static final long serialVersionUID = 1L;
    private ArrayList<Record>records;
    private ArrayList<Account>accounts;
    private ArrayList<ExpenseType>expenseTypes;

    public Wrapper(ArrayList<Record>records, ArrayList<Account>accounts, ArrayList<ExpenseType>expenseTypes) {
        this.records = records;
        this.accounts = accounts;
        this.expenseTypes = expenseTypes;
    }

    public ArrayList<Record> getRecords() {
        return records;
    }


    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public ArrayList<ExpenseType> getExpenseTypes() {
        return expenseTypes;
    }



}
