package com.example.ecommerceappbeen.auth;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.ecommerceappbeen.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText edtResetPasswordEmail;
    private Button btnResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        edtResetPasswordEmail = findViewById(R.id.edtResetPasswordEmail);
        btnResetPassword = findViewById(R.id.btnResetPassword);


        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordReset();
            }
        });


    }

    public void passwordReset() {
        // An e-mail will be sent with further instructions
        ParseUser.requestPasswordResetInBackground(edtResetPasswordEmail.getText().toString(), new RequestPasswordResetCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext()," An Confirmation Email Sent To Provided Email id Please Reset Your Password From Their. ",Toast.LENGTH_LONG).show();
                } else {
                    // Something went wrong. Look at the ParseException to see what's up.
                }
            }
        });
    }

}