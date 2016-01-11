package cmu.model;

import java.io.Serializable;
import java.util.HashSet;


/**
 * Created by qiangwan on 11/10/15.
 * Account class to handle the account object.
 */
public class Account implements Serializable{
    private static final long serialVersionUID = 1L;
    private HashSet<String> accounts;
    private int id;
    private String name;
    private String isDeleted;
    
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsDeleted() {
		return isDeleted;
	}

	public void setName(String name) {
        this.name = name;
    }

    // Constructor
    public Account(){
        accounts = new HashSet<>();
    }

    public Account(int id, String name, String isDeleted) {
        this.id = id;
        this.name = name;
        this.isDeleted = isDeleted;
    }

    public Account(String name, String isDeleted) {
        this.name = name;
        this.isDeleted = isDeleted;
    }

    // get the list of accounts
    public HashSet<String> getAccount() {
        return accounts;
    }

}