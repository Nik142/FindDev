package com.example.nikhil.finddev;

import android.content.Context;
import android.content.Intent;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class YshareW extends Fragment {

    FloatingActionButton fab;
    private final int contactReq = 12;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Switch aSwitch;
    String YSW, SWY;
    List<FireModel> list;
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

        RecyclerViewAdapter_YSW recyclerAdapter = new RecyclerViewAdapter_YSW(list,getContext());
        RecyclerView.LayoutManager recyce = new GridLayoutManager(getContext(),1);
        recycle.setLayoutManager(recyce);
        recycle.setItemAnimator( new DefaultItemAnimator());
        recycle.setAdapter(recyclerAdapter);

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

        SWY = getResources().getString(R.string.SWY);
        YSW = getResources().getString(R.string.YSW);

        myRef.child(SignUp.getNumber()).child(YSW).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                list = new ArrayList<FireModel>();
                for(DataSnapshot objSnapshot : dataSnapshot.getChildren()){

                    FireModel value = objSnapshot.getValue(FireModel.class);
                    FireModel fire = new FireModel();
                    String name = value.getName();
                    String phone = value.getPhone();
                    String status = value.getStatus();
                    fire.setName(name);
                    fire.setPhone(phone);
                    fire.setStatus(status);
                    list.add(fire);
                }
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

                            myRef.child(SignUp.getNumber()).child(YSW).child(finalPhoneNumber).child("Name").setValue(finalContactName);
                            myRef.child(SignUp.getNumber()).child(YSW).child(finalPhoneNumber).child("Status").setValue("True");

                            myRef.child(finalPhoneNumber).child(SWY).child(SignUp.getNumber()).child("Name").setValue("Dummy Name");
                            myRef.child(finalPhoneNumber).child(SWY).child(SignUp.getNumber()).child("Added Back").setValue("No");
                            myRef.child(finalPhoneNumber).child(SWY).child(SignUp.getNumber()).child("Status").setValue("True");
                            myRef.child(finalPhoneNumber).child(SWY).child(SignUp.getNumber()).child("Live Status").setValue("False");
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
