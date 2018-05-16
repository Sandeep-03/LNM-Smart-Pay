package com.example.sandeep.lnmsmartpay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String PREFS_NAME = "LoginPrefs";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    String Name,Letter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        final TextView nv_badge = (TextView) headerView.findViewById(R.id.nv_badge);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final TextView badge = (TextView) findViewById(R.id.badge);

        final TextView amount = (TextView) findViewById(R.id.amount);
        Button account = (Button)findViewById(R.id.button);
        Button transaction = (Button)findViewById(R.id.button2);
        Button expenditure  = (Button)findViewById(R.id.button3);
        Button mycards = (Button)findViewById(R.id.button1);
        Button logout = (Button)findViewById(R.id.button4);


        String userId = "";
        if (user != null)
            userId = user.getUid();



        myRef.child("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Personaldetails value = dataSnapshot.getValue(Personaldetails.class);
                amount.setText(String.valueOf(value.getbalance()));
                Name=value.getname();
                Badgeform l = new Badgeform();
                Letter=l.letter(Name);
                badge.setText(Letter);
                nv_badge.setText(Letter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







        account.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, AccountsActivity.class);
                startActivity(intent);
            }
        });
        transaction.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, TransactionActivity.class);
                startActivity(intent);
            }
        });
        expenditure.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ExpenditureActivity.class);
                startActivity(intent);
            }
        });
        mycards.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, Pending_requests.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.remove("logged");
                editor.commit();
                finish();
                Intent i = new Intent(DashboardActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_pi) {
            startActivity(new Intent(DashboardActivity.this, PersonalActivity.class));
        } else if (id == R.id.nav_h) {
            startActivity(new Intent(DashboardActivity.this, DashboardActivity.class));
        } else if (id == R.id.nav_ac) {
            startActivity(new Intent(DashboardActivity.this, AccountsActivity.class));
        } else if (id == R.id.nav_ts) {
            startActivity(new Intent(DashboardActivity.this, TransactionActivity.class));
        }else if (id == R.id.nav_mc) {
            startActivity(new Intent(DashboardActivity.this, Pending_requests.class));
        }else if (id == R.id.nav_ex) {
            startActivity(new Intent(DashboardActivity.this, ExpenditureActivity.class));
        } else if (id == R.id.nav_logout) {
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.remove("logged");
            editor.commit();
            finish();
            Intent i = new Intent(DashboardActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
