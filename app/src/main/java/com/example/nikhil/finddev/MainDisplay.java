package com.example.nikhil.finddev;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class MainDisplay extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    ShareWY sWY;
    YshareW YsW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_display);

        viewPager = findViewById(R.id.ViewPager);

        tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                viewPager.setCurrentItem(position,false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainDisplay.this, SignIn.class));
                MainDisplay.this.finish();
            break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setViewPager(ViewPager vp){

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        sWY = new ShareWY();
        YsW = new YshareW();

        adapter.addFragment(sWY,"Share With You");
        adapter.addFragment(YsW,"You share With");

        viewPager.setAdapter(adapter);
    }
}
