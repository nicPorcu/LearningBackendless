package com.example.nicolo.learningbackendless;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.social.SocialLoginDialog;

public class Registration extends AppCompatActivity {
    private EditText firstname, lastname, password, confirmpassword, email, username;
    private Button registerButton;
    public static final String EXTRA_USERNAME="username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        wireWidgets();

    }




    private void wireWidgets() {
        setContentView(R.layout.activity_registration);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        confirmpassword=findViewById(R.id.confirm_password);
        firstname=findViewById(R.id.firstName);
        lastname=findViewById(R.id.lastName);
        email=findViewById(R.id.email);
        registerButton=findViewById(R.id.registratiom_Button);
    }
    public void registerOnClick(View view){

        final BackendlessUser user=new BackendlessUser();

        if(!password.getText().toString().equals(confirmpassword.getText().toString())){
            Toast.makeText(this, "Please confirm your password", Toast.LENGTH_SHORT).show();
        }
        else {

            user.setProperty("username", username.getText().toString());
            user.setProperty("email", email.getText().toString());
            user.setProperty("firstname", username.getText().toString());
            user.setProperty("lastname", username.getText().toString());
            user.setPassword(password.getText().toString());
            Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser response) {
                    Log.d("", "handleResponse: succsess");
                    Toast.makeText(Registration.this, "sucsess", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent();
                    i.putExtra(EXTRA_USERNAME,username.getText().toString());
                    setResult(RESULT_OK, i);
                    finish();

                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(Registration.this, "failure " + fault.getDetail(), Toast.LENGTH_SHORT).show();
                    Log.d("", "handleFault: " + fault.getDetail());



                }
            });
        }

    }
}
