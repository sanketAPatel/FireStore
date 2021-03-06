package com.example.firestore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyviewHolder> {
    private  ShowActivity activity;
    private List<Model> mList;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();

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
    //for Delete
    public  void  DeleteData(int position){
        Model item=mList.get(position);
        db.collection("Documents").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            notifyremoved(position);
                            Toast.makeText(activity,"data delete success..",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(activity,"data delete failed.."+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    private  void notifyremoved(int position)
    {
        mList.remove(position);
        notifyItemMoved(position,getItemCount());
        activity.showData();
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
