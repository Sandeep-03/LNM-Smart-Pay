package com.example.sandeep.lnmsmartpay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;


import java.util.ArrayList;
import java.util.List;

import static com.example.sandeep.lnmsmartpay.R.id.s_r;

public class ExpenditureActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button view;
    public static final String PREFS_NAME = "LoginPrefs";
    private TextView amount,badge;
    String Name,Letter;
    long x,i;
    int p=0,q=0,s=0;
    GraphView graphView,graph1,graph2;
    LineGraphSeries<DataPoint> series = new LineGraphSeries();
    LineGraphSeries<DataPoint> series1 = new LineGraphSeries();
    LineGraphSeries<DataPoint> series2 = new LineGraphSeries();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenditure);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        final TextView nv_badge = (TextView) headerView.findViewById(R.id.nv_badge);
        badge = (TextView) findViewById(R.id.badge);





        amount=(TextView)findViewById(R.id.amount);
        String userId = "";
        if (user != null)
            userId = user.getUid();
        final String finalUserId = userId;

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(ExpenditureActivity.this, "Amount:"+dataPoint.getY()+" Rupees", Toast.LENGTH_SHORT).show();}
        });
        series.setTitle("Random Curve 1");
        series.setColor(Color.parseColor("#FF27C444"));
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(15);
        series.setThickness(8);


        series1.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(ExpenditureActivity.this, "Amount:"+dataPoint.getY()+" Rupees", Toast.LENGTH_SHORT).show();}
        });
        series1.setTitle("Random Curve 1");
        series1.setColor(Color.parseColor("#FFE44410"));
        series1.setDrawDataPoints(true);
        series1.setDataPointsRadius(15);
        series1.setThickness(8);


        series2.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(ExpenditureActivity.this, "Amount:"+dataPoint.getY()+" Rupees", Toast.LENGTH_SHORT).show();}
        });
        series2.setTitle("Random Curve 1");
        series2.setColor(Color.parseColor("#FFEE2DEB"));
        series2.setDrawDataPoints(true);
        series2.setDataPointsRadius(15);
        series2.setThickness(8);


         graphView = (GraphView) findViewById(R.id.graph);
        GridLabelRenderer gridLabel = graphView.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Transactions");
        gridLabel.setVerticalAxisTitle("Amount");
        gridLabel.setLabelsSpace(10);
        gridLabel.setHorizontalAxisTitleColor(Color.parseColor("#FF1307F1"));
        gridLabel.setVerticalAxisTitleColor(Color.parseColor("#FF1307F1"));
        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScalableY(true);
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setScrollableY(true);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMaxY(100);
        graphView.getViewport().setMinY(-100);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMaxX(20);
        graphView.getViewport().setMinX(0);
        graphView.setTitle("Money Received till now");
        graphView.setTitleTextSize(60);
        graphView.setTitleColor(Color.parseColor("#FFE3321E"));

        graph1 = (GraphView) findViewById(R.id.graph1);
        GridLabelRenderer gridLabel1 = graph1.getGridLabelRenderer();
        gridLabel1.setHorizontalAxisTitle("Transactions");
        gridLabel1.setVerticalAxisTitle("Amount");
        gridLabel1.setLabelsSpace(10);
        gridLabel1.setHorizontalAxisTitleColor(Color.parseColor("#FF1307F1"));
        gridLabel1.setVerticalAxisTitleColor(Color.parseColor("#FF1307F1"));
        graph1.getViewport().setScalable(true);
        graph1.getViewport().setScalableY(true);
        graph1.getViewport().setScrollable(true);
        graph1.getViewport().setScrollableY(true);
        graph1.getViewport().setYAxisBoundsManual(true);
        graph1.getViewport().setMaxY(100);
        graph1.getViewport().setMinY(-100);
        graph1.getViewport().setXAxisBoundsManual(true);
        graph1.getViewport().setMaxX(20);
        graph1.getViewport().setMinX(0);
        graph1.setTitle("Money Sent till now");
        graph1.setTitleTextSize(60);
        graph1.setTitleColor(Color.parseColor("#FFE3321E"));


        graph2 = (GraphView) findViewById(R.id.graph2);
        GridLabelRenderer gridLabel2 = graph2.getGridLabelRenderer();
        gridLabel2.setHorizontalAxisTitle("Transactions");
        gridLabel2.setVerticalAxisTitle("Amount");
        gridLabel2.setLabelsSpace(10);
        gridLabel2.setHorizontalAxisTitleColor(Color.parseColor("#FF1307F1"));
        gridLabel2.setVerticalAxisTitleColor(Color.parseColor("#FF1307F1"));
        graph2.getViewport().setScalable(true);
        graph2.getViewport().setScalableY(true);
        graph2.getViewport().setScrollable(true);
        graph2.getViewport().setScrollableY(true);
        graph2.getViewport().setYAxisBoundsManual(true);
        graph2.getViewport().setMaxY(100);
        graph2.getViewport().setMinY(-100);
        graph2.getViewport().setXAxisBoundsManual(true);
        graph2.getViewport().setMaxX(20);
        graph2.getViewport().setMinX(0);
        graph2.setTitle("All Transactions");
        graph2.setTitleTextSize(60);
        graph2.setTitleColor(Color.parseColor("#FFE3321E"));





        myRef.child("users").child(finalUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Personaldetails value = dataSnapshot.getValue(Personaldetails.class);
                x = value.getTra_num();
                amount.setText(value.getbalance().toString());
                Name=value.getname();
                Badgeform l = new Badgeform();
                Letter=l.letter(Name);
                badge.setText(Letter);
                nv_badge.setText(Letter);
                
                for (i = 1; i <= x; i++) {


                    myRef.child("users").child(finalUserId).child("transaction").child(String.valueOf(i)).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Transaction value1 = dataSnapshot.getValue(Transaction.class);
                            if(value1.getType().equals("Received")) {
                                   series.appendData(new DataPoint(p, value1.getAmount()), false, 1000);
                                   p++;
                                series2.appendData(new DataPoint(s, value1.getAmount()), false, 1000);
                                s++;
                               }
                            if(value1.getType().equals("Sent")) {
                                series1.appendData(new DataPoint(q, value1.getAmount()), false, 1000);
                                q++;
                                series2.appendData(new DataPoint(s, -value1.getAmount()), false, 1000);
                                s++;
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                graphView.addSeries(series);
                graph1.addSeries(series1);
                graph2.addSeries(series2);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
            startActivity(new Intent(ExpenditureActivity.this, PersonalActivity.class));
        } else if (id == R.id.nav_h) {
            Intent intent = new Intent(ExpenditureActivity.this,DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if (id == R.id.nav_ac) {
            startActivity(new Intent(ExpenditureActivity.this, AccountsActivity.class));
        } else if (id == R.id.nav_ts) {
            startActivity(new Intent(ExpenditureActivity.this, TransactionActivity.class));
        }else if (id == R.id.nav_mc) {
            startActivity(new Intent(ExpenditureActivity.this,Pending_requests.class));
        }else if (id == R.id.nav_ex) {
            startActivity(new Intent(ExpenditureActivity.this, ExpenditureActivity.class));
        } else if (id == R.id.nav_logout) {
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.remove("logged");
            editor.commit();
            finish();
            Intent i = new Intent(ExpenditureActivity.this,LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
