package com.rachita.roomdemo;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rachita.roomdemo.app.App;
import com.rachita.roomdemo.databinding.ActivityMainBinding;
import com.rachita.roomdemo.db.ContactDb;
import com.rachita.roomdemo.db.ContactEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ContactActivity extends AppCompatActivity {

    @Inject
    ContactDb contactDb;

    RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private List<ContactEntity> contacts = new ArrayList<>();
    private AddClickListener addClickListener;
    //this class will be auto generated based on teh layout name
    private ActivityMainBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Dagger Injection
        ((App) getApplication()).getComponent().inject(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //instead of creating on click lister- pass custon listener to layout
        addClickListener = new AddClickListener(this);

        dataBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        dataBinding.setClickHandler(addClickListener);

        //room query
        contacts = contactDb.getContactDao().selectAll();

        recyclerView= findViewById(R.id.recycler_view);
        listAdapter = new ListAdapter(contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);

       /*
       FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             startActivity(new Intent(ContactActivity.this,AddContactActivity.class));
            }
        });*/


        /*Delete on swipe left*/
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

                ContactEntity contactToDelete = contacts.get(viewHolder.getAdapterPosition());
                contactDb.getContactDao().delete(contactToDelete);
                loadData();


            }
        }).attachToRecyclerView(recyclerView);

    }


    private void loadData() {
        contacts.clear();
        contacts.addAll(contactDb.getContactDao().selectAll());
        listAdapter.notifyDataSetChanged();
    }

    public class AddClickListener {
        Context context;

        public AddClickListener(Context context) {
            this.context = context;
        }

        public void OnClick(View view) {
            startActivity(new Intent(context, AddContactActivity.class));
        }


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


    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}
