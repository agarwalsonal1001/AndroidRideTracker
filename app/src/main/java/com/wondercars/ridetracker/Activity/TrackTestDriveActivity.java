package com.wondercars.ridetracker.Activity;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.MediaController;

import com.wondercars.ridetracker.Adapters.ViewPagerAdapter;
import com.wondercars.ridetracker.Fragments.CompletedTestDriveFragment;
import com.wondercars.ridetracker.Fragments.OnGoingTestDriveFragment;
import com.wondercars.ridetracker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrackTestDriveActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.container)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_test_drive);
        ButterKnife.bind(this);
        initActionBar();
        setupTabLayout();
        setupViewPager(mViewPager);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();

                return true;

        default:
            return super.onOptionsItemSelected(item);

        }

    }

    private void setupViewPager(ViewPager mViewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(OnGoingTestDriveFragment.newInstance(), "OnGoing TestDrives");
        adapter.addFragment(CompletedTestDriveFragment.newInstance(), "Completed TestDrives");
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {
             /*   if (state == 0) {
                    try {
                        OtherSelectMedicineFragment.selectMedicineAdapterOther.clearData();
                    } catch (Exception e) {

                    }

                }*/

            }
        });
    }

    private void setupTabLayout() {
        tabs.setupWithViewPager(mViewPager);
    }

    private void initActionBar() {
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Track Rides </font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
