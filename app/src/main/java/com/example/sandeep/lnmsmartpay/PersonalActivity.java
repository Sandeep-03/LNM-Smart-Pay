package com.example.sandeep.lnmsmartpay;

import android.content.Intent;
import android.nfc.Tag;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PersonalActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    FirebaseAuth auth;
    String Name,Letter;
    public TextView email,badge;
    public TextView username;
    public TextView name;
    public TextView phn_no;
    public TextView balance;
    public TextView changepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        changepassword=(TextView)findViewById(R.id.changepassword);
        email = (TextView) findViewById(R.id.email);
        name = (TextView) findViewById(R.id.name);
        phn_no = (TextView) findViewById(R.id.phn_no);
        username = (TextView) findViewById(R.id.user);
        balance = (TextView) findViewById(R.id.balance);
        badge = (TextView) findViewById(R.id.badge);

        String userId = "";
        if (user != null)
            userId = user.getUid();


        auth = FirebaseAuth.getInstance();








        myRef.child("users").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Personaldetails value = dataSnapshot.getValue(Personaldetails.class);
                email.setText(value.getemail());
                name.setText(value.getname());
                phn_no.setText(value.getphn_number().toString());
                balance.setText(value.getbalance().toString());
                username.setText(value.getusername());
                Name=value.getname();
                Badgeform l = new Badgeform();
                Letter=l.letter(Name);
                badge.setText(Letter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        changepassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, ChangepasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(PersonalActivity.this, DashboardActivity.class));
        finish();

    }
}
