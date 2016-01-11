package cmu.finance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import cmu.exception.UiException;
import cmu.adapter.DatabaseAdapter;

/**
 * Created by Pedro on 20/11/2015.
 * FinanceExpenseType Activity. This activity adds an expense type to the DB.
 */
public class FinanceExpenseTypeActivity extends Activity{

    public final static String ID_EXTRA = "cmu.finance._id";
    public final static String NAME_EXTRA = "cmu.finance.name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finance_expense_type_layout);

        // Get data from db using adapter
        DatabaseAdapter da = new DatabaseAdapter(getApplicationContext());
        final String []name = da.getAllExpenseTypeNames();

        try {
            // Build adapter
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_view_table, name);
            // Configure list view
            ListView list = (ListView) findViewById(R.id.expenseTypeListView);
            list.setAdapter(adapter);
            // Set on lick item action
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Redirect to update/delete page
                    Intent intent = new Intent(FinanceExpenseTypeActivity.this, FinanceEditExpenseTypeActivity.class);
                    intent.putExtra(ID_EXTRA, String.valueOf(id));
                    intent.putExtra(NAME_EXTRA, String.valueOf(name[(int) id]));
                    startActivity(intent);
                }
            });
        } catch (Exception e){
            UiException ue = new UiException(8, "Something went wrong", getApplicationContext());
            ue.fix();
        }

        Button addButton = (Button) findViewById(R.id.expenseTypeAddButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(FinanceExpenseTypeActivity.this, FinanceAddExpenseTypeActivity.class);
                    startActivity(intent);
                } catch (Exception e){
                    UiException ue = new UiException(8, "Something went wrong", getApplicationContext());
                    ue.fix();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FinanceExpenseTypeActivity.this, FinanceMainPageActivity.class);
        startActivity(intent);
    }
    
}
