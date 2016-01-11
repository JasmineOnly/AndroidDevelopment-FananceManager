package cmu.db;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import cmu.adapter.Wrapper;
import cmu.model.Account;
import cmu.model.ExpenseType;
import cmu.model.Record;
import cmu.model.User;

/*
anthor: qiangwan  ECE Department, Carnegie Mellon University 
email:qiangwan@andrew.cmu.edu
 */
/**
 * this class is used to communicate with database operations
 *
 */
public class DB {
	//get info from the user table
	public ResultSet getUsersInfos() {
		ResultSet rs = null;
		Connection conn = this.getConn();
		Properties props = this.getProps();
		String getUserInfos = props.getProperty("getUserInfos");
		PreparedStatement pstmt = this.getPstatement(conn, getUserInfos);
		try {
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	//get info from the record table
	public ResultSet getAllrecords() {
		ResultSet rs = null;
		Connection conn =  this.getConn();
		Properties props = this.getProps();
		String getRecords =  props.getProperty("getRecords");
		PreparedStatement pstmt = this.getPstatement(conn,getRecords);
		try {
			 rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	// get info from the account table
	public ResultSet getAllAccounts() {
		ResultSet rs = null;
		Connection conn =  this.getConn();
		Properties props = this.getProps();
		String getAccounts =  props.getProperty("getAccounts");
		PreparedStatement pstmt = this.getPstatement(conn,getAccounts);
		try {
			 rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	// get info from the expenseType table
	public ResultSet getAllExpenseTypes() {
		ResultSet rs = null;
		Connection conn =  this.getConn();
		Properties props = this.getProps();
		String getExpenseTypes =  props.getProperty("getExpenseTypes");
		PreparedStatement pstmt = this.getPstatement(conn,getExpenseTypes);
		try {
			 rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	// add the user
	public void add(User user) {
		Connection conn =  this.getConn();
		Properties props = this.getProps();
		String insertUser = props.getProperty("insertUser");
		PreparedStatement pstmt = this.getPstatement(conn, insertUser);
		try {
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// store the records, accounts and expenseTypes from wrapper
	// and store them(delete the original ones before store)
	public void upload(Wrapper wrapper) {
		Connection conn =  this.getConn();
		Properties props = this.getProps();
		String deleteRecords = props.getProperty("deleteRecords");
		String deleteAccounts = props.getProperty("deleteAccounts");
		String deleteExpenseTypes = props.getProperty("deleteExpenseTypes");
		Statement stmt = this.getStatement(conn);
		try {
			stmt.executeUpdate(deleteExpenseTypes);
			stmt.executeUpdate(deleteAccounts);
			stmt.executeUpdate(deleteRecords);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		ArrayList<Account>accounts = wrapper.getAccounts();
		restoreAccounts(accounts);
		ArrayList<ExpenseType>expenseTypes = wrapper.getExpenseTypes();
		restoreExpenseTypes(expenseTypes);
		ArrayList<Record>records = wrapper.getRecords();
		restoreRecord(records);
	}
			
			
	 //restore records in db
	private void restoreRecord(ArrayList<Record> records) {
		Connection conn =  this.getConn();
		Properties props = this.getProps();
		for(Record record :records) {
			String insertRecord = props.getProperty("insertRecord");
			PreparedStatement pstmt = this.getPstatement(conn, insertRecord);
			try {
				
				pstmt.setString(1, record.getDate());
				pstmt.setDouble(2, record.getAmount());
				pstmt.setInt(3, record.getAccountId());
				pstmt.setInt(4, record.getExpenseTypeId());
				pstmt.setString(5, record.getDescription());
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}


	//restore expenseTypes in db
	private void restoreExpenseTypes(ArrayList<ExpenseType> expenseTypes) {
		Connection conn =  this.getConn();
		Properties props = this.getProps();
		for(ExpenseType expenseType : expenseTypes) {
			String inserExpenseType = props.getProperty("insertExpenseType");
			PreparedStatement pstmt = this.getPstatement(conn, inserExpenseType);
			try {
				pstmt.setInt(1, expenseType.getId());
				pstmt.setString(2, expenseType.getName());
				pstmt.setString(3, expenseType.getIsDeleted());
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	// restore accounts in db
	private void restoreAccounts(ArrayList<Account> accounts) {
		Connection conn =  this.getConn();
		Properties props = this.getProps();
		for(Account account : accounts) {
			String insertAccount = props.getProperty("insertAccount");
			PreparedStatement pstmt = this.getPstatement(conn, insertAccount);
			try {
				pstmt.setInt(1,account.getId());
				pstmt.setString(2, account.getName());
				pstmt.setString(3, account.getIsDeleted());
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	// get the connection statement, properties and resultSet	
	public Properties getProps() {
		Properties props =  new Properties();
		FileInputStream in;
		try {
			in = new FileInputStream(new File("./sql/sql.properties"));
			props.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}
	
	public  Connection getConn(){
		 Connection conn = null;
		 try{
			 Class.forName("com.mysql.jdbc.Driver");
			 conn = DriverManager.getConnection("jdbc:mysql://localhost/mydata?" + "user=root&password=shanggan1021");
		 }catch(ClassNotFoundException e){
			 e.printStackTrace();
		 }catch(SQLException e){
			 e.printStackTrace();
		 }
		 return conn;
	 }
	 
	 public  Statement getStatement(Connection conn){
		 Statement stmt = null;
		 try{
			 if(conn != null){
				 stmt = conn.createStatement();
			 }
		 }catch(SQLException e){
			 e.printStackTrace();
		 }
		 return stmt;
	 }
	 
	 public  PreparedStatement getPstatement(Connection conn, String sql){
		 PreparedStatement pstmt = null;
		 try{
			 if(conn != null){
				 pstmt = conn.prepareStatement(sql);
			 }
		 }catch(SQLException e){
			 e.printStackTrace();
		 }
		 return pstmt;
	 }
	 
	 public  ResultSet getResultSet(Connection conn, String sql){
		 Statement stmt = getStatement(conn);
		 ResultSet rs = getResultSet(stmt,sql);
		 close(stmt);
		 return rs;
	 }
	 
	 public  ResultSet getResultSet(Statement stmt, String sql){
		 ResultSet rs = null;
		 try{
			 if(stmt != null){
				 rs = stmt.executeQuery(sql);
		 
			 }
		 }catch(SQLException e){
			e.printStackTrace();
		 }
		 return rs;
	 }
	//close the statement, preparedstatement,, resultSet and connection
	 public  void close(Connection conn){
		 try{
			 if(conn != null){
				 conn.close();
				 conn = null;
			 }
		 }catch(SQLException e){
			 e.printStackTrace();
		 }
	 }
	 
	 public  void close(Statement stmt){
		 try{
			 if(stmt != null){
				 stmt.close();
				 stmt = null;
			 }
		 }catch(SQLException e){
			 e.printStackTrace();
		 }
	 }
	 
	 public void close(PreparedStatement pstmt){
		 try{
			 if(pstmt != null){
				 pstmt.close();
				 pstmt = null;
			 }
		 }catch(SQLException e){
			 e.printStackTrace();
		 }
	 }
	public  void close(ResultSet rs){
		try{
			if(rs != null){
				rs.close();
				rs = null;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}

