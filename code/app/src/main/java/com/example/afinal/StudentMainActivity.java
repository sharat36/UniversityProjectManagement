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

public class StudentMainActivity extends AppCompatActivity {

    Button select;
    Button groups;
    String email;
    TextView name;
    Button upload;

    private FirebaseAuth firebaseauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        select = (Button) findViewById(R.id.select);
        groups = (Button) findViewById(R.id.groups);
        name = (TextView) findViewById(R.id.name);
        upload = (Button) findViewById(R.id.upload);

        firebaseauth= FirebaseAuth.getInstance();
        FirebaseUser user=firebaseauth.getCurrentUser();
        email=user.getEmail();

        FirebaseDatabase.getInstance().getReference("LoginStudent").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserStudent artist = snapshot.getValue(UserStudent.class);
                    if(email.equals(artist.getStudentEmail()))
                    {
                        name.setText("Welcome " + artist.getStudentName());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(StudentMainActivity.this,ProjectSel.class);
                startActivity(i);
            }
        });

        groups.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(StudentMainActivity.this,GroupCreate.class);
                startActivity(i);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudentMainActivity.this , UploadActivity.class);
                startActivity(i);
            }
        });

    }

}

