package com.rachita.roomdemo.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {ContactEntity.class},version = 1)
public abstract class ContactDb extends RoomDatabase {

   public abstract ContactDao getContactDao();
}
