package com.example.sandeep.lnmsmartpay;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangepasswordActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    public EditText oldpassword;
    public EditText newPassword;
    public EditText newPassword1;
    public Button changepassword;
    public ProgressBar progressBar;
    public String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        progressBar = (ProgressBar)findViewById(R.id.progressBar) ;
        oldpassword=(EditText)findViewById(R.id.password);
        newPassword=(EditText)findViewById(R.id.newPassword);
        newPassword1=(EditText)findViewById(R.id.newPassword1);
        changepassword=(Button)findViewById(R.id.changepassword);

        changepassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                String userId = "";
                if (user != null)
                    userId = user.getUid();


                user.updatePassword(newPassword.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ChangepasswordActivity.this, "Password is updated!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ChangepasswordActivity.this,PersonalActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(ChangepasswordActivity.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
            }
        });

    }
}
