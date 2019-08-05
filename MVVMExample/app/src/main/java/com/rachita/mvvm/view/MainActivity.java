package com.rachita.mvvm.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.rachita.mvvm.R;
import com.rachita.mvvm.databinding.ActivityMainBinding;
import com.rachita.mvvm.model.Book;
import com.rachita.mvvm.model.Category;
import com.rachita.mvvm.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ArrayAdapter<Category> categoryArrayAdapter;
    private ActivityMainBinding activityMainBinding;
    private ClickHandler clickHandler;
    private Category selectedCategory;
    private int selectedBookId;
    private MainActivityViewModel mainActivityViewModel;
      private List<Book>  books;
    private RecyclerView recyclerView;
    private BooksAdapter booksAdapter;
    private ArrayList<Category> categoriesList;

    public static final int ADD_BOOK_REQUEST_CODE = 1;
    public static final int EDIT_BOOK_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        ((App) getApplication()).getApplicationComponent().inject(this);


        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        clickHandler = new ClickHandler(this);
        activityMainBinding.setClickHandler(clickHandler);

        mainActivityViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {

                categoriesList = (ArrayList<Category>) categories;

                initSpinner();
            }
        });




    }

    private void loadRecyclerView(List<Book> bookList) {

        recyclerView = activityMainBinding.secondaryLayout.rvBooks;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        booksAdapter = new BooksAdapter();
        booksAdapter.setBooks(bookList);
        recyclerView.setAdapter(booksAdapter);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Book bookToDelete = books.get(viewHolder.getAdapterPosition());
                mainActivityViewModel.deleteBook(bookToDelete);
            }
        }).attachToRecyclerView(recyclerView);


        booksAdapter.setListener(new BooksAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                selectedBookId = book.getBookId();

                Intent intent = new Intent(MainActivity.this, BookActivity.class);
                intent.putExtra(BookActivity.BOOK_ID, selectedBookId);
                intent.putExtra(BookActivity.BOOK_NAME, book.getName());
                intent.putExtra(BookActivity.UNIT_PRICE, book.getPrice());
                startActivityForResult(intent, EDIT_BOOK_REQUEST_CODE);
            }
        });
    }


    private void initSpinner() {
    /* Without using live data
       List<Category> categories = mainActivityViewModel.getCategories();*/

        categoryArrayAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_dropdown_item,
                categoriesList);
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

    public class ClickHandler {

        private Context context;

        public ClickHandler(Context context) {
            this.context = context;
        }

        public void onAddButtonClick(View view) {
            Intent intent = new Intent(MainActivity.this, BookActivity.class);
            startActivityForResult(intent, ADD_BOOK_REQUEST_CODE);

        }

        public void onSelectItem(AdapterView<?> parent, View view, int pos, long id) {

             selectedCategory = (Category) parent.getItemAtPosition(pos);
            loadBooksArrayList(selectedCategory.getId());
        }
    }

    private void loadBooksArrayList(int categoryID) {

        mainActivityViewModel.getBooks(categoryID).observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(@Nullable List<Book> bookList) {
                books = (ArrayList<Book>) bookList;
                loadRecyclerView(books);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int selectedCategoryId = selectedCategory.getId();
        if (requestCode == ADD_BOOK_REQUEST_CODE && resultCode == RESULT_OK) {

            Book book = new Book();
            book.setCategoryId(selectedCategoryId);
            book.setName(data.getStringExtra(BookActivity.BOOK_NAME));
            book.setPrice(data.getStringExtra(BookActivity.UNIT_PRICE));
            mainActivityViewModel.addBook(book);


        } else if (requestCode == EDIT_BOOK_REQUEST_CODE && resultCode == RESULT_OK) {

            Book book = new Book();
            book.setCategoryId(selectedCategoryId);
            book.setName(data.getStringExtra(BookActivity.BOOK_NAME));
            book.setPrice(data.getStringExtra(BookActivity.UNIT_PRICE));

            book.setBookId(selectedBookId);
            mainActivityViewModel.updateBook(book);


        }
    }
}