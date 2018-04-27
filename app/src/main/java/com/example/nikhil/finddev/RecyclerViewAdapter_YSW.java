package com.example.nikhil.finddev;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerViewAdapter_YSW extends RecyclerView.Adapter <RecyclerViewAdapter_YSW.MyViewHolder> {

    ArrayList<HashMap<String,String>> list;
    Context context;
    String sharingStatus;

    public RecyclerViewAdapter_YSW(ArrayList<HashMap<String,String>> list, Context context) {
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

        HashMap<String,String> temp = list.get(position);
        holder.name.setText(temp.get("Name"));
        sharingStatus = temp.get("Status");

        if(sharingStatus.equals("True")){
            holder.aSwitch.setChecked(true);
        }
        else{
            holder.aSwitch.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, number;
        Switch aSwitch;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nameTxt);
            number = itemView.findViewById(R.id.pnumberTxt);
            aSwitch = itemView.findViewById(R.id.statusSwitch);
        }
    }
}
