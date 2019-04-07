package net.androidbootcamp.bookxchange;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity
{
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Button btnSubmit = findViewById(R.id.btnSubmitNewPW);
        final EditText txtChangePW = findViewById(R.id.txtChangePW);
        final EditText txtConfirmChangePW = findViewById(R.id.txtConfirmChangePW);

        user = FirebaseAuth.getInstance().getCurrentUser();

        btnSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String newPassword = txtChangePW.getText().toString().trim();
                String newPasswordConfirm = txtConfirmChangePW.getText().toString().trim();

                if (TextUtils.isEmpty(newPassword))
                {
                    Toast.makeText(getApplicationContext(), "Please enter a password",
                                   Toast.LENGTH_LONG).show();
                }
                if (newPassword.length() < 6)
                {
                    Toast.makeText(getApplicationContext(),
                                   "Password needs to be at least 6 characters long",
                                   Toast.LENGTH_LONG).show();
                }
                if (TextUtils.isEmpty(newPasswordConfirm))
                {
                    Toast.makeText(getApplicationContext(), "Please confirm new password",
                                   Toast.LENGTH_LONG).show();
                }
                if (! newPassword.equals(newPasswordConfirm))
                {
                    Toast.makeText(getApplicationContext(), "Passwords do not match",
                                   Toast.LENGTH_LONG).show();
                }

                user = FirebaseAuth.getInstance().getCurrentUser();

                user.updatePassword(newPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(ChangePassword.this, "Password is updated!",
                                               Toast.LENGTH_SHORT).show();
                                startActivity(
                                        new Intent(ChangePassword.this, AccountActivity.class));
                            } else
                            {
                                Toast.makeText(ChangePassword.this, "Failed to update password!",
                                               Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.account:
                Intent intent = new Intent(this, AccountActivity.class);
                this.startActivity(intent);
                break;
            case R.id.sell_books:
                Intent intent1 = new Intent(this, SellActivity.class);
                this.startActivity(intent1);
                break;
            case R.id.buy_books:
                Intent intent2 = new Intent(this, BuyActivity.class);
                this.startActivity(intent2);
                break;
            case R.id.messages:
                Intent intent3 = new Intent(this, MessageActivity.class);
                this.startActivity(intent3);
                break;
            case R.id.posts:
                Intent intent4 = new Intent(this, PostActivity.class);
                this.startActivity(intent4);
                break;
            case R.id.help:
                Intent intent5 = new Intent(this, HelpActivity.class);
                this.startActivity(intent5);
                break;
            case R.id.about:
                Intent intent6 = new Intent(this, AboutActivity.class);
                this.startActivity(intent6);
                break;
        }
        return true;
    }
}
