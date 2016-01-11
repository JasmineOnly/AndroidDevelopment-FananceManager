package cmu.finance;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cmu.model.User;
import cmu.webservices.WebServiceHandler;

/**
 * Created by Pedro on 06/11/2015.
 * Register Actitivy.
 */
public class FinanceRegisterActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finance_register_layout);

        final EditText userNameEidtText = (EditText)findViewById(R.id.inputUserName);
        final EditText passwordEditText = (EditText)findViewById(R.id.inputUserPassword);
        final EditText password2EditText = (EditText)findViewById(R.id.inputUserConfirmUserPassword);
        Button submitButton = (Button) findViewById(R.id.submitRegisterButton);

        submitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String username = userNameEidtText.getText().toString();
                    String password = passwordEditText.getText().toString();
                    String password2 = password2EditText.getText().toString();if(password == "")
                    if(password == "") {
                        Toast.makeText(FinanceRegisterActivity.this, "password can't be empty!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(!password.equals(password2)) {
                        Toast.makeText(getApplicationContext(), "Password mismatch", Toast.LENGTH_SHORT).show();
                    }else {
                        if (!hasUserName(username, password)) {
                            Toast.makeText(getApplicationContext(), "Register successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), FinanceLoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(FinanceRegisterActivity.this, "Account or password is invalid", Toast.LENGTH_SHORT).show();
                        }
                    }

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
            User user = new User(account, password);
            WebServiceHandler webServiceHandler = new WebServiceHandler();
            return webServiceHandler.doesExist(user);
        }
    }

    private boolean  hasUserName(String userName, String password){
        try {
            MyTask1 task = new MyTask1(userName, password);
            return task.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
