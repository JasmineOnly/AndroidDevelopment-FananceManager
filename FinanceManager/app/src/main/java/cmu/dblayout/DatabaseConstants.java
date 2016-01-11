package cmu.dblayout;

/**
 * Created by Pedro on 27/11/2015.
 * Constants used in Database tier.
 * This interface is used to avoid hardcoding all over the codes and maintain an unified class where
 * constant values are defined throughout the database tier.
 * We dont have modifier because it will have warning
 */
public interface DatabaseConstants {
    // Name of database
    String DATABASE_NAME = "FinanceManager";

    // Names of tables
     String TABLE_NAME_RECORDS = "RECORDS";
     String TABLE_NAME_ACCOUNTS = "ACCOUNTS";
     String TABLE_NAME_EXPENSE_TYPES = "EXPENSE_TYPES";

    // Names of columns
     String COLUMN_ID = "_id";
     String COLUMN_NAME = "name";
     String COLUMN_IS_DELETED = "isDeleted";

     String COLUMN_DATE = "date";
     String COLUMN_AMOUNT = "amount";
     String COLUMN_ACCOUNT = "account";
     String COLUMN_DESCRIPTION = "description";
     String COLUMN_EXPENSE_TYPE = "expenseType";
}
