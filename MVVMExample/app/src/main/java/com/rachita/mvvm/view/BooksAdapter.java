package com.rachita.mvvm.view;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rachita.mvvm.R;
import com.rachita.mvvm.databinding.BookListItemBinding;
import com.rachita.mvvm.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

    private List<Book> books=new ArrayList<>();
    private OnItemClickListener listener;

    public void setBooks(List<Book> newBooks) {
         this.books = books;
         notifyDataSetChanged();
      /*  final DiffUtil.DiffResult result=DiffUtil.calculateDiff(new BooksDiffCallback(books,newBooks),false);
        books=newBooks;
        resultdispatchUpdatesTo(BooksAdapter.this);.*/

    }


    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        BookListItemBinding bookListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.book_list_item,viewGroup,false);
        return new BookViewHolder(bookListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder bookViewHolder, int position) {

        bookViewHolder.bookListItemBinding.setBook(books.get(position));

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder{
        BookListItemBinding bookListItemBinding;
        public BookViewHolder(@NonNull  BookListItemBinding bookListItemBinding) {
            super(bookListItemBinding.getRoot());
            this.bookListItemBinding=bookListItemBinding;
            this.bookListItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition = getAdapterPosition();
                    if (listener != null && clickedPosition != RecyclerView.NO_POSITION) {
                        listener.onItemClick(books.get(clickedPosition));
                    }
                }
            });
        }


    }


    public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
