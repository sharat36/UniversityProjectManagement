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
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText fullName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    private ProgressDialog progressDialog;
    private RadioButton student;
    private RadioButton faculty;
    DatabaseReference databaseuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        firebaseAuth = FirebaseAuth.getInstance();
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        fullName=(EditText) findViewById(R.id.fullname);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        student=findViewById(R.id.studentradiobutton);
        faculty=findViewById(R.id.facultyradiobutton);
        progressDialog = new ProgressDialog(this);
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if(student.isChecked())
                {
                    adduserStudent();

                }
                else if (faculty.isChecked())
                {
                    adduserFaculty();

                }
                else
                {
                    Toast.makeText(UserActivity.this, "Please Select", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void adduserStudent()
    {
        String name = fullName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        databaseuser = FirebaseDatabase.getInstance().getReference("LoginStudent");
        String id = databaseuser.push().getKey();
        UserStudent user = new UserStudent(id, name, email);
        databaseuser.child(id).setValue(user);
        Toast.makeText(this, "Student added", Toast.LENGTH_LONG).show();
        registerUser(email,password);
    }
    private void adduserFaculty()
    {
        String name = fullName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        databaseuser = FirebaseDatabase.getInstance().getReference("LoginFaculty");
        String id = databaseuser.push().getKey();
        UserFaculty user = new UserFaculty(id, name, email);
        databaseuser.child(id).setValue(user);
        Toast.makeText(this, "Faculty added", Toast.LENGTH_LONG).show();
        registerUser(email,password);
    }
    private void registerUser(String email , String password){

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //finish();
                            customeDialogbox();
                            //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            customeDialogboxError();
                            //Toast.makeText(UserActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }
    public void customeDialogbox()
    {
        DialogShow builder=new DialogShow();
        builder.show(getSupportFragmentManager(),"Registration");
    }
    public void customeDialogboxError()
    {
        DialogShowRegError builder=new DialogShowRegError();
        builder.show(getSupportFragmentManager(),"Registration");
    }
}
