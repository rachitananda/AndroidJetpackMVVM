package com.rachita.roomdemo.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Base Oberserver extended for 2 way data binding
 * @Bindable used for 2 way data binding
 * Getter & setters are need for 2 way data binding
 * @Ignore asks room to ignore the constructor
 */

@Entity(tableName = "contacts")
public class ContactEntity extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    private int studentId;
    private String name;
    private String email;

    public int getStudentId() {
        return studentId;
    }

    @Ignore
    public ContactEntity() {
    }

    public ContactEntity(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Bindable
    public String getName() {
        return name;
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
