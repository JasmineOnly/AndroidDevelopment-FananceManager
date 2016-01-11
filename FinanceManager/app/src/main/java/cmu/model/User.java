package cmu.model;


import java.io.Serializable;


/**
 * This is a user entity
 *
 * We have the warning because I have to keep the class same
 */

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private String username;
	private String password;

    public User(int id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}