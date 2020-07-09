package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FacultyMainActivity extends AppCompatActivity {

    String email;
    Button create;
    Button groups;
    TextView name;
    Button add;
    Button del;
    private FirebaseAuth firebaseauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_main);
        create = (Button) findViewById(R.id.create);
        name = (TextView) findViewById(R.id.name);
        groups = (Button) findViewById(R.id.groups);
        add = (Button) findViewById(R.id.add);
        del = (Button) findViewById(R.id.del);


        firebaseauth= FirebaseAuth.getInstance();
        FirebaseUser user=firebaseauth.getCurrentUser();
        email=user.getEmail();

        FirebaseDatabase.getInstance().getReference("LoginFaculty").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserFaculty artist = snapshot.getValue(UserFaculty.class);
                    if(email.equals(artist.getFacultyEmail()))
                    {
                        name.setText("welcome "+artist.getFacultyName());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(FacultyMainActivity.this,ProjectCreate.class);
                i.putExtra("name1",name.getText().toString().trim());
                startActivity(i);
            }
        });
        groups.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(FacultyMainActivity.this,GroupCreate.class);
                startActivity(i);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(FacultyMainActivity.this,AddStudent.class);
                startActivity(i);
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(FacultyMainActivity.this,DelStudent.class);
                startActivity(i);
            }
        });

    }
}
