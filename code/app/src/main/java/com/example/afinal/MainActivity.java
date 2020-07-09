package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button Login;
    private EditText Email;
    private EditText Password;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private ArrayList<UserStudent> list=new ArrayList<>();
    TextView newuser;
    String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        Login=(Button) findViewById(R.id.buttonlogin);
        Email=(EditText) findViewById(R.id.email);
        Password=(EditText) findViewById(R.id.password);

        progressDialog = new ProgressDialog(this);
        newuser=findViewById(R.id.buttonnew);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=Email.getText().toString().trim();
                password=Password.getText().toString().trim();

                FirebaseDatabase.getInstance().getReference("LoginStudent").addValueEventListener(new ValueEventListener() {
                    @Override

                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list.clear();
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                UserStudent artist = snapshot.getValue(UserStudent.class);
                                if (email.equals(artist.getStudentEmail())) {
                                    loginStudent(email,password);
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                FirebaseDatabase.getInstance().getReference("LoginFaculty").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list.clear();
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                UserFaculty artist = snapshot.getValue(UserFaculty.class);

                                if (email.equals(artist.getFacultyEmail())) {
                                    loginFaculty(email,password);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        });
        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UserActivity.class));
            }
        });
    }
    private void loginStudent(String email , String password) {

        if(TextUtils.isEmpty(email)){
            Toast.makeText(MainActivity.this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(MainActivity.this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "login successful", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MainActivity.this, StudentMainActivity.class));
                        }
                        else
                        {
                            customeDialogboxLoginError();
                            //Toast.makeText(MainActivity.this, "Error please check", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void loginFaculty(String email , String password) {

        if(TextUtils.isEmpty(email)){
            Toast.makeText(MainActivity.this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(MainActivity.this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "login successful", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MainActivity.this, FacultyMainActivity.class));
                        }
                        else
                        {
                            customeDialogboxLoginError();
                            //Toast.makeText(MainActivity.this, "Error please check", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void customeDialogboxLoginError()
    {
        DialogShowLoginError builder=new DialogShowLoginError();
        builder.show(getSupportFragmentManager(),"Login");
    }
}

