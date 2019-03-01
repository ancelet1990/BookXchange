package net.androidbootcamp.bookxchange;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountActivity extends AppCompatActivity
{
    private FirebaseUser user;
    private TextView txtAccountName, txtAccountEmail, txtAccountSchool;
    private String firstName, lastName, email, school;
    Button btnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        txtAccountName = findViewById(R.id.txtAccountName);
        txtAccountEmail = findViewById(R.id.txtAccountEmail);
        txtAccountSchool = findViewById(R.id.txtAccountSchool);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user information
                        User user = dataSnapshot.getValue(User.class);
                        String firstName = user.firstName;
                        String lastName = user.lastName;
                        String email = user.email;
                        String school = user.school;
                        String fullName = firstName + " " + lastName;

                        txtAccountName.setText(fullName);
                        txtAccountEmail.setText(email);
                        txtAccountSchool.setText(school);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
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
            case R.id.log_out:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
        return true;
    }
}
