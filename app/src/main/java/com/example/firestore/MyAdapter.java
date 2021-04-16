package com.example.firestore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyviewHolder> {
    private  ShowActivity activity;
    private List<Model> mList;

    public MyAdapter(ShowActivity activity, List<Model> mList) {
        this.activity = activity;
        this.mList = mList;
    }

//for update
    public void updateData(int position){
        Model item=mList.get(position);
        Bundle bundle=new Bundle();
        bundle.putString("uId",item.getId());
        bundle.putString("uTitle",item.getTitle());
        bundle.putString("uDesc",item.getDesc());
        Intent intent=new Intent(activity,MainActivity.class);
        activity.startActivity(intent);
        //pass data to main activity
    }
    //
    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(activity).inflate(R.layout.item,parent,false);

         return new MyviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        holder.title.setText(mList.get(position).getTitle());
        holder.desc.setText(mList.get(position).getDesc());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyviewHolder extends RecyclerView.ViewHolder{
            TextView title,desc;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title_text);
            desc=itemView.findViewById(R.id.title_desc);

        }
    }
}
