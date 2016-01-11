package cmu.finance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cmu.adapter.BuildRecord;
import cmu.exception.UiException;
import cmu.adapter.DatabaseAdapter;

/**
 * Created by Pedro on 20/11/2015.
 * FinanceAddExpenseType Activity. This activity adds an expense type to the DB.
 */
public class FinanceAddExpenseTypeActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finance_add_expense_type_layout);

        Button addButton = (Button) findViewById(R.id.addExpenseTypeButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    // Get values of UI
                    EditText eName = (EditText) findViewById(R.id.addExpenseTypeName);

                    String expenseType = eName.getText().toString();


                    // Insert in db using adapter
                    BuildRecord buildRecord = new BuildRecord();
                    buildRecord.insertNewExpenseType(expenseType, getApplicationContext());

                    // Toast message
                    Toast.makeText(getApplicationContext(), "Expense Type added", Toast.LENGTH_SHORT).show();

                    // Go back to Main activity (or previous activity, maybe)
                    Intent intent = new Intent(FinanceAddExpenseTypeActivity.this, FinanceExpenseTypeActivity.class);
                    startActivity(intent);

                } catch (Exception e){
                    UiException ue = new UiException(8, "All values should be input", getApplicationContext());
                    ue.fix();
                }
            }
        });

    }
}
