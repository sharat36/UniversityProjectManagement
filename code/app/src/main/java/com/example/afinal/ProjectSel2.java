package com.example.afinal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ProjectSel2 extends ArrayAdapter<Project> {

    private Activity context;
    private List<Project> plist;

    Button submit;
    TextView pname;
    TextView deadline;
    TextView desc;
    ListView list;
    String sname;
    String email;

    private FirebaseAuth firebaseauth;

    DatabaseReference database = FirebaseDatabase.getInstance().getReference("ProjectStudent");

    public ProjectSel2(Activity context, List<Project> plist) {
        super(context, R.layout.student_list, plist);
        this.context = context;
        this.plist = plist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.project_sel, null, true);

        pname = (TextView) listViewItem.findViewById(R.id.pname);
        deadline = (TextView) listViewItem.findViewById(R.id.deadline);
        desc = (TextView) listViewItem.findViewById(R.id.desc);
        list = (ListView) listViewItem.findViewById(R.id.list);

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
                        sname = artist.getStudentName();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        submit = (Button) listViewItem.findViewById(R.id.submit);
        final Project project = plist.get(position);

        pname.setText(project.getPname());
        deadline.setText(project.getDeadline());
        desc.setText(project.getDesc());


        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("CONFIRMATION");
                builder.setMessage("Do you want to select "+project.getPname()+" project ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        addProjects(project.getPname(),project.getDeadline(),project.getDesc());
                        Intent i = new Intent(getContext(), StudentMainActivity.class);
                        context.startActivity(i);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        return listViewItem;
    }

    private void addProjects(String pname1,String deadline1,String desc1) {
        String id = database.push().getKey();

        Project p = new Project(id, pname1, deadline1, desc1,sname);

        database.child(id).setValue(p);

        Toast.makeText(context, pname1+" Selected",Toast.LENGTH_LONG).show();
        //Intent i = new Intent(view,MainActivity.class);
    }
}
