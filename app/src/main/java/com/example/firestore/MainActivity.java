package com.example.firestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private  EditText mTitleEt,mDescriptionEt;
    private Button mSaveBtn,mviewBtn;

    //firesotre object creating
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //view initialization
        mTitleEt =findViewById(R.id.edit_title);
        mDescriptionEt=findViewById(R.id.edit_desc);
        mSaveBtn=findViewById(R.id.save_btn);
        mviewBtn=findViewById(R.id.showall_btn);

        db=FirebaseFirestore.getInstance();

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=mTitleEt.getText().toString().trim();
                String description=mDescriptionEt.getText().toString();
                String id= UUID.randomUUID().toString();

                SavetoFireStore(id,title,description);
            }
        });



    }

    private void SavetoFireStore(String id, String title, String description) {

        if(!title.isEmpty()&&!description.isEmpty()){

            HashMap<String,Object>map=new HashMap<>();
            map.put("id",id);
            map.put("title",title);
            map.put("description",description);

            db.collection("Documents").document(id).set(map)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                  if(task.isSuccessful()){
                      Toast.makeText(MainActivity.this,"loaded", Toast.LENGTH_LONG);
                  }
                  else {
                      Toast.makeText(MainActivity.this,"failed", Toast.LENGTH_LONG);
                  }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });



        }


        else
        {
            Toast.makeText(MainActivity.this," write somrhitn",Toast.LENGTH_SHORT);
        }

    }
}