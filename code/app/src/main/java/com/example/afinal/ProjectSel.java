package com.example.afinal;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;


import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class
ProjectSel extends Activity {

    ListView list;

    List<Project> plist;

    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_list);

        list = (ListView) findViewById(R.id.list);

        database = FirebaseDatabase.getInstance().getReference("ProjectFaculty");

    }

    @Override
    protected void onStart() {
        super.onStart();

        plist = new ArrayList<>();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                plist.clear();

                for (DataSnapshot projectSnapshot : dataSnapshot.getChildren()) {
                    Project project = projectSnapshot.getValue(Project.class);

                    plist.add(project);
                }

                ProjectSel2 adapter = new ProjectSel2(ProjectSel.this, plist);
                list.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
