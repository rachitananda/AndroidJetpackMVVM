package com.rachita.mvvm.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Book.class, Category.class}, version = 1)
public abstract class BookDatabase extends RoomDatabase {

    public abstract BookDao getBookDao();

    public abstract CategoryDao getCategoryDao();


}
