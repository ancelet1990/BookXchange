package net.androidbootcamp.bookxchange;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {

    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Button btnSubmit = findViewById(R.id.btnSubmitNewPW);
        final EditText txtChangePW = findViewById(R.id.txtChangePW);
        EditText txtConfirmChangePW = findViewById(R.id.txtConfirmChangePW);
        progressBar = findViewById(R.id.progressBar);

        user = FirebaseAuth.getInstance().getCurrentUser();
        auth = FirebaseAuth.getInstance();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (user != null && !txtChangePW.getText().toString().trim().equals("")) {
                    if (txtChangePW.getText().toString().trim().length() < 6) {
                        txtChangePW.setError("Password too short, enter minimum 6 characters");
                        progressBar.setVisibility(View.GONE);
                    } else {
                        user.updatePassword(txtChangePW.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(ChangePassword.this, "Password is updated, sign in with new password!", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                            
                                        } else {
                                            Toast.makeText(ChangePassword.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                    }
                } else if (txtChangePW.getText().toString().trim().equals("")) {
                    txtChangePW.setError("Enter password");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
