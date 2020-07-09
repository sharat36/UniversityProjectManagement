package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddStudent extends AppCompatActivity {

    EditText et1,et2;
    Button btn;

    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);


        et1 = (EditText) findViewById(R.id.editText3);
        et2 = (EditText) findViewById(R.id.editText2);
        btn = (Button) findViewById(R.id.button);

        db = FirebaseDatabase.getInstance().getReference("ProjectStudent");

        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                addStudent();
            }
        });

    }
    private void addStudent()
    {
        String project = et1.getText().toString().trim();
        String name = et2.getText().toString().trim();

        if((TextUtils.isEmpty((name)) == false) && (TextUtils.isEmpty((project)) == false))
        {
            String id = db.push().getKey();

            Project p = new Project(id, name, "xx", "xx",project);

            db.child(id).setValue(p);

            Toast.makeText(this, "Student added", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(AddStudent.this,FacultyMainActivity.class);
            startActivity(i);
        }

        else
        {
            Toast.makeText(this, "Enter project and student name properly", Toast.LENGTH_SHORT).show();
        }
    }
}

