package com.rachita.mvvm.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.rachita.mvvm.R;
import com.rachita.mvvm.databinding.ActivityMainBinding;
import com.rachita.mvvm.model.Book;
import com.rachita.mvvm.model.BookStoreRepository;
import com.rachita.mvvm.model.Category;
import com.rachita.mvvm.root.App;
import com.rachita.mvvm.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    BookStoreRepository bookStoreRepository;

    private  ArrayAdapter<Category> categoryArrayAdapter;
    private ActivityMainBinding activityMainBinding;
    private ClickHandler clickHandler;
    private MainActivityViewModel mainActivityViewModel;
    private List<Book>  books;
    private RecyclerView recyclerView;
    private BooksAdapter booksAdapter;

    public static final int ADD_BOOK_REQUEST_CODE = 1;
    public static final int EDIT_BOOK_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((App) getApplication()).getApplicationComponent().inject(this);


        mainActivityViewModel= ViewModelProviders.of(this).get(MainActivityViewModel.class);

        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        clickHandler = new ClickHandler(this);
        activityMainBinding.setClickHandler(clickHandler);
        initSpinner();

        initList();




     /*   FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
             //   bookStoreRepository.codeToTriggerOnCreate();
            }
        });*/
    }

    private void initList() {

        mainActivityViewModel.getBooks(1).observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(@Nullable List<Book> bookList) {
                books = (ArrayList<Book>) bookList;
                loadRecyclerView();
            }
        });
    }

    private void loadRecyclerView() {

        recyclerView = activityMainBinding.secondaryLayout.rvBooks;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        booksAdapter = new BooksAdapter();
        recyclerView.setAdapter(booksAdapter);

        booksAdapter.setBooks(books);

    }


    private void initSpinner(){
        List<Category> categories = mainActivityViewModel.getCategories();//bookStoreRepository.getCategories();
        categoryArrayAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_dropdown_item,
                categories);
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityMainBinding.setSpinnerAdapter(categoryArrayAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class ClickHandler{

        private Context context;

        public ClickHandler(Context context) {
            this.context = context;
        }

        public void onAddButtonClick(View view){
            Intent intent = new Intent(MainActivity.this, BookActivity.class);
            startActivityForResult(intent, ADD_BOOK_REQUEST_CODE);

        }

        public void onSelectItem(AdapterView<?> parent, View view, int pos, long id) {

           /* selectedCategory = (Category) parent.getItemAtPosition(pos);

            String message = " id is " + selectedCategory.getId() + "\n name is " + selectedCategory.getCategoryName() + "\n email is " + selectedCategory.getCategoryDescription();

            loadBooksArrayList(selectedCategory.getId());*/
        }



    }
}
