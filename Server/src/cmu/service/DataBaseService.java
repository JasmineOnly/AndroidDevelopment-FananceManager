package cmu.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cmu.adapter.UserManager;
import cmu.adapter.Wrapper;
import cmu.db.DB;
import cmu.model.Account;
import cmu.model.ExpenseType;
import cmu.model.Record;
import cmu.model.User;

/**
 * 
 *  This class is used to offer some database services
 *    
 *
 */
public class DataBaseService {
	public UserManager getUserManager() {
		UserManager userManager = new UserManager();
		DB db = new DB();
		ResultSet rsUserRecords = db.getUsersInfos();
		try{ 
			while (rsUserRecords.next()) {
			int id = rsUserRecords.getInt(1);
			String username = rsUserRecords.getString(2);
			String password = rsUserRecords.getString(3);
			userManager.addUser(new User(id, username, password));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return userManager;
	}
	
	// get the records, accounts, expenseTypes from db
	// to form a wrapper
	public Wrapper restore() {
		DB db = new DB();
		ResultSet rsRecords = db.getAllrecords();
		ArrayList<Record>records = new ArrayList<Record>();
		try {
				while(rsRecords.next()) {
					String date = rsRecords.getString(1);
		            double amount = rsRecords.getDouble(2);
		            int accountId = rsRecords.getInt(3);
					int expenseTypeId = rsRecords.getInt(4);
		            String description =rsRecords.getString(5);
				    records.add(new Record(date, amount, accountId, expenseTypeId, description ));
				}
		 } catch (SQLException e) {
				e.printStackTrace();
		}
		
		ResultSet rsAccounts = db.getAllAccounts();
		ArrayList<Account>accounts = new ArrayList<Account>();
		try {
				while(rsAccounts.next()) {
					int id = rsAccounts.getInt(1);
					String name = rsAccounts.getString(2);
					String isDeleted = rsAccounts.getString(3);
		            accounts.add(new Account(id, name, isDeleted));
				}
		 } catch (SQLException e) {
				e.printStackTrace();
		}
		
		
		ResultSet rsExpenseTypes = db.getAllExpenseTypes();
		ArrayList<ExpenseType>expenseTypes = new ArrayList<ExpenseType>();
		try {
				while(rsExpenseTypes.next()) {
					int id = rsExpenseTypes.getInt(1);
					String name = rsExpenseTypes.getString(2);
					String isDeleted = rsExpenseTypes.getString(3);
					expenseTypes.add(new ExpenseType(id,name, isDeleted));
				}
		 } catch (SQLException e) {
				e.printStackTrace();
		}
		return new Wrapper(records, accounts, expenseTypes);
	}
	
	// store the wrapper
	public void upload(Wrapper wrapper) {
		DB db = new DB();
		db.upload(wrapper);
	}

	public void add(User user) {
		DB db = new DB();
		db.add(user);
	}
}
