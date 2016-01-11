package cmu.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import cmu.dblayout.DatabaseConnector;
import cmu.dblayout.DatabaseConstants;
import cmu.exception.DatabaseException;
import cmu.model.Account;
import cmu.model.ExpenseType;
import cmu.model.HeaderInfo;
import cmu.model.Record;

/**
 * Created by Pedro on 21/11/2015.
 * Database Adapter located in the business tier.
 * Serves the UI content through this API which fetches the data to the Database Connector.
 * The warning show is neglectable since it only specifies a different way to get the current date.
 */
public class DatabaseAdapter implements DatabaseConstants{
    private DatabaseConnector database;
    public DatabaseAdapter(Context context){
        database = new DatabaseConnector(context);
    }

    private void open() throws SQLException {
        database.open();
    }

    private void close(){
        database.close();
    }

    /* Modular methods for insert, update, delete and select */
    private void insert(String tableName, ContentValues content){
        database.insert(tableName, content);
    }

    private void update(long id, String tableName, ContentValues content){
        database.update(id, tableName, content);
    }

    private void delete(String tableName, String selection){
        database.delete(tableName, selection);
    }

    private Cursor select(String tableName, String[] columns, String selection){
        return database.select(tableName, columns, selection);
    }
    /* */

    /* Public APIs to serve content to the UI */
    public ArrayList<HashMap<String, String>> populateTableViewByPosition(int position){

        ArrayList<HashMap<String, String>> tableList = new ArrayList<>();

        DateFormat dateFormatYear = new SimpleDateFormat("yyyy");
        DateFormat dateFormatMonth = new SimpleDateFormat("MM/yyyy");
        DateFormat dateFormatDay = new SimpleDateFormat("dd/MM/yyyy");
        Date dateO = new Date();;
        String selection = null;

        /* Populate list view depending on position
         * The only thing that changes is the query.
         * case 0: year
         * case 1: month
         * case 2: day
         * */
        switch (position){
            case 0: selection = COLUMN_DATE + " like '%" + dateFormatYear.format(dateO) + "'";
                break;
            case 1: selection = COLUMN_DATE + " like '%" + dateFormatMonth.format(dateO) + "'";
                break;
            case 2: selection = COLUMN_DATE + " like '%" + dateFormatDay.format(dateO) + "'";
                break;
            default: selection = COLUMN_DATE + " like '%" + dateFormatMonth.format(dateO) + "'";
        }

        // Get data from db using adapter
        open();
        Cursor mCur = select(TABLE_NAME_RECORDS, null, selection);

        // Set table header
        HashMap<String,String> header = new HashMap<>();
        header.put("First", "Date");
        header.put("Second", "Amount");
        header.put("Third", "Account");
        header.put("Fourth", "Description");
        header.put("Fifth", "Expense Type");
        tableList.add(header);
        try {
            if (mCur.moveToFirst()) {
                do {
                    // Fetch from db
                    String date = mCur.getString(mCur.getColumnIndex(COLUMN_DATE));
                    String amount = mCur.getString(mCur.getColumnIndex(COLUMN_AMOUNT));
                    String accountId = mCur.getString(mCur.getColumnIndex(COLUMN_ACCOUNT));
                    String description = mCur.getString(mCur.getColumnIndex(COLUMN_DESCRIPTION));
                    String expenseTypeId = mCur.getString(mCur.getColumnIndex(COLUMN_EXPENSE_TYPE));

                    // Get account and expense type names
                    String account = null;
                    String expenseType = null;
                    try {
                        Cursor cAcc = select(TABLE_NAME_ACCOUNTS, null, COLUMN_ID + "=" + accountId);
                        if (cAcc.moveToFirst()) {
                            account = cAcc.getString(cAcc.getColumnIndex(COLUMN_NAME));
                        }
                        Cursor cExp = select(TABLE_NAME_EXPENSE_TYPES, null, COLUMN_ID + "=" +
                                expenseTypeId);
                        if (cExp.moveToFirst()) {
                            expenseType = cExp.getString(cExp.getColumnIndex(COLUMN_NAME));
                        }
                    } catch (Exception e) {
                        account = "N/A";
                        expenseType = "N/A";
                    }

                    // Put row into table
                    HashMap<String, String> row = new HashMap<>();
                    row.put("First", date);
                    row.put("Second", amount);
                    row.put("Third", account);
                    row.put("Fourth", description);
                    row.put("Fifth", expenseType);
                    tableList.add(row);

                } while (mCur.moveToNext());
            }
        } catch (Exception e){
            DatabaseException dbe = new DatabaseException(0);
            dbe.fix();
        }
        mCur.close();
        close();

        return tableList;
    }

