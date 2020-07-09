package com.example.afinal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProjectCreate extends Activity {
    EditText editProjectName;
    EditText editDeadline;
    EditText editDesc;
    Button CreateProject;
    String fname;
    String [] fname1 = new String[2];

    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_layout);

        editProjectName = (EditText) findViewById(R.id.editProjectName);
        editDeadline = (EditText) findViewById(R.id.editDeadline);
        editDesc = (EditText) findViewById(R.id.editDesc);
        CreateProject = (Button) findViewById(R.id.CreateProject);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                fname= null;
            } else {
                fname= extras.getString("name1");
                fname1 = fname.split(" ");
            }
        } else {
            fname = (String) savedInstanceState.getSerializable("fname");
            fname1 = fname.split(" ");
        }

        database = FirebaseDatabase.getInstance().getReference("ProjectFaculty");

        CreateProject.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                addprojects();
            }
        });
    }

    private void addprojects() {
        String pname = editProjectName.getText().toString().trim();
        String deadline = editDeadline.getText().toString().trim();
        String desc = editDesc.getText().toString().trim();

        if (!TextUtils.isEmpty(pname) && !TextUtils.isEmpty(deadline) && !TextUtils.isEmpty(desc)) {
            String id = database.push().getKey();

            Project p = new Project(id, pname, deadline, desc , fname1[1]);

            database.child(id).setValue(p);

            Toast.makeText(this, "project added", Toast.LENGTH_LONG).show();
            Intent i = new Intent(ProjectCreate.this,FacultyMainActivity.class);
            startActivity(i);

        }
        else {
            Toast.makeText(this, "enter project name or deadline properly", Toast.LENGTH_LONG).show();
        }
    }
}

