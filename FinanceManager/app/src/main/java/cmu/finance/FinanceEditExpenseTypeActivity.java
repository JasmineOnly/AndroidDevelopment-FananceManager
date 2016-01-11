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
 * Created by Pedro on 21/11/2015.
 * EditExpenseType Activity - this activity is reached by clicking an Expense Type under
 * FinanceExpenseTypeActivity. When update or delete is hit, intent redirects you back.
 */
public class FinanceEditExpenseTypeActivity extends Activity {


    private String extra_name = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finance_edit_expense_type_layout);

        // Get extra from previous activity
        extra_name = getIntent().getStringExtra(FinanceAccountActivity.NAME_EXTRA);
        // Set text to text view
        EditText info = (EditText)findViewById(R.id.editExpenseTypeName);
        info.setText(extra_name);

        Button update = (Button) findViewById(R.id.editExpenseTypeUpdateButton);
        Button delete = (Button) findViewById(R.id.editExpenseTypeDeleteButton);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Get new name from UI
                    EditText eNewName = (EditText)findViewById(R.id.editExpenseTypeName);
                    String newName = eNewName.getText().toString();

                    // Update using adapter
                    BuildRecord buildRecord = new BuildRecord();
                    buildRecord.updateExpenseTypeName(newName, extra_name, getApplicationContext());

                    // Success message and go to previous activity
                    Toast.makeText(getApplicationContext(), "Expense Type updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FinanceEditExpenseTypeActivity.this, FinanceExpenseTypeActivity.class);
                    startActivity(intent);

                } catch (Exception e){
                    UiException ue = new UiException(8, "Something went wrong", getApplicationContext());
                    ue.fix();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    // Delete using adapter
                    BuildRecord buildRecord = new BuildRecord();
                    buildRecord.deleteExpenseType(extra_name, getApplicationContext());

                    // Success message and go to previous activity
                    Toast.makeText(getApplicationContext(), "Expense Type deleted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FinanceEditExpenseTypeActivity.this, FinanceExpenseTypeActivity.class);
                    startActivity(intent);

                } catch (Exception e){
                    UiException ue = new UiException(8, "Something went wrong", getApplicationContext());
                    ue.fix();
                }
            }
        });
    }
}
