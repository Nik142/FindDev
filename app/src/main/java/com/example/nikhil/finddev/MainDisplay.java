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

public class MainDisplay extends AppCompatActivity {

    private final int contactReq = 12;
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

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){

            case R.id.newContact: Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(intent,contactReq);
            break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK){

            switch (requestCode){

                case contactReq: contactPicked(data);
                break;
            }
        }
    }

    private void contactPicked(Intent data){

        Cursor cursor = null;

        try{

            Uri uri = data.getData();
            String phoneNumber = null, contactName = null;

            cursor = getContentResolver().query(uri,null,null,null,null);
            cursor.moveToFirst();

            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

            phoneNumber = cursor.getString(phoneIndex);
            contactName = cursor.getString(nameIndex);

        }catch (Exception e){

            e.printStackTrace();
        }
    }*/

    private void setViewPager(ViewPager vp){

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        sWY = new ShareWY();
        YsW = new YshareW();

        adapter.addFragment(sWY,"Share With You");
        adapter.addFragment(YsW,"You share With");

        viewPager.setAdapter(adapter);
    }
}
