package com.rachita.mvvm.root;

import android.content.Context;

import com.rachita.mvvm.model.BookStoreRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Context context;
    private BookStoreRepository bookStoreRepository;

    public ApplicationModule(Context context) {

        this.context = context;
        bookStoreRepository= new BookStoreRepository(context);
    }

    @ApplicationScope
    @Provides
    Context providesContext() {
        return context;
    }

    @ApplicationScope
    @Provides
    BookStoreRepository providesBookStoreRepository() {
        return bookStoreRepository;
    }

}
