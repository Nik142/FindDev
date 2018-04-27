package com.example.nikhil.finddev;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class YshareW extends Fragment {

    FloatingActionButton fab;
    private final int contactReq = 12;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef, idRef;
    FirebaseUser user;
    Switch aSwitch;
    String YSW, SWY, pNumber = "";
    ArrayList<HashMap<String,String>> list;
    RecyclerView recycle;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_yshare_w,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recycle = getView().findViewById(R.id.recyclerViewYSW);



        fab = getView().findViewById(R.id.newContactFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent,contactReq);
            }
        });

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("Users");
        idRef = database.getReference().child("userId");

           SharedPreferences preferences = getActivity().getSharedPreferences("FindDev",Context.MODE_PRIVATE);
           pNumber = preferences.getString("pNumber",null);

           Toast.makeText(getActivity().getApplicationContext(),pNumber,Toast.LENGTH_LONG).show();

        SWY = getResources().getString(R.string.SWY);
        YSW = getResources().getString(R.string.YSW);

    }

    @Override
    public void onResume() {
        super.onResume();

        myRef.child(pNumber).child(YSW).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                list = new ArrayList<>();
                for(DataSnapshot objSnapshot : dataSnapshot.getChildren()){

                    HashMap<String,String> samples = new HashMap<>();

                    ArrayList<String> tempValues = (ArrayList<String>) objSnapshot.getValue();

                    String name = tempValues.get(0);
                    String stat = tempValues.get(1);

                    samples.put("Name", name);
                    samples.put("Status", stat);
                    list.add(samples);

                }

                RecyclerViewAdapter_YSW recyclerAdapter = new RecyclerViewAdapter_YSW(list,getContext());
                RecyclerView.LayoutManager recyce = new GridLayoutManager(getContext(),1);
                recycle.setLayoutManager(recyce);
                recycle.setItemAnimator( new DefaultItemAnimator());
                recycle.setAdapter(recyclerAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

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

            cursor = getActivity().getContentResolver().query(uri,null,null,null,null);
            cursor.moveToFirst();

            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

            phoneNumber = cursor.getString(phoneIndex);
            contactName = cursor.getString(nameIndex);

            final String finalPhoneNumber = phoneNumber;
            final String finalContactName = contactName;
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot objSnapshot: dataSnapshot.getChildren()) {
                        Object obj = objSnapshot.getKey();

                        if(obj.equals(finalPhoneNumber)){

                            myRef.child(pNumber).child(YSW).child(finalPhoneNumber)
                                    .child("0").setValue(finalContactName);// " 0 " = " Name "
                            myRef.child(pNumber).child(YSW).child(finalPhoneNumber)
                                    .child("1").setValue("True");// " 1 " = " Status "

                            myRef.child(finalPhoneNumber).child(SWY).child(pNumber)
                                    .child("0").setValue("Dummy Name");
                            myRef.child(finalPhoneNumber).child(SWY).child(pNumber)
                                    .child("2").setValue("No");// " 2 " = " Added Back"
                            myRef.child(finalPhoneNumber).child(SWY).child(pNumber)
                                    .child("1").setValue("True");
                            myRef.child(finalPhoneNumber).child(SWY).child(pNumber)
                                    .child("3").setValue("False");// " 3 " = " Live status "
                        }
                        else{
                            Toast.makeText(getContext(),"This user does not exist",Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }catch (Exception e){

            e.printStackTrace();
        }
    }
}
