package com.rachita.roomdemo.app;


import com.rachita.roomdemo.AddContactActivity;
import com.rachita.roomdemo.ContactActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(ContactActivity activity);
    void inject(AddContactActivity activity);

}
