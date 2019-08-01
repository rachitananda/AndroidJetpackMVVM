package com.rachita.mvvm.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BookDao {

    @Insert
    void insert(Book book);

    @Insert
    void insert(Book... book);

    @Delete
    void delete(Book book);

    @Update
    void update(Book book);

    @Query("SELECT * from  book_table")
    List<Book> getBooks();

    @Query("SELECT * from book_table where category_id==:category_id")
    LiveData<List<Book>> getBooks(int category_id);

}
