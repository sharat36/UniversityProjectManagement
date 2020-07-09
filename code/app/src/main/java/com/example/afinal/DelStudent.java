package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DelStudent extends AppCompatActivity {

    EditText et1,et2;
    Button btn;

    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.del_layout);

        et1 = (EditText) findViewById(R.id.editText3);
        et2 = (EditText) findViewById(R.id.editText2);
        btn = (Button) findViewById(R.id.button);
        database = FirebaseDatabase.getInstance().getReference("ProjectStudent");

        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                deleteStudent();
            }
        });
    }

    private void deleteStudent()
    {
        final String project = et1.getText().toString().trim();
        final String name = et2.getText().toString().trim();


        if((TextUtils.isEmpty((name)) == false) && (TextUtils.isEmpty((project)) == false))
        {
            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                    for (DataSnapshot projectSnapshot : dataSnapshot.getChildren()) {
                        Project p = projectSnapshot.getValue(Project.class);
                        if(p.getPname().equals(name) && p.getFname().equals(project)){
                            DatabaseReference dr = FirebaseDatabase.getInstance().getReference("ProjectStudent").child(p.getProjectid());
                            dr.removeValue();
                            Intent i = new Intent(DelStudent.this,FacultyMainActivity.class);
                            startActivity(i);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        else
        {
            Toast.makeText(this, "Enter project and student name properly", Toast.LENGTH_SHORT).show();
        }
    }
}