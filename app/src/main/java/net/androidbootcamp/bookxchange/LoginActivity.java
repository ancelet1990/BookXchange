package net.androidbootcamp.bookxchange;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity
{
    private EditText loginEmail, loginPassword;
    private Button /*btnRegister,*/ btnLogin;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private String email, password;
    private static final String TAG = "Login to Account";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null)
        {
            startActivity(new Intent(LoginActivity.this, BuyActivity.class));
            finish();
        }

        loginEmail = findViewById(R.id.txtEmailLogin);
        loginPassword = findViewById(R.id.txtLoginPassword);
//        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

//        btnRegister.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
//            }
//        });

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                email = loginEmail.getText().toString();
                password = loginPassword.getText().toString();

                if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(getApplicationContext(), "Enter email address",
                                   Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password))
                {
                    Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT)
                         .show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            progressBar.setVisibility(View.GONE);
                            if (! task.isSuccessful())
                            {
                                // there was an error
                                Log.d(TAG, "auth failed :" + task.getException());
                                if (password.length() < 6)
                                {
                                    loginPassword.setError(getString(R.string.minimum_password));
                                } else
                                {
                                    Toast.makeText(LoginActivity.this,
                                                   getString(R.string.auth_failed),
                                                   Toast.LENGTH_LONG).show();
                                }
                            } else
                            {
                                startActivity(new Intent(LoginActivity.this, BuyActivity.class));
                                finish();
                            }
                        }
                    });
            }
        });
    }
}