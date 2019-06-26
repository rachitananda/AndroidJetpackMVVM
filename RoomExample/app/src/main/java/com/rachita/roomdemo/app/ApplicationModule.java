package com.rachita.roomdemo.app;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.rachita.roomdemo.db.ContactDb;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {


    private ContactDb contactDb;


    public ApplicationModule(Application application) {

        contactDb = Room.databaseBuilder(application, ContactDb.class, "ContactDb")
                .allowMainThreadQueries()
                .build();
    }


    @Provides
    @Singleton
    public ContactDb providesContactDb() {
        return contactDb;
    }


}
