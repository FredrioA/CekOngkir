package com.example.cekongkir.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.cekongkir.R;
import com.example.cekongkir.ui.fragment.HomeFragment;
import com.example.cekongkir.ui.fragment.ListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

@SuppressLint("NonConstantResourceId")
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Fragment selectedFragment = new HomeFragment();
    protected BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        loadFragment(selectedFragment);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_nav_home:
                selectedFragment = new HomeFragment();
                loadFragment(selectedFragment);
                break;

            case R.id.menu_nav_list:
                selectedFragment = new ListFragment();
                loadFragment(selectedFragment);
                break;
        }
        return loadFragment(selectedFragment);
    }

    private boolean loadFragment(Fragment selectedFragment) {
        if (selectedFragment !=null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,selectedFragment)
                    .commit();
            return true;
        }
        return false;
    }
}
