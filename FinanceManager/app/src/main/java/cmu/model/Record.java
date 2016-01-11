package cmu.model;


import java.io.Serializable;

/**
 * Created by Qiangwan on 11/10/15.
 * Record class to handle the record object.
 * We have the warning because I have to keep the class same
 */
public class Record implements Serializable {
    private static final long serialVersionUID = 1L;
    private String date;
    private Double amount;
    private int accountId;
    private int expenseTypeId;
    private String description;


    public Record(String date, Double amount, int accountId, int expenseTypeId, String description) {
        this.date = date;
        this.amount = amount;
        this.accountId = accountId;
        this.expenseTypeId = expenseTypeId;
        this.description = description;
    }

    // Getter and Setter for date
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Getter and Setter for amount
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getExpenseTypeId() {
		return expenseTypeId;
	}

	public void setExpenseTypeId(int expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}

}
