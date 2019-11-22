package com.example.wemood;

/**
 * Class name: MainActivity
 *
 * version 1.0
 *
 * Date: November 5, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.RadioGroup;

import com.example.wemood.Fragments.FriendsFragment;
import com.example.wemood.Fragments.HomeFragment;
import com.example.wemood.Fragments.MapFragment;
import com.example.wemood.Fragments.ProfileFragment;

/**
 * @author ChengZhang Dong
 *
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    private RadioGroup mTabRadioGroup;
    private SparseArray<Fragment> mFragmentSparseArray;

    /**
     * initialize the view of MainActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * set the navigation bar and HomePage as a default page
     */
    private void initView() {
        mTabRadioGroup = findViewById(R.id.tabs_rg);
        mFragmentSparseArray = new SparseArray<>();
        mFragmentSparseArray.append(R.id.home_tab, HomeFragment.newInstance());
        mFragmentSparseArray.append(R.id.friends_tab, FriendsFragment.newInstance());
        mFragmentSparseArray.append(R.id.map_tab, MapFragment.newInstance());
        mFragmentSparseArray.append(R.id.profile_tab, ProfileFragment.newInstance());
        mTabRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        mFragmentSparseArray.get(checkedId)).commit();
            }
        });
        // set HomePage as default page
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                mFragmentSparseArray.get(R.id.home_tab)).commit();
        findViewById(R.id.sign_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddMoodActivity.class));
            }
        });
    }

}