package cmu.model;

import java.io.Serializable;
import java.util.HashSet;

import cmu.exception.ModelException;

/**
 * Created by qiangwan on 11/10/15.
 * Expense type class to handle the expense type object.
 *
 */
public class ExpenseType implements Serializable {
    private static final long serialVersionUID = 1L;
    private HashSet<String> expenseTypes;
    private int id;
    private String name;
    private String isDeleted;
    
    public String getIsDeleted() {
		return isDeleted;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public ExpenseType(int id,String name, String isDeleted) {
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
        try {
            for (String t : expenseTypes) {
                if (t.equals(type)) {
                    expenseTypes.remove(t);
                }
            }
        } catch (Exception e){
            ModelException me = new ModelException(5);
            me.fix();
        }
    }

    // update an expense type
    public void updateExpenseType(String type,String newType){
        try {
            for (String t : expenseTypes) {
                if (t.equals(type)) {
                    expenseTypes.remove(t);
                    addExpenseType(newType);
                }
            }
        } catch (Exception e){
            ModelException me = new ModelException(5);
            me.fix();
        }
    }

}
