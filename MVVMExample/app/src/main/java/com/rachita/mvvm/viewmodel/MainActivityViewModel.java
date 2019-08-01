package com.rachita.mvvm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.rachita.mvvm.model.Book;
import com.rachita.mvvm.model.BookStoreRepository;
import com.rachita.mvvm.model.Category;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {



   private BookStoreRepository bookStoreRepository;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        bookStoreRepository= new BookStoreRepository(application);
    }


    public List<Category> getCategories(){
      return   bookStoreRepository.getCategories();
    }

    public LiveData<List<Book>> getBooks(int categoryId){
       return bookStoreRepository.getBooks(categoryId);
    }

}
