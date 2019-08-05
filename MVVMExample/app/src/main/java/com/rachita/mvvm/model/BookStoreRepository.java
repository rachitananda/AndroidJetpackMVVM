package com.rachita.mvvm.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BookStoreRepository {

    private BookDao bookDao;
    private CategoryDao categoryDao;
    private BookDatabase instance;

    public BookStoreRepository(Context context) {
        instance = getInstance(context);
        this.bookDao = instance.getBookDao();
        this.categoryDao = instance.getCategoryDao();
    }

    private BookDatabase getInstance(Context context) {

        if (instance == null) {

            instance = Room.databaseBuilder(context, BookDatabase.class, "books_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();

        }

        return instance;
    }

    private RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new InitialDataAsyncTask(instance).execute();
        }
    };


    /**
     *
     * @returns category list WITHOUT LIVE Data
     */
    public   List<Category> getCategories() {
        List<Category> categories=null;

        ExecutorService es = Executors.newSingleThreadExecutor();
        Future< List<Category>> result = es.submit(new Callable< List<Category>>() {
            public  List<Category> call() throws Exception {
                // the other thread
                return categoryDao.getAll();
            }
        });
        try {
            categories = result.get();
        } catch (Exception e) {
            // failed
            e.printStackTrace();
        }
        return categories;
    }

    public LiveData<List<Category>> getAllCategories(){
        return categoryDao.getCategories();
    }

    public LiveData<List<Book>> getBooks(int categoryId){
        return bookDao.getBooks(categoryId);
    }

    public void insert(final Book book) {
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                bookDao.insert(book);
            }
        });

    }

    public void update(final Book book) {

        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                bookDao.update(book);
            }
        });
    }

    public void delete(final Book book) {

        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                bookDao.delete(book);
            }
        });
    }


    private static class InitialDataAsyncTask extends AsyncTask<Void, Void, Void> {
        private CategoryDao categoryDAO;
        private BookDao bookDAO;

        public InitialDataAsyncTask(BookDatabase booksDatabase) {

            categoryDAO = booksDatabase.getCategoryDao();
            bookDAO = booksDatabase.getBookDao();
            Log.e("rachita", "InitialDataAsyncTask: ");
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Category[] categories =
                    new Category[]{
                            new Category("Fiction"),
                            new Category("Science"),
                            new Category("Literature")

                    };
            categoryDAO.insert(categories);

            Book[] books =
                    new Book[]{
                            new Book("Fault In our Stars", "Rs125", 1),
                            new Book("Rice Mother", "Rs325", 1),
                            new Book("The Palace of Illusion", "Rs452", 1)
                    };

            bookDAO.insert(books);


            Book[] books2 =
                    new Book[]{
                            new Book("Habbit", "Rs135", 2),
                            new Book("Graphical guide to Psychology", "Rs325", 2),
                            new Book("Theory of Everything", "Rs282", 2)
                    };

            bookDAO.insert(books2);


            Book[] books3 =
                    new Book[]{
                            new Book("The train to Pakistan", "Rs125", 3),
                            new Book("Experimental Truth", "Rs325", 3),
                            new Book("Poems by Rabindranath Tagore", "Rs122", 3)
                    };

            bookDAO.insert(books3);

            List<Book> books4 = bookDAO.getBooks();
            Log.e("rachita", "doInBackground: " + books4.size());
            return null;
        }


    }

}
