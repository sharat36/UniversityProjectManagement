package com.example.afinal;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GroupCreate extends AppCompatActivity {

    DatabaseReference database;
    DatabaseReference database1;
    DatabaseReference database2;
    ListView list;

    List<Project> plist;

    String temp;
    int j = 0;
    LinkedList<String>templist = new LinkedList<>();

    protected void showGroups() {
        plist = new ArrayList<>();

        database2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){

                for (DataSnapshot projectSnapshot : dataSnapshot.getChildren()) {
                    Project project = projectSnapshot.getValue(Project.class);
                    plist.add(project);
                }
                GroupList adapter = new GroupList(GroupCreate.this, plist);
                list.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void addGroups() {
        database1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){

                for (DataSnapshot projectSnapshot : dataSnapshot.getChildren()) {
                    Project project = projectSnapshot.getValue(Project.class);
                    temp =  project.getFname() + " " + project.getPname();
                    templist.add(temp);
                }

                LinkedList<String> groups = new LinkedList<>();
                LinkedList<String> list = templist;

                Map<String, LinkedList<String>> map = new HashMap<>();
                for(int i = 0; i < list.size(); i++) {
                    String[] arr = list.get(i).split(" ");
                    if (!map.containsKey(arr[1])) {
                        LinkedList<String> tempList = new LinkedList<>();
                        tempList.add(arr[0]);
                        map.put(arr[1], tempList);
                    } else {
                        LinkedList<String> tempList = map.get(arr[1]);
                        tempList.add(arr[0]);
                        map.put(arr[1], tempList);
                    }
                }

                Map<String, LinkedList<String>> map1 = map;

                for(Map.Entry<String, LinkedList<String>> m : map1.entrySet()) {
                    String pname = m.getKey();
                    LinkedList<String> x = m.getValue();
                    if (x.size() % 2 == 0) {
                        for (int i = 0; i < x.size(); i += 2) {
                            j++;
                            String str = "Group" + j + "=" + x.get(i) + "," + x.get(i + 1) + "-" + pname;
                            groups.add(str);
                        }
                    } else if (x.size() >= 3) {
                        j++;
                        String str = "Group" + j + "=" + x.get(0) + "," + x.get(1) + "," + x.get(2) + "-" + pname;
                        groups.add(str);
                        for (int i = 3; i < x.size(); i += 2) {
                            j++;
                            str = "Group" + j + "=" + x.get(i) + "," + x.get(i + 1) + "-" + pname;
                            groups.add(str);
                        }
                    } else if (x.size() == 1) {
                        String str = "Group" + j + "=" + x.get(0) + "-" + pname;
                        groups.add(str);
                    }
                }

                LinkedList<String> groups1 = groups;

                database.removeValue();

                for (int i = 0; i < groups1.size(); i++) {
                    String str = groups1.get(i);
                    String[] group_name = str.split("=");
                    String[] project_name = group_name[1].split("-");


                    String group_name1 = group_name[0];
                    String project_name1 = project_name[1];
                    String names = project_name[0];

                    Project project = new Project(names, project_name1, group_name1);
                    String id = database.push().getKey();
                    database.child(id).setValue(project);
                }
                showGroups();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_create);

        database = FirebaseDatabase.getInstance().getReference("Groups");
        database2 = FirebaseDatabase.getInstance().getReference("Groups");
        database1 = FirebaseDatabase.getInstance().getReference("ProjectStudent");
        list = (ListView) findViewById(R.id.listViewGroups);

        addGroups();
    }
}
