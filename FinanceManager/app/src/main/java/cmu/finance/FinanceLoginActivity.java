package cmu.finance;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;



import cmu.adapter.DatabaseAdapter;
import cmu.model.User;
import cmu.webservices.WebServiceHandler;

/**
 * Created by Pedro on 06/11/2015.
 * Login activity. This is only seen once, the first time the user logs in.
 */
public class FinanceLoginActivity extends Activity {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText accountEdit;
    private EditText passwordEdit;
    private CheckBox rememberPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finance_login_layout);


        pref = PreferenceManager.getDefaultSharedPreferences(this);
        accountEdit = (EditText) findViewById(R.id.account);
        passwordEdit = (EditText) findViewById(R.id.password);
        rememberPass = (CheckBox) findViewById(R.id.remember_pass);
        Button login;
        login = (Button) findViewById(R.id.login);
        boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember) {
            String account = pref.getString("account", "");
            String password = pref.getString("password", "");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);

            Intent intent = new Intent(FinanceLoginActivity.this, FinanceMainPageActivity.class);
            startActivity(intent);
        }

        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                if(correctLogin(account, password)){
                    editor = pref.edit();
                    if(rememberPass.isChecked()) {
                        editor.putBoolean("remember_password", true);
                        editor.putString("account", account);
                        editor.putString("password", password);
                    }else {
                        editor.clear();
                    }
                    editor.commit();

                    // Create default accounts and expense types
                    DatabaseAdapter da = new DatabaseAdapter(getApplicationContext());
                    da.insertNewAccount("Cash");
                    da.insertNewExpenseType("Food");
                    da.insertNewExpenseType("Income");

                    // Success message and redirect to Main activity
                    Toast.makeText(getApplicationContext(),"Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), FinanceMainPageActivity.class);
                    startActivity(intent);
                }
                 else {
                    Toast.makeText(FinanceLoginActivity.this, "account or password is invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button registerButton =
                (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(FinanceLoginActivity.this, FinanceRegisterActivity.class);
                    startActivity(intent);
                } catch (Exception e){
                    System.out.println(e.toString());
                }
            }
        });

    }

    private class MyTask1 extends AsyncTask<Void, Void, Boolean> {

        private String account;
        private String password;

        public MyTask1(String account, String password) {
            this.account = account;
            this.password = password;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            User user= new User(account, password);
            WebServiceHandler webServiceHandler = new WebServiceHandler();
            return  webServiceHandler.check(user);
        }


    }


    private boolean correctLogin(String userName, String password){
        try {
            MyTask1 task = new MyTask1(userName, password);
            return task.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
