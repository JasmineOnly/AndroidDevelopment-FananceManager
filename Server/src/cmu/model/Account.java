package cmu.model;


import java.io.Serializable;
import java.util.HashSet;

/**
 * Created by qiangwan on 11/10/15.
 */

/*
 * This is account entity
 */
public class Account implements Serializable{
	private static final long serialVersionUID = 1L;
	private HashSet<String> accounts;
    private int id;
	private String name;
    private String isDeleted;
    
    //get
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

    public String getName() {
        return name;
    }

    public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setName(String name) {
        this.name = name;
    }

    // Constructor
    public Account(){
        accounts = new HashSet<>();
    }

    public Account(String name, String isDeleted) {
        this.name = name;
        this.isDeleted = isDeleted;
    }
    
    public Account(int id, String name, String isDeleted) {
        this.id = id;
    	this.name = name;
        this.isDeleted = isDeleted;
    }

    // get the list of accouSnts
    public HashSet<String> getAccount() {
        return accounts;
    }

    // add an account to accounts
    public void addAccount(String name){
        accounts.add(name);
    }

    // delete an account
    public void deleteAccount(String account){
        for (String a : accounts){
            if (a.equals(account)){
                accounts.remove(account);
            }
        }
    }

    // update an account
    public void updateAccount(String account, String newAccount){
        for (String a : accounts){
            if (a.equals(account)){
                accounts.remove(a);
                accounts.add(newAccount);
            }
        }
    }
}
