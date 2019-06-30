package com.rachita.roomdemo.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.rachita.roomdemo.BR;

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

    @ColumnInfo(name = "contact_name")
    private String name;

    @ColumnInfo(name = "contact_email")
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
        notifyPropertyChanged(BR.name);
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }


}
