package com.example.eveorg;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String UID, userName;
    //TextView curr_name, curr_sap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UID = getIntent().getExtras().get("UID").toString();
        userName = getIntent().getExtras().get("UserName").toString();

        //curr_name = (TextView)findViewById(R.id.nav_fullName);
        //curr_sap = (TextView)findViewById(R.id.nav_user_sap);

        //curr_name.setText(userName);
        //curr_sap.setText(UID);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState==null){
          getSupportFragmentManager().beginTransaction().replace(R.id.frame_2, new fragment_home()).commit();
        }



//        Bundle bundle = new Bundle();
//        bundle.putString("UID",UID);
//        fragment_profile obj = new fragment_profile();
//        obj.setArguments(bundle);
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
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            loadFragment(new fragment_home());

        } else if (id == R.id.nav_search) {
            loadFragment(new fragment_search());

        } else if (id == R.id.nav_profile) {
            loadFragment(new fragment_profile());

        } else if (id == R.id.nav_settings) {
            loadFragment(new fragment_settings());

        } else if (id == R.id.nav_contact_us) {
            loadFragment(new fragment_contact_us());

        } else if (id == R.id.nav_log_out) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Log Out");
            alertDialog.setMessage("Are you sure you want to log out?");

            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "Logging you out..", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this,LoginNew.class));
                    finish();
                }
            });
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(MainActivity.this, "You selected No..", Toast.LENGTH_LONG).show();
                    item.setChecked(false);
                }
            });

            AlertDialog dialog = alertDialog.create();
            dialog.show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadFragment(android.support.v4.app.Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame_2,fragment);
        fragmentTransaction.commit();
    }

}
