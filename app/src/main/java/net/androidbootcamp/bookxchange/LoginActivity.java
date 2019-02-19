package net.androidbootcamp.bookxchange;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView signUp = (TextView) findViewById(R.id.txtSignUp);
        Button login = (Button)findViewById(R.id.btnLogin);

        signUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LoginActivity.this, BuyActivity.class));
            }
        });
    }
}
