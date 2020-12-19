package com.example.ecommerceappbeen.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.ecommerceappbeen.activities.MainActivity;
import com.example.ecommerceappbeen.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUp extends AppCompatActivity {
    private EditText edtUsername, edtEmail, edtPassword;
    private Button btnSignUp;
    private TextView btnLogIn;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        toolbar = findViewById(R.id.signUpToolbar);
        setSupportActionBar(toolbar);

        Window window = com.example.ecommerceappbeen.auth.SignUp.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(com.example.ecommerceappbeen.auth.SignUp.this, R.color.botom_navigation_bar_background_color));


        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn = findViewById(R.id.btnLogin);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtUsername.getText().toString().equals("") || edtEmail.getText().toString().equals("") || edtPassword.getText().toString().equals("")) {
                    Toast.makeText(com.example.ecommerceappbeen.auth.SignUp.this, "Enter All Values", Toast.LENGTH_LONG).show();
                } else {
                    ParseUser parseUser = new ParseUser();
                    parseUser.put("username", edtUsername.getText().toString());
                    parseUser.put("email", edtEmail.getText().toString());
                    parseUser.put("password", edtPassword.getText().toString());
                    parseUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            Toast.makeText(com.example.ecommerceappbeen.auth.SignUp.this, "Success", Toast.LENGTH_LONG).show();
                            socialMediaActivity();
                        }
                    });
                }
            }
        });
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(com.example.ecommerceappbeen.auth.SignUp.this, com.example.ecommerceappbeen.auth.LogIn.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void socialMediaActivity(){
        Intent intent =new Intent(com.example.ecommerceappbeen.auth.SignUp.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}