    public ArrayList<HashMap<String, String>> populateTableView(){

        ArrayList<HashMap<String, String>> tableList = new ArrayList<>();

        // Get data from db using adapter
        open();
        Cursor mCur = select(TABLE_NAME_RECORDS, null, null);

        // Set table header
        HashMap<String,String> header = new HashMap<>();
        header.put("First", "Date");
        header.put("Second", "Amount");
        header.put("Third", "Account");
        header.put("Fourth", "Description");
        header.put("Fifth", "Expense Type");
        tableList.add(header);
        if (mCur.moveToFirst()) {
            do {
                // Fetch from db
                String date = mCur.getString(mCur.getColumnIndex(COLUMN_DATE));
                String amount = mCur.getString(mCur.getColumnIndex(COLUMN_AMOUNT));
                String accountId = mCur.getString(mCur.getColumnIndex(COLUMN_ACCOUNT));
                String description = mCur.getString(mCur.getColumnIndex(COLUMN_DESCRIPTION));
                String expenseTypeId = mCur.getString(mCur.getColumnIndex(COLUMN_EXPENSE_TYPE));

                // Get account and expense type names
                String account = null;
                String expenseType = null;
                try {
                    Cursor cAcc = select(TABLE_NAME_ACCOUNTS, null, COLUMN_ID + "=" + accountId);
                    if (cAcc.moveToFirst()) {
                        account = cAcc.getString(cAcc.getColumnIndex(COLUMN_NAME));
                    }
                    Cursor cExp = select(TABLE_NAME_EXPENSE_TYPES, null, COLUMN_ID + "=" +
                            expenseTypeId);
                    if (cExp.moveToFirst()) {
                        expenseType = cExp.getString(cExp.getColumnIndex(COLUMN_NAME));
                    }
                } catch (Exception e){
                    account = "N/A";
                    expenseType = "N/A";
                }

                // Put row into table
                HashMap<String,String> row = new HashMap<>();
                row.put("First", date);
                row.put("Second", amount);
                row.put("Third", account);
                row.put("Fourth", description);
                row.put("Fifth", expenseType);
                tableList.add(row);

            } while (mCur.moveToNext());
        }
        mCur.close();
        close();

        return tableList;
    }

    public ArrayList<String> getAllAccounts(){
        open();
        // Populate account types
        Cursor cAccounts = select(TABLE_NAME_ACCOUNTS, null, COLUMN_IS_DELETED + "='N'");
        ArrayList<String> accountList = new ArrayList<>(cAccounts.getCount());
        if (cAccounts.moveToFirst()) {
            do {
                String name = cAccounts.getString(cAccounts.getColumnIndex(COLUMN_NAME));
                accountList.add(name);
            } while (cAccounts.moveToNext());
        }
        cAccounts.close();
        close();

        return accountList;
    }

    public ArrayList<String> getAllExpenseTypesButIncome(){
        open();
        // Populate account types
        Cursor cExpenseType = select(TABLE_NAME_EXPENSE_TYPES, null, COLUMN_IS_DELETED + "='N'"
                + " AND " + COLUMN_NAME + "!='Income'");
        ArrayList<String> expenseTypeList = new ArrayList<>(cExpenseType.getCount());
        if (cExpenseType.moveToFirst()) {
            do {
                String name = cExpenseType.getString(cExpenseType.getColumnIndex(COLUMN_NAME));
                expenseTypeList.add(name);
            } while (cExpenseType.moveToNext());
        }
        cExpenseType.close();
        close();

        return expenseTypeList;
    }

    public String[] getAllAccountNames(){
        open();
        Cursor mCur = select(TABLE_NAME_ACCOUNTS, null, COLUMN_IS_DELETED + "='N'");

        // Populate list view
        int count = mCur.getCount();
        final String []name = new String[count];
        int i = 0;
        if (mCur.moveToFirst()) {
            do {
                name[i] = mCur.getString(mCur.getColumnIndex(COLUMN_NAME));
                i++;
            } while (mCur.moveToNext());
        }
        mCur.close();
        close();

        return name;
    }

