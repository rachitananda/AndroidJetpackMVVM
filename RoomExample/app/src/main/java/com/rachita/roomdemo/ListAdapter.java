package com.rachita.roomdemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rachita.roomdemo.db.ContactEntity;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ContactViewHolder> {

    List<ContactEntity> contacts;

    public ListAdapter(List<ContactEntity> contacts) {
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item,viewGroup,false);

        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder contactViewHolder, int position) {

        contactViewHolder.nameTv.setText(contacts.get(position).getName());
        contactViewHolder.emailTv.setText(contacts.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTv, emailTv;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTv = itemView.findViewById(R.id.name_tv);
            emailTv = itemView.findViewById(R.id.email_tv);


        }
    }
}
