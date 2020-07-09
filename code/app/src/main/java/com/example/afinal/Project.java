package com.example.afinal;

import java.util.ArrayList;

public class Project {

    String projectid;
    String pname;
    String deadline;
    String desc;
    String fname;
    String group;

    public Project(){ }

    public Project(String projectid, String pname, String deadline , String desc , String fname){
        this.pname = pname;
        this.deadline = deadline;
        this.desc = desc;
        this.fname  = fname;
        this.projectid = projectid;

    }

    public Project(String fname, String pname ,String group ){
        this.pname = pname;
        this.fname = fname;
        this.group = group;
    }

    public String getProjectid() {
        return projectid;
    }

    public String getPname() {
        return pname;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getDesc() {
        return desc;
    }

    public String getFname() { return fname; }

    public String getGroup() { return group; }



}
