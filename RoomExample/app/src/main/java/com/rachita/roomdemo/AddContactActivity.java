package com.rachita.roomdemo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.rachita.roomdemo.app.App;
import com.rachita.roomdemo.databinding.AddContactLayoutBinding;
import com.rachita.roomdemo.db.ContactDb;
import com.rachita.roomdemo.db.ContactEntity;

import java.util.List;

import javax.inject.Inject;

public class AddContactActivity extends AppCompatActivity {

  /*  @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.email_et)
    EditText emailEt;
    @BindView(R.id.save_btn)
    Button saveBtn;
*/
    @Inject
    ContactDb contactDb;

    private SaveContactHandler saveContactHandler;
    private AddContactLayoutBinding dataBinding;
    private ContactEntity contact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact_layout);

       // ButterKnife.bind(this);

        //Dagger Injection
        ((App) getApplication()).getComponent().inject(this);

        saveContactHandler= new SaveContactHandler();
        contact= new ContactEntity();

        //databinding
        dataBinding = DataBindingUtil.setContentView(this,R.layout.add_contact_layout);
        dataBinding.setClickHandler(saveContactHandler);
        dataBinding.setContact(contact);

       /*
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contactDb.getContactDao().insert(new ContactEntity(nameEt.getText().toString(), emailEt.getText().toString()));
                List<ContactEntity> list = contactDb.getContactDao().selectAll();
                finish();
            }
        });*/


    }


    public class SaveContactHandler{


        public void saveClickHandler(View v){

            contactDb.getContactDao().insert(contact);
            List<ContactEntity> list = contactDb.getContactDao().selectAll();
            finish();
        }

    }
}
