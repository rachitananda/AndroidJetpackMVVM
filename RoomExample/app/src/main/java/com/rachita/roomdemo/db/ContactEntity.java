package com.rachita.roomdemo.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "contacts")
public class ContactEntity {

    @PrimaryKey(autoGenerate = true)
    private int studentId;
    private String name;
    private String email;

    public int getStudentId() {
        return studentId;
    }

    public ContactEntity(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

}
