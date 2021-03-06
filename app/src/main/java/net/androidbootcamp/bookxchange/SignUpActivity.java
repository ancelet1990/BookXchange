package net.androidbootcamp.bookxchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    private EditText regFirstName, regLastName, regEmail, regPassword, regConfirmPassword;
    private Spinner spSchool;
    private String firstName, lastName, email, school;
    private FirebaseAuth auth;
    private DatabaseReference database;
    private Button btnSignUp;
    private ProgressBar progressBar;
    private static final String TAG = "Create Account";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        //initialize button and progress bar
        btnSignUp = findViewById(R.id.btnSignUp);
        progressBar = findViewById(R.id.progressBar);
        //Initialize edit text fields
        regFirstName = findViewById(R.id.txtFirstName);
        regLastName = findViewById(R.id.txtLastName);
        regEmail = findViewById(R.id.txtEmailAddressReg);
        regPassword = findViewById(R.id.txtRegPassword);
        regConfirmPassword = findViewById(R.id.txtRegPasswordConfirm);
        //initialize spinner
        spSchool = findViewById(R.id.spinnerSchool);

        spSchool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                school = spSchool.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "Please select a school",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        });

        ArrayAdapter<CharSequence> school_adapter =
                ArrayAdapter.createFromResource(this, R.array.school_array, R.layout.spinner_item);
        spSchool.setAdapter(school_adapter);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = regEmail.getText().toString().trim();
                String password = regPassword.getText().toString().trim();
                String passwordConfirm = regConfirmPassword.getText().toString().trim();
                firstName = regFirstName.getText().toString().trim();
                lastName = regLastName.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter your email address",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter a password",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(passwordConfirm)) {
                    Toast.makeText(getApplicationContext(), "Please confirm your password",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(passwordConfirm)) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match",
                            Toast.LENGTH_SHORT).show();
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(firstName)) {
                    Toast.makeText(getApplicationContext(), "Please enter your first name",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(lastName)) {
                    Toast.makeText(getApplicationContext(), "Please enter your last name",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(school)) {
                    Toast.makeText(getApplicationContext(), "Please select a school",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (task.isSuccessful()) {
                                    FirebaseUser currentFirebaseUser = auth.getCurrentUser();
                                    String userid = currentFirebaseUser.getUid();

                                    database = FirebaseDatabase.getInstance().getReference("Users")
                                            .child(userid);

                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("id", userid);
                                    hashMap.put("firstName", firstName);
                                    hashMap.put("lastName", lastName);
                                    hashMap.put("email", email);
                                    hashMap.put("school", school);
                                    hashMap.put("imageURL", "default");
                                    hashMap.put("status", "offline");
                                    hashMap.put("username", firstName + " " + lastName);
                                    hashMap.put("search", firstName.toLowerCase() + " " + lastName.toLowerCase());

                                    Log.d(TAG, "create user completed successfully = " +
                                            task.isSuccessful());
                                    //User user = new User(firstName, lastName, email, school);
                                    Log.d(TAG, "user object created");
                                    //database.child("users").child(currentFirebaseUser.getUid()).setValue(user);
                                    database.setValue(hashMap)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Intent intent = new Intent(SignUpActivity.this,
                                                                MainBuyActivity.class);
                                                        intent.addFlags(
                                                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                                                        Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }
                                            });
                                } else {
                                    Log.d(TAG, "auth failed " + task.getException());
                                    Toast.makeText(SignUpActivity.this,
                                            "Authentication failed." + task.getException(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}