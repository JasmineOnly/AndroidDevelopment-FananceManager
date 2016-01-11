package cmu.model;

/**
 * Created by Pedro on 02/12/2015.
 * HeaderInfo class is an encapsulation of income, expense and balance.
 * Used to display data on Main Activity. Populated through Database Adapter.
 */
public class HeaderInfo {

    private double income;
    private double expense;
    private double balance;

    public HeaderInfo(){
        this.income = 0;
        this.expense = 0;
        this.balance = 0;
    }

    public double getIncome() {
        Double roundOff = (double) Math.round(income*100.0)/100.0;
        return roundOff;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getExpense() {
        Double roundOff = (double) Math.round(expense*100.0)/100.0;
        return roundOff;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public double getBalance() {
        Double roundOff = (double) Math.round(balance*100.0)/100.0;
        return roundOff;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
