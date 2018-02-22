package com.example.nicolo.learningbackendless;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class LoginActivity extends AppCompatActivity {
    private TextView createAccount,loginButton;
    private EditText usernameText, passwordText;
    private ProgressBar loadingBar;
    public static int REGISTRATION_REQUEST=42;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        wireWidgets();
        Backendless.initApp( this, BackendSettings.APP_ID, BackendSettings.API_KEY );

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentThing();

            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBar.setVisibility(View.VISIBLE);
                Backendless.UserService.login(usernameText.getText().toString(), passwordText.getText().toString(), new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {

                        String user=(String)response.getProperty("username");
                        Toast.makeText(LoginActivity.this, user+" has logged on", Toast.LENGTH_SHORT).show();

                        loadingBar.setVisibility((View.GONE));
                        testDataRetrieval();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(LoginActivity.this, fault.getDetail(), Toast.LENGTH_SHORT).show();
                        loadingBar.setVisibility((View.GONE));


                    }
                });
            }
        });

    }

    private void testDataRetrieval() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK && requestCode==REGISTRATION_REQUEST){
            usernameText.setText(data.getStringExtra(Registration.EXTRA_USERNAME));
        }
    }

    private void intentThing() {
        Intent i=new Intent(this, Registration.class);
        startActivityForResult(i,REGISTRATION_REQUEST);
    }

    private void wireWidgets() {
        createAccount=findViewById(R.id.create_account);
        usernameText=findViewById(R.id.view_username);
        passwordText=findViewById(R.id.view_password);
        loginButton=findViewById(R.id.login);
        loadingBar=findViewById(R.id.loadingBar);
        loadingBar.setVisibility(View.GONE);


    }







}
