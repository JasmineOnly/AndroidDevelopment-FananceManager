package cmu.model;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Created by qiangwan on 11/21/2015.
 */

/*
 * This class is used to manager the user 
 * and it contains all the users will be sent back to the client 
 * when client asks for the restore
 */
public class UserManager implements Serializable{
	private static final long serialVersionUID = 1L;
	private  ArrayList<User>users = new ArrayList<>();
	public ArrayList<User> getUsers() {
		return users;
	}
	
	
	public boolean checkUser(User user) {
		for(User u: users) {
			if(u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())) {
				return true;
			}
		} 
		return false;
	}
	
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	

	public void addUser(User user) {
		this.users.add(user);
	}


	public boolean checkUserName(User user) {
		for(User u: users) {
			if(u.getUsername().equals(user.getUsername())) {
				return true;
			}
		} 
		return false;
	}
	 
}



//package cmu.adapter;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import Exception.UserExistsException;
//import cmu.model.User;
//
///**
// * Created by qiangwan on 11/21/2015.
// */
//
///*
// * This class is used to manager the user 
// * and it contains all the users will be sent back to the client 
// * when client asks for the restore
// */
//public class UserManager implements Serializable {
//	private static final long serialVersionUID = 1L;
//	private  ArrayList<User>users = new ArrayList<>();
//	public ArrayList<User> getUsers() {
//		return users;
//	}
//	
//	//check the username and password when receive the login request 
//	public boolean checkUser(User user) {
//		for(User u: users) {
//			if(u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())) {
//				return true;
//			}
//		} 
//		return false;
//	}
//	// check the username when receive the registration
//	public boolean checkUserName(User user) {
//		for(User u: users) {
//			if(u.getUsername().equals(user.getUsername())) {
//				return true;
//			}else {
//				new UserExistsException("Use exists!!");
//			}
//		} 
//		return false;
//	}
//}
