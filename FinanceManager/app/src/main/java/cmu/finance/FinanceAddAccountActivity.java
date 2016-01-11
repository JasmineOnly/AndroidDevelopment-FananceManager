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
 * FinanceAddAccount Activity. This activity adds an account to the DB.
 */
public class FinanceAddAccountActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finance_add_account_layout);

        Button addButton = (Button) findViewById(R.id.addAccountButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    // Get values of UI
                    EditText eName = (EditText) findViewById(R.id.addAccountName);
                    String account = eName.getText().toString();

                    // Insert into DB
                    /*
                    DatabaseAdapter da = new DatabaseAdapter(getApplicationContext());
                    da.insertNewAccount(account);
                    */
                    BuildRecord buildRecord = new BuildRecord();
                    buildRecord.insertNewAccount(account, getApplicationContext());

                    // Toast message
                    Toast.makeText(getApplicationContext(), "Account added", Toast.LENGTH_SHORT).show();

                    // Go back to Main activity (or previous activity, maybe)
                    Intent intent = new Intent(FinanceAddAccountActivity.this, FinanceAccountActivity.class);
                    startActivity(intent);

                } catch (Exception e){
                    e.printStackTrace();
                    UiException ue = new UiException(8, "All values should be input", getApplicationContext());
                    ue.fix();
                }
            }
        });

    }
}
