package cmu.adapter;

import java.io.Serializable;
import java.util.ArrayList;

import cmu.model.Account;
import cmu.model.ExpenseType;
import cmu.model.Record;

/**
 * Created by qiangwan on 11/21/2015.
 */

/**
 * this class is to wrap up the records, accounts and expenseTypes
 * and communicate with client
 *
 */

public class Wrapper implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<Record>records;
    private ArrayList<Account>accounts;
    private ArrayList<ExpenseType>expenseTypes;
    
    //constructor
    public Wrapper(ArrayList<Record>records, ArrayList<Account>accounts, ArrayList<ExpenseType>expenseTypes) {
        this.records = records;
        this.accounts = accounts;
        this.expenseTypes = expenseTypes;
    }

    //getters and setters
    public ArrayList<Record> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<Record> records) {
        this.records = records;
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

    public void setExpenseTypes(ArrayList<ExpenseType> expenseTypes) {
        this.expenseTypes = expenseTypes;
    }
}