    public String[] getAllExpenseTypeNames(){
        open();
        Cursor mCur = select(TABLE_NAME_EXPENSE_TYPES, null,
                COLUMN_IS_DELETED + "='N'" + " AND " + COLUMN_NAME + "!='Income'");

        // Populate list view
        int count = mCur.getCount();
        final String []name = new String[count];
        int i = 0;
        if (mCur.moveToFirst()) {
            do {
                name[i] = mCur.getString(mCur.getColumnIndex(COLUMN_NAME));
                i++;
            } while (mCur.moveToNext());
        }
        mCur.close();
        close();

        return name;
    }

    public void insertNewAccount(String account){

        open();
        // Check if it exists
        Cursor mCur = select(TABLE_NAME_ACCOUNTS, null, COLUMN_NAME + "='" + account + "'");
        long id;
        System.out.println(mCur);
        if(mCur.getCount() != 0) {
            System.out.println(mCur.toString());
            if (mCur.moveToFirst()) {
                id = Long.parseLong(mCur.getString(mCur.getColumnIndex(COLUMN_ID)));
                // Prepare Content
                ContentValues content = new ContentValues();
                content.put(COLUMN_NAME, account);
                content.put(COLUMN_IS_DELETED, "N");

                // Update
                update(id, TABLE_NAME_ACCOUNTS, content);
                mCur.close();
            }
        }else {
            // Prepare content
            ContentValues content = new ContentValues();
            content.put(COLUMN_NAME, account);
            content.put(COLUMN_IS_DELETED, "N");

            // Insert into table
            insert(TABLE_NAME_ACCOUNTS, content);
        }

        close();
    }

    public void insertNewExpenseType(String expenseType){
        // Check if it exists
        open();
        Cursor mCur = select(TABLE_NAME_EXPENSE_TYPES, null, COLUMN_NAME + "='" + expenseType + "'");
        long id;
        if(mCur.getCount() != 0) {
            if (mCur.moveToFirst()) {
                id = Long.parseLong(mCur.getString(mCur.getColumnIndex(COLUMN_ID)));
                // Prepare Content
                ContentValues content = new ContentValues();
                content.put(COLUMN_NAME, expenseType);
                content.put(COLUMN_IS_DELETED, "N");

                // Update
                update(id, TABLE_NAME_EXPENSE_TYPES, content);
                mCur.close();
            }
        }else {
            // Prepare content
            ContentValues content = new ContentValues();
            content.put(COLUMN_NAME, expenseType);
            content.put(COLUMN_IS_DELETED, "N");

            // Insert in db using adapter
            insert(TABLE_NAME_EXPENSE_TYPES, content);
        }

        close();
    }

    public void insertNewRecord(String date, double amount, String account, String expenseType,
                                String description){

        open();
        ContentValues content = new ContentValues();
        long accountId = 0;
        long expenseTypeId = 0;

        // Get account ID
        Cursor cur = select(TABLE_NAME_ACCOUNTS, null, COLUMN_NAME + "=\"" + account + "\"");
        if (cur.moveToFirst()) {
            accountId = Long.parseLong(cur.getString(cur.getColumnIndex(COLUMN_ID)));
        }
        // Get expense type ID
        cur = select(TABLE_NAME_EXPENSE_TYPES, null, COLUMN_NAME + "=\"" + expenseType + "\"");
        if (cur.moveToFirst()) {
            expenseTypeId = Long.parseLong(cur.getString(cur.getColumnIndex(COLUMN_ID)));
        }

        content.put(COLUMN_DATE, date);
        content.put(COLUMN_AMOUNT, amount);
        content.put(COLUMN_ACCOUNT, accountId);
        content.put(COLUMN_DESCRIPTION, description);
        content.put(COLUMN_EXPENSE_TYPE, expenseTypeId);

        // Insert values into DB
        insert(TABLE_NAME_RECORDS, content);

        cur.close();
        close();
    }


