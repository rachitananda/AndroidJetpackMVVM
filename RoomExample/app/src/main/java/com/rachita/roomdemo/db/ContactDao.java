package com.rachita.roomdemo.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    void insert(ContactEntity contactEntity);

    @Delete
    void delete(ContactEntity contactEntity);

    @Query("select * from contacts")
    List<ContactEntity> selectAll();



}
