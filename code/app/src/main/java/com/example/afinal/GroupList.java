package com.example.afinal;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class GroupList extends ArrayAdapter<Project> {
    private Activity context;
    private List<Project> projects;

    public GroupList(Activity context, List<Project> projects){
        super(context, R.layout.group_create,projects);
        this.context = context;
        this.projects = projects;
    }


    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.group_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewProjects = (TextView) listViewItem.findViewById(R.id.textViewPname);
        TextView textViewgroups = (TextView) listViewItem.findViewById(R.id.textViewGroups);


        Project project = projects.get(position);

        textViewName.setText(project.getFname());
        textViewProjects.setText(project.getPname());
        textViewgroups.setText(project.getGroup());

        return listViewItem;
    }
}