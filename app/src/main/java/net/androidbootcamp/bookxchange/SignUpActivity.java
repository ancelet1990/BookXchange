package net.androidbootcamp.bookxchange;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button signUp= findViewById(R.id.btnSignUp);
        signUp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            startActivity(new Intent(SignUpActivity.this, BuyActivity.class));
        }
    });
    }
}
