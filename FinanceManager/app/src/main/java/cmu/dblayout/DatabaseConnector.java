package cmu.dblayout;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import java.io.InputStream;
import java.util.Properties;
import cmu.exception.DatabaseException;

/**
 * Created by Pedro on 05/11/2015.
 * DatabaseConnector class. As seen on the lectures with few modifications.
 * Creates SQLite database, tables and makes DAO operations.
 * Exception handling is not implemented here (as planned), since this is a
 * Content Provider, it will provide content as is.
 * Classes invoking this class are responsible to implemented Exception handling.
 */
public class DatabaseConnector implements DatabaseConstants{

    private SQLiteDatabase database;
    private DatabaseOpenHelper databaseOpenHelper;

    public DatabaseConnector(Context context){
        databaseOpenHelper = new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);
    }

    public void open() throws SQLException{
        try {
            database = databaseOpenHelper.getWritableDatabase();
        } catch (Exception e){
            DatabaseException dbe = new DatabaseException(1);
            dbe.fix();
        }
    }

    public void close(){
        try {
            if (database != null)
                database.close();
        } catch (Exception e){
            DatabaseException dbe = new DatabaseException(1);
            dbe.fix();
        }
    }

    /* Modular methods for insert, update, delete and select */
    public void insert(String tableName, ContentValues content){
        try {
            database.insert(tableName, null, content);
        } catch (Exception e){
            DatabaseException dbe = new DatabaseException(2);
            dbe.fix();
        }
    }
    public void update(long id, String tableName, ContentValues content){
        try {
            database.update(tableName, content, COLUMN_ID + "=" + id, null);
        } catch (Exception e){
            DatabaseException dbe = new DatabaseException(2);
            dbe.fix();
        }
    }
    public void delete(String tableName, String selection){
        try {
            database.delete(tableName, selection, null);
        } catch (Exception e){
            DatabaseException dbe = new DatabaseException(2);
            dbe.fix();
        }
    }
    public Cursor select(String tableName, String[] columns, String selection){
        Cursor cur = null;
        try {
            cur = database.query(tableName, columns, selection, null, null, null, null);
        } catch (Exception e){
            DatabaseException dbe = new DatabaseException(3);
            dbe.fix();
        }
        return cur;
    }
    /* */

    private class DatabaseOpenHelper extends SQLiteOpenHelper{
        Context context;

        public DatabaseOpenHelper(Context context, String name, CursorFactory factory, int version){
            super(context, name, factory, version);
            this.context = context;
        }

        // Create the tables once the DB is created.
        @Override
        public void onCreate(SQLiteDatabase db){
            try{
                Properties properties = getProps(context);
                String createUsers = properties.getProperty("createUsers");
                db.execSQL(createUsers);
                String createRecords = properties.getProperty("createRecords");
                db.execSQL(createRecords);
                String createAccounts = properties.getProperty("createAccounts");
                db.execSQL(createAccounts);
                String createExpenseTypes = properties.getProperty("createExpenseTypes");
                db.execSQL(createExpenseTypes);
            } catch (Exception e){
                e.printStackTrace();
                DatabaseException dbe = new DatabaseException(0);
                dbe.fix();
            }
        }


        public Properties getProps(Context context) {
            Properties properties = new Properties();
            InputStream in;
            try {
                Resources resources = context.getResources();
                AssetManager manager = resources.getAssets();
                in = manager.open("SQLStatements.properties");
                //in = new FileInputStream(new File("/assets/SQLStatements.properties"));
                properties.load(in);
            }catch (Exception e) {
                DatabaseException dbe = new DatabaseException(0);
                dbe.fix();
            }

            return properties;
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
