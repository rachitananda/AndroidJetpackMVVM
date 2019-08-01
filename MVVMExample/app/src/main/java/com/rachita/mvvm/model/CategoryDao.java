package com.rachita.mvvm.model;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert
    void insert(Category category);

    @Insert
    void insert(Category... category);

    @Update
    void update(Category category);

    @Delete
    void  delete(Category category);

    @Query("select * from BOOK_CATEGORY_TABLE")
    List<Category> getAll();


}
