package cmu.finance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import cmu.exception.UiException;

/**
 * Created by Pedro on 09/11/2015.
 * Container Activity for the add record functionality.
 */
public class MultiFragRecord extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multi_frag_record_layout);

        try {
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.menuRecordContainer, new MenuRecordFragment()).commit();
            }
        } catch (Exception e){
            UiException ue = new UiException(8, "Something went wrong", getApplicationContext());
            ue.fix();
        }
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(getApplicationContext(), FinanceMainPageActivity.class);
        startActivity(startMain);
    }
}