    public void updateAccountName(String newName, String oldName){
        open();
        Cursor mCur = select(TABLE_NAME_ACCOUNTS, null, COLUMN_NAME + "=\"" + oldName + "\"");

        long id = 0;
        if (mCur.moveToFirst()) {
            do {
                id = Long.parseLong(mCur.getString(mCur.getColumnIndex(COLUMN_ID)));
            } while (mCur.moveToNext());
        }
        // Prepare Content
        ContentValues content = new ContentValues();
        content.put(COLUMN_NAME, newName);
        content.put(COLUMN_IS_DELETED, "N");

        // Update
        update(id, TABLE_NAME_ACCOUNTS, content);

        mCur.close();
        close();
    }

    public void updateExpenseTypeName(String newName, String oldName){
        open();
        Cursor mCur = select(TABLE_NAME_EXPENSE_TYPES, null, COLUMN_NAME + "=\"" + oldName + "\"");

        long id = 0;
        if (mCur.moveToFirst()) {
            do {
                id = Long.parseLong(mCur.getString(mCur.getColumnIndex(COLUMN_ID)));
            } while (mCur.moveToNext());
        }
        // Prepare Content
        ContentValues content = new ContentValues();
        content.put(COLUMN_NAME, newName);
        content.put(COLUMN_IS_DELETED, "N");

        // Update
        update(id, TABLE_NAME_EXPENSE_TYPES, content);

        mCur.close();
        close();
    }

    public void deleteAccount(String account){
        open();
        Cursor mCur = select(TABLE_NAME_ACCOUNTS, null, COLUMN_NAME + "=\"" + account + "\"");
        long id = 0;
        String name = null;
        if (mCur.moveToFirst()) {
            do {
                id = Long.parseLong(mCur.getString(mCur.getColumnIndex(COLUMN_ID)));
                name = mCur.getString(mCur.getColumnIndex(COLUMN_NAME));
            } while (mCur.moveToNext());
        }
        // Prepare content
        ContentValues content = new ContentValues();
        content.put(COLUMN_NAME, name);
        content.put(COLUMN_IS_DELETED, "Y");

        // Update flag to Y
        update(id, TABLE_NAME_ACCOUNTS, content);

        mCur.close();
        close();
    }

    public void deleteExpenseType(String expenseType){
        open();
        Cursor mCur = select(TABLE_NAME_EXPENSE_TYPES, null,
                COLUMN_NAME + "=\"" + expenseType + "\"");
        long id = 0;
        String name = null;
        if (mCur.moveToFirst()) {
            do {
                id = Long.parseLong(mCur.getString(mCur.getColumnIndex(COLUMN_ID)));
                name = mCur.getString(mCur.getColumnIndex(COLUMN_NAME));
            } while (mCur.moveToNext());
        }

        // Prepare content
        ContentValues content = new ContentValues();
        content.put(COLUMN_NAME, name);
        content.put(COLUMN_IS_DELETED, "Y");

        // Update flag to Y
        update(id, TABLE_NAME_EXPENSE_TYPES, content);

        mCur.close();
        close();
    }

    public HeaderInfo getHeaderInfo(){
        HeaderInfo hi = new HeaderInfo();

        open();
        // Get id of Income "expense type"
        long incomeId = 0;
        Cursor cur = select(TABLE_NAME_EXPENSE_TYPES, null, COLUMN_NAME + "=\"Income\"");
        if (cur.moveToFirst()) {
            incomeId = Long.parseLong(cur.getString(cur.getColumnIndex(COLUMN_ID)));
        }
        // Get all records
        cur = select(TABLE_NAME_RECORDS, null, null);
        double income = 0;
        double expense = 0;

        // Count all income, expenses and calculate balance
        if (cur.moveToFirst()) {
            do {
                int expenseType = 0;
                double amount;
                try {
                    expenseType = Integer.parseInt(cur.getString(cur.getColumnIndex(COLUMN_EXPENSE_TYPE)));
                    amount = Double.parseDouble(cur.getString(cur.getColumnIndex(COLUMN_AMOUNT)));
                } catch (Exception e){
                    amount = 0;
                }
                // If expenseType = incomeId, it means that record is an Income, else a expense
                if(expenseType == incomeId){
                    income += amount;
                } else {
                    expense += -1*amount; // Expenses are stored as negative values
                }
            } while (cur.moveToNext());
            hi.setIncome(income);
            hi.setExpense(expense);
            hi.setBalance(income - expense);
        }
        cur.close();
        close();

        return hi;
    }

