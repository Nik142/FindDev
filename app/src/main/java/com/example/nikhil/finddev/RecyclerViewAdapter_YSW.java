package com.example.nikhil.finddev;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter_YSW extends RecyclerView.Adapter <RecyclerViewAdapter_YSW.MyViewHolder> {

    List<FireModel> list;
    Context context;
    String sharingStatus;

    public RecyclerViewAdapter_YSW(List<FireModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ysw, parent, false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        FireModel myList = list.get(position);
        MyViewHolder.name.setText(myList.getName());
        MyViewHolder.number.setText(myList.getPhone());
        sharingStatus = myList.getStatus();
        if(sharingStatus.equals("True")){
            MyViewHolder.aSwitch.setChecked(true);
        }
        else{
            MyViewHolder.aSwitch.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {

        int arr = 0;

        try{
            if(list.size()==0){

                arr = 0;

            }
            else{

                arr=list.size();
            }



        }catch (Exception e){



        }

        return arr;
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{

        static TextView name, number;
        static Switch aSwitch;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nameTxt);
            number = itemView.findViewById(R.id.pnumberTxt);
            aSwitch = itemView.findViewById(R.id.statusSwitch);
        }
    }
}
