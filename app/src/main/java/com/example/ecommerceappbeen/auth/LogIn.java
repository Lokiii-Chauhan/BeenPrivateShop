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
import com.example.ecommerceappbeen.MainActivity;
import com.example.ecommerceappbeen.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LogIn extends AppCompatActivity {

    private EditText edtEmail,edtPassword;
    private Button btnLogIn;
    private TextView btnSignUP,btnForgotPassword;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        toolbar = findViewById(R.id.loginToolbar);
        setSupportActionBar(toolbar);

        if (ParseUser.getCurrentUser()!=null){

            socialMediaActivity();
            finish();
        }

        Window window = com.example.ecommerceappbeen.auth.LogIn.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(com.example.ecommerceappbeen.auth.LogIn.this, R.color.botom_navigation_bar_background_color));

        edtEmail=findViewById(R.id.edtLoginEmail);
        edtPassword=findViewById(R.id.edtLoginPassword);
        btnLogIn=findViewById(R.id.btnLogInLogin);
        btnSignUP=findViewById(R.id.btnLoginSignUp);
        btnForgotPassword = findViewById(R.id.btnForgotPassword);


        btnSignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(com.example.ecommerceappbeen.auth.LogIn.this,SignUp.class);
                startActivity(intent);
            }
        });
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser.logInInBackground(edtEmail.getText().toString(), edtPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                       if (user !=null && e==null){
                           Toast.makeText(getApplicationContext(),"Success", Toast.LENGTH_LONG).show();
                           socialMediaActivity();
                       }else {
                           Toast.makeText(getApplicationContext(),"Failed", Toast.LENGTH_LONG).show();
                       }
                    }
                });
            }
        });


        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ForgotPasswordActivity.class));
            }
        });

    }
    public void socialMediaActivity(){
        Intent intent =new Intent(com.example.ecommerceappbeen.auth.LogIn.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