    public Wrapper upload() {

        open();

        Cursor cursorRecords = select(TABLE_NAME_RECORDS, null, null);
        ArrayList<Record>records = new ArrayList<>();
        if (cursorRecords.moveToFirst()){
            do {
                String date = cursorRecords.getString(cursorRecords.getColumnIndex(COLUMN_DATE));
                double amount = cursorRecords.getDouble(cursorRecords.getColumnIndex(COLUMN_AMOUNT));
                int account = Integer.parseInt(cursorRecords.getString(cursorRecords.getColumnIndex(COLUMN_ACCOUNT)));
                int expenseType = Integer.parseInt(cursorRecords.getString(cursorRecords.getColumnIndex(COLUMN_EXPENSE_TYPE)));
                String description = cursorRecords.getString(cursorRecords.getColumnIndex(COLUMN_DESCRIPTION));
                records.add(new Record(date, amount, account, expenseType, description));
            } while (cursorRecords.moveToNext());
        }
        cursorRecords.close();

        Cursor cursorAccounts = select(TABLE_NAME_ACCOUNTS, null, null);
        ArrayList<Account>accounts = new ArrayList<>();
        if (cursorAccounts.moveToFirst()){
            do {
                int id = cursorAccounts.getInt(cursorAccounts.getColumnIndex(COLUMN_ID));
                String name = cursorAccounts.getString(cursorAccounts.getColumnIndex(COLUMN_NAME));
                String isDeleted = cursorAccounts.getString(cursorAccounts.getColumnIndex(COLUMN_IS_DELETED));
                accounts.add(new Account(id, name, isDeleted));
            } while (cursorAccounts.moveToNext());
        }
        cursorAccounts.close();

        Cursor cursorExpenseTypes = select(TABLE_NAME_EXPENSE_TYPES, null, null);
        ArrayList<ExpenseType>expenseTypes = new ArrayList<>();
        if (cursorExpenseTypes.moveToFirst()){
            do {
                int id = cursorExpenseTypes.getInt(cursorExpenseTypes.getColumnIndex(COLUMN_ID));
                String name = cursorExpenseTypes.getString(cursorExpenseTypes.getColumnIndex(COLUMN_NAME));
                String isDeleted = cursorExpenseTypes.getString(cursorExpenseTypes.getColumnIndex(COLUMN_IS_DELETED));
                expenseTypes.add(new ExpenseType(id, name, isDeleted));
            } while (cursorExpenseTypes.moveToNext());
        }
        close();

        return new Wrapper(records, accounts, expenseTypes);
    }


    public void restore(Wrapper wrapper) {

        open();

        // Delete the contents of all tables...
        delete(TABLE_NAME_RECORDS, null);
        delete(TABLE_NAME_ACCOUNTS, null);
        delete(TABLE_NAME_EXPENSE_TYPES, null);

        // ... And then re-insert them.
        ArrayList<Account> accounts = wrapper.getAccounts();
        for(Account account: accounts) {
            // Prepare content
            ContentValues content = new ContentValues();
            content.put(COLUMN_ID, account.getId());
            content.put(COLUMN_NAME, account.getName());
            content.put(COLUMN_IS_DELETED, account.getIsDeleted());

            // Insert into Account table
            insert(TABLE_NAME_ACCOUNTS,content);
        }

        ArrayList<ExpenseType> expenseTypes = wrapper.getExpenseTypes();
        for(ExpenseType expenseType: expenseTypes) {
            // Prepare content
            ContentValues content = new ContentValues();
            content.put(COLUMN_ID, expenseType.getId());
            content.put(COLUMN_NAME, expenseType.getName());
            content.put(COLUMN_IS_DELETED, expenseType.getIsDeleted());

            // Insert into expense types table
            insert(TABLE_NAME_EXPENSE_TYPES,content);
        }

        ArrayList<Record> records = wrapper.getRecords();
        for(Record record: records) {
            // Prepare content
            ContentValues content = new ContentValues();
            content.put(COLUMN_DATE, record.getDate());
            content.put(COLUMN_AMOUNT, record.getAmount());
            content.put(COLUMN_ACCOUNT, record.getAccountId());
            content.put(COLUMN_EXPENSE_TYPE, record.getExpenseTypeId());
            content.put(COLUMN_DESCRIPTION, record.getDescription());

            // Insert into records table
            insert(TABLE_NAME_RECORDS,content);
        }

    }

}
