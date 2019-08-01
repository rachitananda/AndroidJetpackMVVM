package com.rachita.mvvm.root;

import com.rachita.mvvm.view.MainActivity;
import com.rachita.mvvm.viewmodel.MainActivityViewModel;

import dagger.Component;

@ApplicationScope
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);
    void inject(MainActivityViewModel mainActivityViewModel);
}
