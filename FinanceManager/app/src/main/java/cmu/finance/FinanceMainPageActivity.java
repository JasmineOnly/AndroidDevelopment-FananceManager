package cmu.finance;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import cmu.exception.UiException;
import cmu.adapter.DatabaseAdapter;
import cmu.model.HeaderInfo;


/**
 * Created by Pedro on 06/11/2015.
 * Main activity. Contains all menus, buttons and important info.
 */
public class FinanceMainPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finance_mainpage_layout);

        // Get Main header data using adapter
        DatabaseAdapter da = new DatabaseAdapter(getApplicationContext());
        HeaderInfo header = da.getHeaderInfo();

        //Populate Text Views headers
        TextView tIncome = (TextView) findViewById(R.id.mainIncomeText);
        TextView tExpense = (TextView) findViewById(R.id.mainExpenseText);
        TextView tBalance = (TextView) findViewById(R.id.mainBalanceText);
        tIncome.setText("Income: $" + Double.toString(header.getIncome()));
        tExpense.setText("Expense: $" + Double.toString(header.getExpense()));
        tBalance.setText("Balance: $" + Double.toString(header.getBalance()));

        // Populate View List
        populateViewList();

        findViewById(R.id.addRecordButton).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(FinanceMainPageActivity.this, MultiFragRecord.class);
                FinanceMainPageActivity.this.startActivity(intent);
            }
        });

        findViewById(R.id.accountButton).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(FinanceMainPageActivity.this, FinanceAccountActivity.class);
                FinanceMainPageActivity.this.startActivity(intent);
            }
        });


        findViewById(R.id.expenseTypeButton).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(FinanceMainPageActivity.this, FinanceExpenseTypeActivity.class);
                FinanceMainPageActivity.this.startActivity(intent);
            }
        });

        findViewById(R.id.detailButton).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(FinanceMainPageActivity.this, DetailViewPagerActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    private void populateViewList(){
        ArrayList<HashMap<String, String>> tableList;
        ListView list = (ListView) findViewById(R.id.mainPageListView);

        // Populate custom table using adapter
        DatabaseAdapter da = new DatabaseAdapter(getApplicationContext());
        tableList = da.populateTableView();

        try {
            ListViewAdapter adapter = new ListViewAdapter(this, tableList);
            list.setAdapter(adapter);

            // Set on click item action
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
        } catch (Exception e){
            UiException ue = new UiException(8, "Something went wrong", getApplicationContext());
            ue.fix();
        }
    }



}
