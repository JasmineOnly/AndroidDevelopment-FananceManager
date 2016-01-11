package cmu.model;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Created by qiangwan on 11/10/15.
 */

/**
 * 
 * this is the expenseT
 *
 */
public class ExpenseType implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private HashSet<String> expenseTypes;
    private String name;
    private String isDeleted;
    
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // constructor
    public ExpenseType() {
        expenseTypes = new HashSet<String>();
    }

    public ExpenseType(String name, String isDeleted) {
        this.name = name;
        this.isDeleted = isDeleted;
    }
    
    public ExpenseType(int id, String name, String isDeleted) {   
        this.id = id;
    	this.name = name;                                 
        this.isDeleted = isDeleted;                       
        
    }      
    
    // get the list of expense types
    public HashSet<String> getExpenseType() {
        return expenseTypes;
    }

    // add an expense type
    public void addExpenseType(String type){
        expenseTypes.add(type);
    }

    // delete an expense type
    public void deleteExpenseType(String type){
        for (String t:expenseTypes){
            if(t.equals(type)){
                expenseTypes.remove(t);
            }
        }
    }

    // update an expense type
    public void updateExpenseType(String type,String newType){
        for (String t : expenseTypes){
            if(t.equals(type)){
                expenseTypes.remove(t);
                addExpenseType(newType);
            }
        }
    }

}
