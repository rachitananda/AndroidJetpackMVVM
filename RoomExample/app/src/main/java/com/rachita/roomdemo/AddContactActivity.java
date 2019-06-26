package com.rachita.roomdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rachita.roomdemo.app.App;
import com.rachita.roomdemo.db.ContactDb;
import com.rachita.roomdemo.db.ContactEntity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddContactActivity extends AppCompatActivity {

    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.email_et)
    EditText emailEt;
    @BindView(R.id.save_btn)
    Button saveBtn;

    @Inject
    ContactDb contactDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact_layout);

        ButterKnife.bind(this);
        ((App) getApplication()).getComponent().inject(this);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contactDb.getContactDao().insert(new ContactEntity(nameEt.getText().toString(), emailEt.getText().toString()));
                List<ContactEntity> list = contactDb.getContactDao().selectAll();
                finish();
            }
        });


    }
}
